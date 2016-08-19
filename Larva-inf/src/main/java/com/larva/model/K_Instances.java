package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="k_instances",id="id",strategy = StrategyType.AUTO)
public class K_Instances extends WeakEntity implements Serializable  {
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String hostname = "hostname";
		public static final String cpuCounts = "cpuCounts";
		public static final String cpuCores = "cpuCores";
		public static final String memTotal = "memTotal";
		public static final String status = "status";
	}
	
	public String getId() {
		return super.get(Columns.id);
	}
	public K_Instances setId(String id) {
		super.set(Columns.id, id);
		return this;
	}
	
	public String getHostname() {
		return super.get(Columns.hostname);
	}
	public K_Instances setHostname(String hostname) {
		super.set(Columns.hostname, hostname);
		return this;
	}
	
	public String getCpuCounts() {
		return super.get(Columns.cpuCounts);
	}
	public K_Instances setCpuCounts(String cpuCounts) {
		super.set(Columns.cpuCounts, cpuCounts);
		return this;
	}
	
	public String getCpuCores() {
		return super.get(Columns.cpuCores);
	}
	public K_Instances setCpuCores(String cpuCores) {
		super.set(Columns.cpuCores, cpuCores);
		return this;
	}
	
	public String getMemTotal() {
		return super.get(Columns.memTotal);
	}
	public K_Instances setMemTotal(String memTotal) {
		super.set(Columns.memTotal, memTotal);
		return this;
	}
	public String getStatus() {
		return super.get(Columns.status);
	}
	public K_Instances setStatus(String status) {
		super.set(Columns.status, status);
		return this;
	}
	
}
