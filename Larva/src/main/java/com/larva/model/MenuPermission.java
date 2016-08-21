package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="menu_permission",id="id",strategy = StrategyType.NULL)
public class MenuPermission extends WeakEntity implements Serializable {
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//menu_permission.id
		public static final String menuId = "menu_id";//menu_permission.menu_id (菜单id)
		public static final String permissionId = "permission_id";//menu_permission.permission_id (权限id)
	}

    public String getId() {
        return super.getStr(Columns.id);
    }

    public MenuPermission setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getMenuId() {
        return super.getStr(Columns.menuId);
    }

    public MenuPermission setMenuId(String menuId) {
    	super.set(Columns.menuId, menuId);
        return this;
    }

    public String getPermissionId() {
        return super.getStr(Columns.permissionId);
    }

    public MenuPermission setPermissionId(String permissionId) {
    	super.set(Columns.permissionId, permissionId);
        return this;
    }
}