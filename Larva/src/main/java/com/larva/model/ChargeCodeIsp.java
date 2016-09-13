package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_charge_code_isp",id="id",strategy = StrategyType.NULL)
public class ChargeCodeIsp extends WeakEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4102519841976148315L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String charge_id = "charge_id";
		public static final String isp_id = "isp_id";
		public static final String state = "state";
		public static final String create_time = "create_time";
		public static final String create_people_name = "create_people_name";
		public static final String update_time = "update_time";
		public static final String update_people_name = "update_people_name";
	}
	public String getUpdatePeopleName() {
        return super.getStr(Columns.update_people_name);
    }

    public ChargeCodeIsp setUpdatePeopleName(String update_people_name) {
    	super.set(Columns.update_people_name, update_people_name);
        return this;
    }
	public Date getUpdateTime() {
        return super.getDate(Columns.update_time);
    }

    public ChargeCodeIsp setUpdateTime(Date update_time) {
    	super.set(Columns.update_time, update_time);
        return this;
    }
	public String getCreatePeopleName() {
        return super.getStr(Columns.create_people_name);
    }

    public ChargeCodeIsp setCreatePeopleName(String create_people_name) {
    	super.set(Columns.create_people_name, create_people_name);
        return this;
    }
	public Date getCreateTime() {
        return super.getDate(Columns.create_time);
    }

    public ChargeCodeIsp setCreateTime(Date create_time) {
    	super.set(Columns.create_time, create_time);
        return this;
    }
	public Integer getState() {
        return super.getInt(Columns.state);
    }

    public ChargeCodeIsp setState(Integer state) {
    	super.set(Columns.state, state);
        return this;
    }
	public String getIspId() {
        return super.getStr(Columns.isp_id);
    }

    public ChargeCodeIsp setIspId(String isp_id) {
    	super.set(Columns.isp_id, isp_id);
        return this;
    }
	public String getChargeId() {
        return super.getStr(Columns.charge_id);
    }

    public ChargeCodeIsp setChargeId(String charge_id) {
    	super.set(Columns.charge_id, charge_id);
        return this;
    }
    public String getId() {
        return super.getStr(Columns.id);
    }

    public ChargeCodeIsp setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

}