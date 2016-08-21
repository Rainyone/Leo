package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="department",id="id",strategy = StrategyType.NULL)
public class Department extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//department.id
		public static final String parentId = "parent_id";//department.parent_id (上级部门id)
		public static final String name = "name";//department.name (部门名字)
		public static final String order = "order";//department.order (排序)
	}
	
    public String getId() {
        return super.getStr(Columns.id);
    }

    public Department setId(String id) {
        super.set(Columns.id, id);
        return this;
    }

    public String getParentId() {
        return super.getStr(Columns.parentId);
    }

    public Department setParentId(String parentId) {
    	super.set(Columns.parentId, parentId);
        return this;
    }

    public String getName() {
        return super.get(Columns.name);
    }

    public Department setName(String name) {
    	super.set(Columns.name, name);
        return this;
    }

    public Integer getOrder() {
        return super.getInt(Columns.order);
    }

    public Department setOrder(Integer order) {
    	super.set(Columns.order, order);
        return this;
    }
}