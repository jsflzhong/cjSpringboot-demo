/*
 * This file is generated by jOOQ.
*/
package com.cj.jooq.jooq;


import com.cj.jooq.jooq.tables.CustDeviceInfo;
import com.cj.jooq.jooq.tables.CustDevicePushLog;
import com.cj.jooq.jooq.tables.UPermission;
import com.cj.jooq.jooq.tables.URole;
import com.cj.jooq.jooq.tables.URolePermission;
import com.cj.jooq.jooq.tables.UUser;
import com.cj.jooq.jooq.tables.UUserRole;
import com.cj.jooq.jooq.tables.UserT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Test extends SchemaImpl {

    private static final long serialVersionUID = 697013263;

    /**
     * The reference instance of <code>test</code>
     */
    public static final Test TEST = new Test();

    /**
     * 客户设备信息表
     */
    public final CustDeviceInfo CUST_DEVICE_INFO = com.cj.jooq.jooq.tables.CustDeviceInfo.CUST_DEVICE_INFO;

    /**
     * 设备推送日志表
     */
    public final CustDevicePushLog CUST_DEVICE_PUSH_LOG = com.cj.jooq.jooq.tables.CustDevicePushLog.CUST_DEVICE_PUSH_LOG;

    /**
     * The table <code>test.user_t</code>.
     */
    public final UserT USER_T = com.cj.jooq.jooq.tables.UserT.USER_T;

    /**
     * The table <code>test.u_permission</code>.
     */
    public final UPermission U_PERMISSION = com.cj.jooq.jooq.tables.UPermission.U_PERMISSION;

    /**
     * The table <code>test.u_role</code>.
     */
    public final URole U_ROLE = com.cj.jooq.jooq.tables.URole.U_ROLE;

    /**
     * The table <code>test.u_role_permission</code>.
     */
    public final URolePermission U_ROLE_PERMISSION = com.cj.jooq.jooq.tables.URolePermission.U_ROLE_PERMISSION;

    /**
     * The table <code>test.u_user</code>.
     */
    public final UUser U_USER = com.cj.jooq.jooq.tables.UUser.U_USER;

    /**
     * The table <code>test.u_user_role</code>.
     */
    public final UUserRole U_USER_ROLE = com.cj.jooq.jooq.tables.UUserRole.U_USER_ROLE;

    /**
     * No further instances allowed
     */
    private Test() {
        super("test", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            CustDeviceInfo.CUST_DEVICE_INFO,
            CustDevicePushLog.CUST_DEVICE_PUSH_LOG,
            UserT.USER_T,
            UPermission.U_PERMISSION,
            URole.U_ROLE,
            URolePermission.U_ROLE_PERMISSION,
            UUser.U_USER,
            UUserRole.U_USER_ROLE);
    }
}
