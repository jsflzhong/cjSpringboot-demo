/*
 * This file is generated by jOOQ.
*/
package com.cj.jooq.jooq.tables.records;


import com.cj.jooq.jooq.tables.URolePermission;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class URolePermissionRecord extends UpdatableRecordImpl<URolePermissionRecord> implements Record3<String, String, String> {

    private static final long serialVersionUID = 1487188316;

    /**
     * Setter for <code>test.u_role_permission.id</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>test.u_role_permission.id</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>test.u_role_permission.rid</code>. 角色ID
     */
    public void setRid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>test.u_role_permission.rid</code>. 角色ID
     */
    public String getRid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>test.u_role_permission.pid</code>. 权限ID
     */
    public void setPid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>test.u_role_permission.pid</code>. 权限ID
     */
    public String getPid() {
        return (String) get(2);
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
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return URolePermission.U_ROLE_PERMISSION.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return URolePermission.U_ROLE_PERMISSION.RID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return URolePermission.U_ROLE_PERMISSION.PID;
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
        return getRid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getPid();
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
        return getRid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getPid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URolePermissionRecord value1(String value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URolePermissionRecord value2(String value) {
        setRid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URolePermissionRecord value3(String value) {
        setPid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URolePermissionRecord values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached URolePermissionRecord
     */
    public URolePermissionRecord() {
        super(URolePermission.U_ROLE_PERMISSION);
    }

    /**
     * Create a detached, initialised URolePermissionRecord
     */
    public URolePermissionRecord(String id, String rid, String pid) {
        super(URolePermission.U_ROLE_PERMISSION);

        set(0, id);
        set(1, rid);
        set(2, pid);
    }
}
