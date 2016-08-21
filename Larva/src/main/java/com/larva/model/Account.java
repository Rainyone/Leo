package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="account",id="id", strategy = StrategyType.NULL)
public class Account extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";//account.id (用户id)
		public static final String account = "account";//account.account (账号)
		public static final String password = "password";//account.password (密码)
		public static final String registerTime = "register_time";//account.register_time (注册时间)
	}
    public String getId() {
        return super.getStr(Columns.id);
    }

    public Account setId(String id) {
        super.set(Columns.id, id);
        return this;
    }

    public String getAccount() {
        return super.get(Columns.account);
    }

    public Account setAccount(String account) {
    	super.set(Columns.account, account);
    	return this;
    }

    public String getPassword() {
        return super.get(Columns.password);
    }

    public Account setPassword(String password) {
        super.set(Columns.password, password);
        return this;
    }

    public Date getRegisterTime() {
        return super.get(Columns.registerTime);
    }

    public Account setRegisterTime(Date registerTime) {
        super.set(Columns.registerTime, registerTime);
        return this;
    }
}