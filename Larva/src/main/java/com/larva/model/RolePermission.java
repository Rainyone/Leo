package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="role_permission",id="id",strategy = StrategyType.AUTO)
public class RolePermission extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//role_permission.id
		public static final String roleId = "role_id";//role_permission.role_id (角色id)
		public static final String permissionId = "permission_id";//role_permission.permission_id (权限id)
	}

    public Integer getId() {
        return super.getInt(Columns.id);
    }

    public RolePermission setId(Integer id) {
    	super.set(Columns.id, id);
        return this;
    }

    public Integer getRoleId() {
        return super.getInt(Columns.roleId);
    }

    public RolePermission setRoleId(Integer roleId) {
    	super.set(Columns.roleId, roleId);
        return this;
    }

    public Integer getPermissionId() {
        return super.getInt(Columns.permissionId);
    }

    public RolePermission setPermissionId(Integer permissionId) {
    	super.set(Columns.permissionId, permissionId);
        return this;
    }
}