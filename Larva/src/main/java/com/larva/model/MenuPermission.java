package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="menu_permission",id="id",strategy = StrategyType.AUTO)
public class MenuPermission extends WeakEntity implements Serializable {
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//menu_permission.id
		public static final String menuId = "menu_id";//menu_permission.menu_id (菜单id)
		public static final String permissionId = "permission_id";//menu_permission.permission_id (权限id)
	}

    public Integer getId() {
        return super.getInt(Columns.id);
    }

    public MenuPermission setId(Integer id) {
    	super.set(Columns.id, id);
        return this;
    }

    public Integer getMenuId() {
        return super.getInt(Columns.menuId);
    }

    public MenuPermission setMenuId(Integer menuId) {
    	super.set(Columns.menuId, menuId);
        return this;
    }

    public Integer getPermissionId() {
        return super.getInt(Columns.permissionId);
    }

    public MenuPermission setPermissionId(Integer permissionId) {
    	super.set(Columns.permissionId, permissionId);
        return this;
    }
}