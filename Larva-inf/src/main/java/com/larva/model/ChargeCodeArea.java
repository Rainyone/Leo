package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_charge_code_area",id="id", strategy = StrategyType.NULL)
public class ChargeCodeArea extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String update_time = "update_time";
	}
    public String getId() {
        return super.getStr(Columns.id);
    }

    public ChargeCodeArea setId(String id) {
        super.set(Columns.id, id);
        return this;
    }

    public Date getUpdateTime() {
        return super.get(Columns.update_time);
    }

    public ChargeCodeArea setUpdateTime(Date update_time) {
    	super.set(Columns.update_time, update_time);
    	return this;
    }
}