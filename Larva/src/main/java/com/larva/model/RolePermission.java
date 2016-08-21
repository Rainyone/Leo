package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="role_permission",id="id",strategy = StrategyType.NULL)
public class RolePermission extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//role_permission.id
		public static final String roleId = "role_id";//role_permission.role_id (角色id)
		public static final String permissionId = "permission_id";//role_permission.permission_id (权限id)
	}

    public String getId() {
        return super.getStr(Columns.id);
    }

    public RolePermission setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getRoleId() {
        return super.getStr(Columns.roleId);
    }

    public RolePermission setRoleId(String roleId) {
    	super.set(Columns.roleId, roleId);
        return this;
    }

    public String getPermissionId() {
        return super.getStr(Columns.permissionId);
    }

    public RolePermission setPermissionId(String permissionId) {
    	super.set(Columns.permissionId, permissionId);
        return this;
    }
}