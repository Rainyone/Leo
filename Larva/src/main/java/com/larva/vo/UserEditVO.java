package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-9-18.
 */
public class UserEditVO {
    @NotNull(message = "账号格式错误")
    private String account;
    @NotNull(message = "密码格式错误")
    private String  password;
    @NotNull(message = "请选择部门")
    private String dep;

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
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
}
