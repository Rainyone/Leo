package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="app_and_code",id="id",strategy = StrategyType.NULL)
public class AppAndChargeCode extends WeakEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String app_code_id = "app_code_id";
		public static final String app_id = "app_id";
		public static final String app_name = "app_name";
		public static final String charge_code_id = "charge_code_id";
		public static final String charge_code_name = "charge_code_name";
	}
	public String getChargeCodeName() {
        return super.getStr(Columns.charge_code_name);
    }

    public AppAndChargeCode setChargeCodeName(String charge_code_name) {
    	super.set(Columns.charge_code_name, charge_code_name);
        return this;
    }
	public String getChargeCodeId() {
        return super.getStr(Columns.charge_code_id);
    }

    public AppAndChargeCode setChargeCodeId(String charge_code_id) {
    	super.set(Columns.charge_code_id, charge_code_id);
        return this;
    }
	public String getAppCodeId() {
        return super.getStr(Columns.app_code_id);
    }

    public AppAndChargeCode setAppCodeId(String app_code_id) {
    	super.set(Columns.app_code_id, app_code_id);
        return this;
    }
	public String getAppId() {
        return super.getStr(Columns.app_id);
    }

    public AppAndChargeCode setAppId(String app_id) {
    	super.set(Columns.app_id, app_id);
        return this;
    }
	public String getAppName() {
        return super.getStr(Columns.app_name);
    }

    public AppAndChargeCode setAppName(String app_name) {
    	super.set(Columns.app_name, app_name);
        return this;
    }
	
}