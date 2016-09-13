package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="charge_code_and_isp",id="id",strategy = StrategyType.NULL)
public class ChargeCodeAndIsp extends WeakEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String code_name = "code_name";
		public static final String dx = "dx";
		public static final String yd = "yd";
		public static final String lt = "lt";
		public static final String qt = "qt";
	}
	public Integer getQt() {
        return super.getInt(Columns.qt);
    }

    public ChargeCodeAndIsp setQt(Integer qt) {
    	super.set(Columns.qt, qt);
        return this;
    }
	public Integer getLt() {
        return super.getInt(Columns.lt);
    }

    public ChargeCodeAndIsp setLt(Integer lt) {
    	super.set(Columns.lt, lt);
        return this;
    }
	public Integer getYd() {
        return super.getInt(Columns.yd);
    }

    public ChargeCodeAndIsp setYd(Integer yd) {
    	super.set(Columns.yd, yd);
        return this;
    }
	public Integer getDx() {
        return super.getInt(Columns.dx);
    }

    public ChargeCodeAndIsp setDx(Integer dx) {
    	super.set(Columns.dx, dx);
        return this;
    }
    public String getCodeName() {
        return super.getStr(Columns.code_name);
    }

    public ChargeCodeAndIsp setCodeName(String code_name) {
    	super.set(Columns.code_name, code_name);
        return this;
    }
    public String getId() {
        return super.getStr(Columns.id);
    }

    public ChargeCodeAndIsp setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }
    
}