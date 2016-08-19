package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="k_versions",id="id",strategy = StrategyType.AUTO)
public class K_Versions extends WeakEntity implements Serializable  {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String instance_id = "instance_id";
		public static final String version_type = "version_type";
		public static final String version_name = "version_name";
		public static final String version = "version";
	}
	
	public String getId() {
		return super.get(Columns.id);
	}
	public K_Versions setId(String id) {
		super.set(Columns.id, id);
		return this;
	}
	
	public String getInstance_id() {
		return super.get(Columns.instance_id);
	}
	public K_Versions setInstance_id(String instance_id) {
		super.set(Columns.instance_id, instance_id);
		return this;
	}
	
	public String getVersion_type() {
		return super.get(Columns.version_type);
	}
	public K_Versions setVersion_type(String version_type) {
		super.set(Columns.version_type, version_type);
		return this;
	}
	
	public String getVersion_name() {
		return super.get(Columns.version_name);
	}
	public K_Versions setVersion_name(String version_name) {
		super.set(Columns.version_name, version_name);
		return this;
	}
	
	public Long getVersion() {
		return super.get(Columns.version);
	}
	public K_Versions setVersion(Long version) {
		super.set(Columns.version, version);
		return this;
	}
	
}
