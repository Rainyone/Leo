package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 * Created by sxjun on 15-8-30.
 */
public class MenuEditVO {
    @NotNull(message = "菜单id不能为空")
    private String id;
    private String parentId;
    @NotNull(message = "菜单名字不能为空")
    private String name;
    private String url;
    private String icon;
    @NotNull(message = "菜单排序不能为空")
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
    
    
}
