package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_charge_disable_time",id="id", strategy = StrategyType.NULL)
public class ChargeDisableTime extends WeakEntity implements Serializable {
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
		public static final String charge_code_id = "charge_code_id";
		public static final String disable_start_time = "disable_start_time";
		public static final String disable_end_time = "disable_end_time";
		public static final String state = "state";
		public static final String create_time = "create_time";
		public static final String create_people_name = "create_people_name";
		public static final String update_time = "update_time";
		public static final String update_people_name = "update_people_name";
	}
	public String getCodeName() {
        return super.getStr(Columns.code_name);
    }

    public ChargeDisableTime setCodeName(String code_name) {
        super.set(Columns.code_name, code_name);
        return this;
    }
	public String getUpdatePeopleName() {
        return super.getStr(Columns.update_people_name);
    }

    public ChargeDisableTime setUpdatePeopleName(String update_people_name) {
        super.set(Columns.update_people_name, update_people_name);
        return this;
    }
	public Date getUpdateTime() {
        return super.getDate(Columns.update_time);
    }

    public ChargeDisableTime setUpdateTime(Date update_time) {
        super.set(Columns.update_time, update_time);
        return this;
    }

	public String getCreatePeopleName() {
        return super.getStr(Columns.create_people_name);
    }

    public ChargeDisableTime setCreatePeopleName(String create_people_name) {
        super.set(Columns.create_people_name, create_people_name);
        return this;
    }
	public Date getCreateTime() {
        return super.getDate(Columns.create_time);
    }

    public ChargeDisableTime setCreateTime(Date create_time) {
        super.set(Columns.create_time, create_time);
        return this;
    }

	public Integer getState() {
        return super.getInt(Columns.state);
    }

    public ChargeDisableTime setState(Integer state) {
        super.set(Columns.state, state);
        return this;
    }
	public Date getDisableEndTime() {
        return super.getDate(Columns.disable_end_time);
    }

    public ChargeDisableTime setDisableEndTime(Date disable_end_time) {
        super.set(Columns.disable_end_time, disable_end_time);
        return this;
    }
	public Date getDisableStartTime() {
        return super.getDate(Columns.disable_start_time);
    }

    public ChargeDisableTime setDisableStartTime(Date disable_start_time) {
        super.set(Columns.disable_start_time, disable_start_time);
        return this;
    }
	public String getChargeCodeId() {
        return super.getStr(Columns.charge_code_id);
    }

    public ChargeDisableTime setChargeCodeId(String charge_code_id) {
        super.set(Columns.charge_code_id, charge_code_id);
        return this;
    }
	    
    public String getId() {
        return super.getStr(Columns.id);
    }

    public ChargeDisableTime setId(String id) {
        super.set(Columns.id, id);
        return this;
    }
    
}