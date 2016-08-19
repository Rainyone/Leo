package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="permission",id="id",strategy = StrategyType.AUTO)
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

    public Integer getId() {
        return super.getInt(Columns.id);
    }

    public Permission setId(Integer id) {
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

    public Integer getParentId() {
        return super.getInt(Columns.parentId);
    }

    public Permission setParentId(Integer parentId) {
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