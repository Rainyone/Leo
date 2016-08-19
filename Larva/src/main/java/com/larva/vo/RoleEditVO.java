package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-8-30.
 */
public class RoleEditVO {
    @NotNull(message = "角色id不能为空")
    private Integer id;

    @NotNull(message = "角色名字不能为空")
    private String name;
    @NotNull(message = "角色键值不能为空")
    private String key;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
