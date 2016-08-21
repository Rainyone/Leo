package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-9-21.
 */
public class UserEditDepVO {
    @NotNull(message = "用户id不能为空")
    private String userId;
    @NotNull(message = "部门id不能为空")
    private String depId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }
}
