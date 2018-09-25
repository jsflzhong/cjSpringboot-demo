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
import com.cj.jooq.jooq.tables.records.CustDeviceInfoRecord;
import com.cj.jooq.jooq.tables.records.CustDevicePushLogRecord;
import com.cj.jooq.jooq.tables.records.UPermissionRecord;
import com.cj.jooq.jooq.tables.records.URolePermissionRecord;
import com.cj.jooq.jooq.tables.records.URoleRecord;
import com.cj.jooq.jooq.tables.records.UUserRecord;
import com.cj.jooq.jooq.tables.records.UUserRoleRecord;
import com.cj.jooq.jooq.tables.records.UserTRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>test</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<CustDeviceInfoRecord, Integer> IDENTITY_CUST_DEVICE_INFO = Identities0.IDENTITY_CUST_DEVICE_INFO;
    public static final Identity<CustDevicePushLogRecord, Integer> IDENTITY_CUST_DEVICE_PUSH_LOG = Identities0.IDENTITY_CUST_DEVICE_PUSH_LOG;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CustDeviceInfoRecord> KEY_CUST_DEVICE_INFO_PRIMARY = UniqueKeys0.KEY_CUST_DEVICE_INFO_PRIMARY;
    public static final UniqueKey<CustDeviceInfoRecord> KEY_CUST_DEVICE_INFO_IDX_ID = UniqueKeys0.KEY_CUST_DEVICE_INFO_IDX_ID;
    public static final UniqueKey<CustDeviceInfoRecord> KEY_CUST_DEVICE_INFO_IDX_PUSH_TOKEN = UniqueKeys0.KEY_CUST_DEVICE_INFO_IDX_PUSH_TOKEN;
    public static final UniqueKey<CustDevicePushLogRecord> KEY_CUST_DEVICE_PUSH_LOG_PRIMARY = UniqueKeys0.KEY_CUST_DEVICE_PUSH_LOG_PRIMARY;
    public static final UniqueKey<CustDevicePushLogRecord> KEY_CUST_DEVICE_PUSH_LOG_IDX_ID = UniqueKeys0.KEY_CUST_DEVICE_PUSH_LOG_IDX_ID;
    public static final UniqueKey<UserTRecord> KEY_USER_T_PRIMARY = UniqueKeys0.KEY_USER_T_PRIMARY;
    public static final UniqueKey<UPermissionRecord> KEY_U_PERMISSION_PRIMARY = UniqueKeys0.KEY_U_PERMISSION_PRIMARY;
    public static final UniqueKey<URoleRecord> KEY_U_ROLE_PRIMARY = UniqueKeys0.KEY_U_ROLE_PRIMARY;
    public static final UniqueKey<URolePermissionRecord> KEY_U_ROLE_PERMISSION_PRIMARY = UniqueKeys0.KEY_U_ROLE_PERMISSION_PRIMARY;
    public static final UniqueKey<UUserRecord> KEY_U_USER_PRIMARY = UniqueKeys0.KEY_U_USER_PRIMARY;
    public static final UniqueKey<UUserRoleRecord> KEY_U_USER_ROLE_PRIMARY = UniqueKeys0.KEY_U_USER_ROLE_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 extends AbstractKeys {
        public static Identity<CustDeviceInfoRecord, Integer> IDENTITY_CUST_DEVICE_INFO = createIdentity(CustDeviceInfo.CUST_DEVICE_INFO, CustDeviceInfo.CUST_DEVICE_INFO.ID);
        public static Identity<CustDevicePushLogRecord, Integer> IDENTITY_CUST_DEVICE_PUSH_LOG = createIdentity(CustDevicePushLog.CUST_DEVICE_PUSH_LOG, CustDevicePushLog.CUST_DEVICE_PUSH_LOG.ID);
    }

    private static class UniqueKeys0 extends AbstractKeys {
        public static final UniqueKey<CustDeviceInfoRecord> KEY_CUST_DEVICE_INFO_PRIMARY = createUniqueKey(CustDeviceInfo.CUST_DEVICE_INFO, "KEY_cust_device_info_PRIMARY", CustDeviceInfo.CUST_DEVICE_INFO.ID);
        public static final UniqueKey<CustDeviceInfoRecord> KEY_CUST_DEVICE_INFO_IDX_ID = createUniqueKey(CustDeviceInfo.CUST_DEVICE_INFO, "KEY_cust_device_info_idx_id", CustDeviceInfo.CUST_DEVICE_INFO.ID);
        public static final UniqueKey<CustDeviceInfoRecord> KEY_CUST_DEVICE_INFO_IDX_PUSH_TOKEN = createUniqueKey(CustDeviceInfo.CUST_DEVICE_INFO, "KEY_cust_device_info_idx_push_token", CustDeviceInfo.CUST_DEVICE_INFO.PUSH_TOKEN);
        public static final UniqueKey<CustDevicePushLogRecord> KEY_CUST_DEVICE_PUSH_LOG_PRIMARY = createUniqueKey(CustDevicePushLog.CUST_DEVICE_PUSH_LOG, "KEY_cust_device_push_log_PRIMARY", CustDevicePushLog.CUST_DEVICE_PUSH_LOG.ID);
        public static final UniqueKey<CustDevicePushLogRecord> KEY_CUST_DEVICE_PUSH_LOG_IDX_ID = createUniqueKey(CustDevicePushLog.CUST_DEVICE_PUSH_LOG, "KEY_cust_device_push_log_idx_id", CustDevicePushLog.CUST_DEVICE_PUSH_LOG.ID);
        public static final UniqueKey<UserTRecord> KEY_USER_T_PRIMARY = createUniqueKey(UserT.USER_T, "KEY_user_t_PRIMARY", UserT.USER_T.ID);
        public static final UniqueKey<UPermissionRecord> KEY_U_PERMISSION_PRIMARY = createUniqueKey(UPermission.U_PERMISSION, "KEY_u_permission_PRIMARY", UPermission.U_PERMISSION.ID);
        public static final UniqueKey<URoleRecord> KEY_U_ROLE_PRIMARY = createUniqueKey(URole.U_ROLE, "KEY_u_role_PRIMARY", URole.U_ROLE.ID);
        public static final UniqueKey<URolePermissionRecord> KEY_U_ROLE_PERMISSION_PRIMARY = createUniqueKey(URolePermission.U_ROLE_PERMISSION, "KEY_u_role_permission_PRIMARY", URolePermission.U_ROLE_PERMISSION.ID);
        public static final UniqueKey<UUserRecord> KEY_U_USER_PRIMARY = createUniqueKey(UUser.U_USER, "KEY_u_user_PRIMARY", UUser.U_USER.ID);
        public static final UniqueKey<UUserRoleRecord> KEY_U_USER_ROLE_PRIMARY = createUniqueKey(UUserRole.U_USER_ROLE, "KEY_u_user_role_PRIMARY", UUserRole.U_USER_ROLE.ID);
    }
}
