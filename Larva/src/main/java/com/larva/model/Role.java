package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="role",id="id",strategy = StrategyType.NULL)
public class Role extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//`role`.id (角色id)
		public static final String name = "name";//`role`.name (角色名字)
		public static final String key = "key";//`role`.key (角色key)
	}

    public String getId() {
        return super.getStr(Columns.id);
    }

    public Role setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getName() {
        return super.get(Columns.name);
    }

    public Role setName(String name) {
    	super.set(Columns.name, name);
        return this;
    }

    public String getKey() {
        return super.get(Columns.key);
    }

    public Role setKey(String key) {
    	super.set(Columns.key, key);
        return this;
    }
}