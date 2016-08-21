package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="menu",id="id",strategy = StrategyType.NULL)
public class Menu extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//menu.id
		public static final String parentId = "parent_id";//menu.parent_id (菜单上级id))
		public static final String name = "name";//menu.name (菜单名字)
		public static final String url = "url";//menu.url (菜单链接地址)
		public static final String order = "order";//menu.order (菜单排序)
		public static final String icon = "icon";//menu.icon (菜单图片)
	}

    public String getId() {
        return super.getStr(Columns.id);
    }

    public Menu setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getParentId() {
        return super.getStr(Columns.parentId);
    }

    public Menu setParentId(String parentId) {
    	super.set(Columns.parentId, parentId);
        return this;
    }

    public String getName() {
        return super.get(Columns.name);
    }

    public Menu setName(String name) {
    	super.set(Columns.name, name);
        return this;
    }

    public String getUrl() {
        return super.get(Columns.url);
    }

    public Menu setUrl(String url) {
    	super.set(Columns.url, url);
        return this;
    }

    public Integer getOrder() {
        return super.getInt(Columns.order);
    }

    public Menu setOrder(Integer order) {
    	super.set(Columns.order, order);
        return this;
    }

	public String getIcon() {
		return super.get(Columns.icon);
	}

	public Menu setIcon(String icon) {
		super.set(Columns.icon, icon);
		return this;
	}
    
}