package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="account_role",id="id",strategy = StrategyType.NULL)
public class AccountRole extends WeakEntity implements Serializable {
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//account_role.id (用户角色id)
		public static final String accountId = "account_id";//account_role.account_id (账号id)
		public static final String roleId = "role_id";//account_role.role_id (角色id)
	}
	

    public String getId() {
        return super.getStr(Columns.id);
    }

    public AccountRole setId(String id) {
    	super.set(Columns.id, id);
        return this;
    }

    public String getAccountId() {
        return super.getStr(Columns.accountId);
    }

    public AccountRole setAccountId(String accountId) {
    	super.set(Columns.accountId, accountId);
        return this;
    }

    public String getRoleId() {
        return super.getStr(Columns.roleId);
    }

    public AccountRole setRoleId(String roleId) {
    	super.set(Columns.roleId, roleId);
        return this;
    }
}