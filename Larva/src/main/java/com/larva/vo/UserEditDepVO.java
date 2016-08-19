package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-9-21.
 */
public class UserEditDepVO {
    @NotNull(message = "用户id不能为空")
    private Integer userId;
    @NotNull(message = "部门id不能为空")
    private Integer depId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }
}
