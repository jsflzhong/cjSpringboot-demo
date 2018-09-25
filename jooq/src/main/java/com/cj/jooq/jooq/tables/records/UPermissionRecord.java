/*
 * This file is generated by jOOQ.
*/
package com.cj.jooq.jooq.tables.records;


import com.cj.jooq.jooq.tables.UPermission;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UPermissionRecord extends UpdatableRecordImpl<UPermissionRecord> implements Record6<String, String, String, String, String, String> {

    private static final long serialVersionUID = -186544747;

    /**
     * Setter for <code>test.u_permission.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>test.u_permission.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>test.u_permission.url</code>. url地址
     */
    public void setUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>test.u_permission.url</code>. url地址
     */
    public String getUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>test.u_permission.name</code>. url描述
     */
    public void setName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>test.u_permission.name</code>. url描述
     */
    public String getName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>test.u_permission.permission</code>. 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    public void setPermission(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>test.u_permission.permission</code>. 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    public String getPermission() {
        return (String) get(3);
    }

    /**
     * Setter for <code>test.u_permission.parentId</code>. 父编号
     */
    public void setParentid(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>test.u_permission.parentId</code>. 父编号
     */
    public String getParentid() {
        return (String) get(4);
    }

    /**
     * Setter for <code>test.u_permission.parentIds</code>. 父编号列表列表
     */
    public void setParentids(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>test.u_permission.parentIds</code>. 父编号列表列表
     */
    public String getParentids() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return UPermission.U_PERMISSION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return UPermission.U_PERMISSION.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return UPermission.U_PERMISSION.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return UPermission.U_PERMISSION.PERMISSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return UPermission.U_PERMISSION.PARENTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UPermission.U_PERMISSION.PARENTIDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getPermission();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getParentid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getParentids();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPermission();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getParentid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getParentids();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord value2(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord value3(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord value4(String value) {
        setPermission(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord value5(String value) {
        setParentid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord value6(String value) {
        setParentids(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UPermissionRecord values(String value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UPermissionRecord
     */
    public UPermissionRecord() {
        super(UPermission.U_PERMISSION);
    }

    /**
     * Create a detached, initialised UPermissionRecord
     */
    public UPermissionRecord(String id, String url, String name, String permission, String parentid, String parentids) {
        super(UPermission.U_PERMISSION);

        set(0, id);
        set(1, url);
        set(2, name);
        set(3, permission);
        set(4, parentid);
        set(5, parentids);
    }
}
