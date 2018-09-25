package com.cj.jooq.service.base;

import org.apache.commons.lang3.tuple.Pair;
import org.jooq.*;
import org.jooq.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.trueCondition;

/**
 * Created by Jian.Cui on 2018/9/25.
 */
public abstract class AbstractJooqFactory<E extends Serializable> {

    @Autowired
    protected DSLContext dsl;

    /**
     * Mapping the Class and Table,so we can get the key or value in the api in this class conveniently.
     *
     * @return (Object, Relationship)
     */
    protected abstract Pair<Class<E>, Table<? extends UpdatableRecord>> mapping();

    protected Class<E> type() {
        return mapping().getLeft();
    }

    protected Table<? extends UpdatableRecord> table() {
        return mapping().getRight();
    }

    /**
     * ------------------------------------select------------------------------------
     */
    public List<E> findAll() {
        return dsl.selectFrom(table()).fetchInto(type());
    }

    public Optional<E> findOptionalById(Integer id) {
        return findOptional(_pk(), id);
    }

    /**
     * Find by field
     * The class "DSL" has a lot of useful things such as Field,condition,row...
     * Tested
     */
    public <Z> List<E> find(Field<Z> field, Z value) {
        return dsl.selectFrom(table()).where(field.eq(value)).fetchInto(type());
    }

    /**
     * findOptional by field
     * The Optional class provides a lot of api about null or empty judgement.
     * Tested
     */
    public <Z> Optional<E> findOptional(Field<Z> field, Z value) {
        return dsl.selectFrom(table()).where(field.eq(value)).fetchOptionalInto(type());
    }

    /**
     * findOptional by condition
     * Tested
     */
    public Optional<E> findOptional(Condition condition) {
        return select(condition).fetchOptionalInto(type());
    }

    /**
     * find by multi conditions
     * DO NOT use "and" in the condition because jooq already do it for me.
     * Tested
     */
    public List<E> find(Condition... condition) {
        return select(condition).fetchInto(type());
    }

    public <T extends E> List<T> find(Condition condition, Class<T> subType) {
        return select(condition).fetchInto(subType);
    }


    public long countAll() {
        return dsl.selectCount().from(table()).fetchOneInto(long.class);
    }

    public <Z> long count(Field<Z> field, Z value) {
        return dsl.selectCount().from(table()).where(field.eq(value)).fetchOneInto(long.class);
    }

    public long count(Condition... condition) {
        return dsl.selectCount().from(table()).where(condition).fetchOneInto(long.class);
    }


    public boolean existsById(Integer id) {
        return exists(_pk(), id);
    }

    public <Z> boolean exists(Field<Z> field, Z value) {
        return count(field, value) > 0;
    }

    public boolean exists(Condition... condition) {
        return count(condition) > 0;
    }

    public boolean notExists(Condition... condition) {
        return count(condition) == 0;
    }

    /**
     * ------------------------------------insert------------------------------------
     */
    public boolean insert(E entity) {
        return record(entity, false).insert() == 1;
    }

    /**
     * 新增记录并返回新增后的记录
     *
     * @apiNote 先执行insert, 再执行select
     */
    public <T extends E> E insertAndReturn(T entity) {
        return dsl.insertInto(table()).set(record(entity, false)).returning().fetchOne().into(type());
    }

    /**
     * insert and return the primary key of this data.
     * Attention: The type of id should be Integer or sth related in this function.
     * If it is a String, then there will be an exception even i use a Integer as the id.
     * Tested --- Exception.
     */
    public <T extends E> Integer insertAndReturnId(T entity) {
        UpdatableRecord record = record(entity, false);
        record.insert();
        return record.getValue(_pk());
    }

    /**
     * insert, or, if the primary key is already existed,then update.
     * Tested
     *
     * @return affect lines
     */
    public Integer save(Map<Field<?>, ?> saveMap) {
        if (saveMap == null || saveMap.isEmpty()) {
            return 0;
        }
        return dsl.insertInto(table()).set(saveMap).onDuplicateKeyUpdate().set(saveMap).execute();
    }

    /**
     * ------------------------------------update------------------------------------
     */
    public <T extends E> boolean update(T entity) {
        return record(entity, true).update() == 1;
    }

    public <T extends E> E updateAndReturn(T entity) {
        UpdatableRecord<?> record = record(entity, true);
        Integer id = record.getValue(_pk());
        if (id == null) {
            throw new IllegalArgumentException("id can not be null for update");
        }
        record.update();
        return select(_pk().eq(id)).fetchOneInto(type());
    }

    /**
     * @param record     待更新的数据
     * @param conditions 更新条件
     */
    public int update(UpdatableRecord record, List<Condition> conditions) {
        return dsl.update(table()).set(record).where(conditions).execute();
    }

    /**
     * @param record     待更新的数据
     * @param conditions 更新条件
     */
    public int update(UpdatableRecord record, Condition... conditions) {
        return dsl.update(table()).set(record).where(conditions).execute();
    }

    /**
     * @param map        待更新的数据
     * @param conditions 更新条件
     * @apiNote 如果多个更新项之间有严格的先后顺序, 务必使用有序Map
     */
    public int update(Map<? extends Field<?>, ?> map, List<Condition> conditions) {
        return dsl.update(table()).set(map).where(conditions).execute();
    }

    /**
     * @param map       待更新的数据
     * @param condition 更新条件
     * @apiNote 如果多个更新项之间有严格的先后顺序, 务必使用有序Map
     */
    public int update(Map<? extends Field<?>, ?> map, Condition condition) {
        return dsl.update(table()).set(map).where(condition).execute();
    }

    /**
     * 更新指定的字段
     *
     * @param conditions 更新条件
     */
    public <Z> int update(Field<Z> field, Z value, List<Condition> conditions) {
        return dsl.update(table()).set(field, value).where(conditions).execute();
    }

    /**
     * 更新指定的字段
     *
     * @param conditions 更新条件
     */
    public <Z> int update(Field<Z> field, Z value, Condition... conditions) {
        return dsl.update(table()).set(field, value).where(conditions).execute();
    }

    /**
     * ------------------------------------delete------------------------------------
     */
    public boolean deleteById(Integer... ids) {
        if (ids.length == 1) {
            return dsl.delete(table()).where(_pk().eq(ids[0])).execute() == 1;
        }
        return dsl.delete(table()).where(_pk().in(ids)).execute() == ids.length;
    }

    public <Z> int deleteBy(Field<Z> field, Z value) {
        return dsl.delete(table()).where(field.eq(value)).execute();
    }


    protected UpdatableRecord<?> record(E entity, boolean forUpdate) {
        Field<Integer> field = _pk();
        UpdatableRecord<?> record = dsl.newRecord(table(), entity);
        if (forUpdate && field != null) {
            record.changed(field, false);
        }
        // 如果实体属性为NULL,但数据库列为NOT NULL, 则允许数据库应用列的默认值
        int size = record.size();
        for (int i = 0; i < size; i++)
            if (record.getValue(i) == null && !record.field(i).getDataType().nullable())
                record.changed(i, false);
        return record;
    }

    protected List<UpdatableRecord<?>> records(Collection<E> entities, boolean forUpdate) {
        return entities.stream().map(entity -> record(entity, forUpdate)).collect(Collectors.toList());
    }

    private Field<Integer> _pk() {
        UniqueKey<?> key = table().getPrimaryKey();
        if (key == null) {
            throw new DataAccessException("no primary key");
        }
        Field<?>[] fields = key.getFieldsArray();
        if (fields.length == 0) {
            throw new DataAccessException("no primary key");
        }
        return fields[0].cast(Integer.class);
    }

    protected SelectConditionStep<? extends Record> select(Condition... condition) {
        if (condition == null) {
            return dsl.selectFrom(table()).where(trueCondition());
        }
        return dsl.selectFrom(table()).where(condition);
    }
}
