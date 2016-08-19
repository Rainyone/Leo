package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-8-30.
 */
public class PermissionCreateVO {
    @NotNull(message = "父级权限不能为空")
    private Integer parentId;
    @NotNull(message = "权限名字不能为空")
    private String name;
    @NotNull(message = "权限键值不能为空")
    private String key;
    @NotNull(message = "权限排序不能为空")
    private Integer order;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
