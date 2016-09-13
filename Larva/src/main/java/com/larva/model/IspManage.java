package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_static_isp",id="id",strategy = StrategyType.NULL)
public class IspManage extends WeakEntity implements Serializable {
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String ispCode = "isp_code";
		public static final String ispName = "isp_name";
	}

    public String getId() {
        return super.getStr(Columns.id);
    }

    public IspManage setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getIspCode() {
        return super.getStr(Columns.ispCode);
    }

    public IspManage setIspCode(String ispCode) {
    	super.set(Columns.ispCode, ispCode);
        return this;
    }

    public String getIspName() {
        return super.getStr(Columns.ispName);
    }

    public IspManage setIspName(String ispName) {
    	super.set(Columns.ispName, ispName);
        return this;
    }
}