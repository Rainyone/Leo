package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="department_account",id="id",strategy = StrategyType.NULL)
public class DepartmentAccount extends WeakEntity implements Serializable {
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//department_account.id
		public static final String depId = "dep_id";//department_account.dep_id (部门id)
		public static final String accountId = "account_id";//department_account.account_id (账号id)
	}
	
    public String getId() {
        return super.getStr(Columns.id);
    }

    public DepartmentAccount setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getDepId() {
        return super.getStr(Columns.depId);
    }

    public DepartmentAccount setDepId(String depId) {
    	super.set(Columns.depId, depId);
        return this;
    }

    public String getAccountId() {
        return super.getStr(Columns.accountId);
    }

    public DepartmentAccount setAccountId(String accountId) {
    	super.set(Columns.accountId, accountId);
        return this;
    }
}