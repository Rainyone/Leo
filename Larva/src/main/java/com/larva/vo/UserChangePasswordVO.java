package com.larva.vo;

import javax.validation.constraints.NotNull;

public class UserChangePasswordVO {
    @NotNull(message = "账号格式错误")
    private String account;
    @NotNull(message = "密码格式错误")
    private String  password;
    @NotNull(message = "请输入原密码")
    private String old_password;
    
    public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "UserChangePasswordVO [account=" + account + ", password="
				+ password + ", old_password=" + old_password + "]";
	}
}
