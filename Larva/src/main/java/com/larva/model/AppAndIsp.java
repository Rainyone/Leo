package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="app_and_isp",id="id",strategy = StrategyType.NULL)
public class AppAndIsp extends WeakEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String app_id = "app_id";
		public static final String app_name = "app_name";
		public static final String dx = "dx";
		public static final String yd = "yd";
		public static final String lt = "lt";
		public static final String qt = "qt";
	}
	public String getAppId() {
        return super.getStr(Columns.app_id);
    }

    public AppAndIsp setAppId(String app_id) {
    	super.set(Columns.app_id, app_id);
        return this;
    }
	public String getAppName() {
        return super.getStr(Columns.app_name);
    }

    public AppAndIsp setAppName(String app_name) {
    	super.set(Columns.app_name, app_name);
        return this;
    }
	
	public Integer getQt() {
        return super.getInt(Columns.qt);
    }

    public AppAndIsp setQt(Integer qt) {
    	super.set(Columns.qt, qt);
        return this;
    }
	public Integer getLt() {
        return super.getInt(Columns.lt);
    }

    public AppAndIsp setLt(Integer lt) {
    	super.set(Columns.lt, lt);
        return this;
    }
	public Integer getYd() {
        return super.getInt(Columns.yd);
    }

    public AppAndIsp setYd(Integer yd) {
    	super.set(Columns.yd, yd);
        return this;
    }
	public Integer getDx() {
        return super.getInt(Columns.dx);
    }

    public AppAndIsp setDx(Integer dx) {
    	super.set(Columns.dx, dx);
        return this;
    }
    
}