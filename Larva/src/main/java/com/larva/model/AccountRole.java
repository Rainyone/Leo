package com.larva.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="account_role",id="id",strategy = StrategyType.AUTO)
public class AccountRole extends WeakEntity implements Serializable {
	
	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//account_role.id (用户角色id)
		public static final String accountId = "account_id";//account_role.account_id (账号id)
		public static final String roleId = "role_id";//account_role.role_id (角色id)
	}
	

    public Integer getId() {
        return super.getInt(Columns.id);
    }

    public AccountRole setId(Integer id) {
    	super.set(Columns.id, id);
        return this;
    }

    public Integer getAccountId() {
        return super.getInt(Columns.accountId);
    }

    public AccountRole setAccountId(Integer accountId) {
    	super.set(Columns.accountId, accountId);
        return this;
    }

    public Integer getRoleId() {
        return super.getInt(Columns.roleId);
    }

    public AccountRole setRoleId(Integer roleId) {
    	super.set(Columns.roleId, roleId);
        return this;
    }
}