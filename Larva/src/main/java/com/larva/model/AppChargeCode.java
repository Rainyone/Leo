package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_app_charge_code",id="id",strategy = StrategyType.NULL)
public class AppChargeCode extends WeakEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String app_id = "app_id";
		public static final String charge_code_id = "charge_code_id";
		public static final String description = "description";
		public static final String state = "state";
		public static final String create_time = "create_time";
		public static final String create_people_name = "create_people_name";
		public static final String update_time = "update_time";
		public static final String update_people_name = "update_people_name";
	}
	public String getUpdatePeopleName() {
        return super.getStr(Columns.update_people_name);
    }
    public AppChargeCode setUpdatePeopleName(String update_people_name) {
    	super.set(Columns.update_people_name, update_people_name);
        return this;
    }
	public Date getUpdateTime() {
        return super.getDate(Columns.update_time);
    }
    public AppChargeCode setUpdateTime(Date update_time) {
    	super.set(Columns.update_time, update_time);
        return this;
    }
	public String getCreatePeopleName() {
        return super.getStr(Columns.create_people_name);
    }

    public AppChargeCode setCreatePeopleName(String create_people_name) {
    	super.set(Columns.create_people_name, create_people_name);
        return this;
    }
	public Date getCreateTime() {
        return super.getDate(Columns.create_time);
    }
    public AppChargeCode setCreateTime(Date create_time) {
    	super.set(Columns.create_time, create_time);
        return this;
    }
	public Integer getState() {
        return super.getInt(Columns.state);
    }
    public AppChargeCode setState(Integer state) {
    	super.set(Columns.state, state);
        return this;
    }
	public String getDescription() {
        return super.getStr(Columns.description);
    }

    public AppChargeCode setDescription(String description) {
    	super.set(Columns.description, description);
        return this;
    }
	public String getChargeCodeId() {
        return super.getStr(Columns.charge_code_id);
    }

    public AppChargeCode setChargeCodeId(String charge_code_id) {
    	super.set(Columns.charge_code_id, charge_code_id);
        return this;
    }
	public String getId() {
        return super.getStr(Columns.id);
    }

    public AppChargeCode setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }
	public String getAppId() {
        return super.getStr(Columns.app_id);
    }

    public AppChargeCode setAppId(String app_id) {
    	super.set(Columns.app_id, app_id);
        return this;
    }
}