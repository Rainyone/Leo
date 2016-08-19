package com.mini.example.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Column;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

/**
 * 
 * @author sxjun
 * @date 2015-06-22 18:19:47
 */
@Entity(table="users",id="id",strategy=StrategyType.UUID)
public class User extends WeakEntity implements Serializable {

	public static final long serialVersionUID = 6405509322884392287L;
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String name = "name";
		public static final String password = "password";
		public static final String createTime = "createtime";
		public static final String type = "type";
	}

	
	public User setId (String id) {
		super.set(Columns.id, id);
		return this;
	}
	
	@Column("id")
	public String getId () {
		return super.get(Columns.id);
	}
	
	public User setName (String name) {
		super.set(Columns.name, name);
		return this;
	}
	
	@Column("name")
	public String getName () {
		return super.get(Columns.name);
	}
	
	public User setPassword (String password) {
		super.set(Columns.password, password);
		return this;
	}
	
	@Column("password")
	public String getPassword () {
		return super.get(Columns.password);
	}
	
	public User setCreateTime (Date createTime) {
		super.set(Columns.createTime, createTime);
		return this;
	}
	
	@Column("createtime")
	public Date getCreateTime () {
		return super.get(Columns.createTime);
	}
	
	public User setType (Integer type) {
		super.set(Columns.type, type);
		return this;
	}
	
	@Column("type")
	public Integer getType () {
		return super.get(Columns.type);
	}

}
