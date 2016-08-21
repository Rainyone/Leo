package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="permission",id="id",strategy = StrategyType.NULL)
public class Permission extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//permission.id (权限id)
		public static final String name = "name";//permission.name (权限名字)
		public static final String key = "key";//permission.key (权限key)
		public static final String parentId = "parent_id";//permission.parent_id (上级权限)
		public static final String order = "order";//permission.order (权限排序)
	}

    public String getId() {
        return super.getStr(Columns.id);
    }

    public Permission setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getName() {
        return super.get(Columns.name);
    }

    public Permission setName(String name) {
    	super.set(Columns.name, name);
        return this;
    }

    public String getKey() {
        return super.get(Columns.key);
    }

    public Permission setKey(String key) {
    	super.set(Columns.key, key);
        return this;
    }

    public String getParentId() {
        return super.getStr(Columns.parentId);
    }

    public Permission setParentId(String parentId) {
    	super.set(Columns.parentId, parentId);
        return this;
    }

    public Integer getOrder() {
        return super.getInt(Columns.order);
    }

    public Permission setOrder(Integer order) {
    	super.set(Columns.order, order);
        return this;
    }
}