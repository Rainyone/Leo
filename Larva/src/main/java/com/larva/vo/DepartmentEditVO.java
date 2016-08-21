package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-8-30.
 */
public class DepartmentEditVO {
    @NotNull(message = "部门id不能为空")
    private String id;
    @NotNull(message = "上级部门不能为空")
    private String parentId;
    @NotNull(message = "部门名字不能为空")
    private String name;
    @NotNull(message = "部门排序不能为空")
    private Integer order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
