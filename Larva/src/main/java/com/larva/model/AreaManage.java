package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_app_manage",id="id", strategy = StrategyType.NULL)
public class AreaManage extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String areid = "areid";
		public static final String areaname = "areaname";
		public static final String parentId = "parentId";
		public static final String arealevel = "arealevel";
		public static final String status = "status";
	}
    public String getAreaId() {
        return super.getStr(Columns.areid);
    }

    public AreaManage setAreaId(String areid) {
        super.set(Columns.areid, areid);
        return this;
    }
    public String getAreaName() {
    	return super.getStr(Columns.areaname);
	}
	public AreaManage setAreaName(String areaname) {
		super.set(Columns.areaname, areaname);
		return this;
	}
	public String getParentId() {
		return super.getStr(Columns.parentId);
	}
	public AreaManage setParentId(String parentId) {
		super.set(Columns.parentId, parentId);
		return this;
	}
	
	public String getAreaLevel() {
		return super.getStr(Columns.arealevel);
	}
	public AreaManage setAreaLevel(String arealevel) {
		super.set(Columns.arealevel, arealevel);
		return this;
	}
	public String getStatus() {
		return super.getStr(Columns.status);
	}
	public AreaManage setStatus(String status) {
		super.set(Columns.status, status);
		return this;
	}
    
}