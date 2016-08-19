package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 */
public class AppManageCreateVO {
    @NotNull(message = "APP名称不能为空")
    private String app_name = "app_name";
    @NotNull(message = "APP包名不能为空")
    private String app_package_name = "app_package_name";
	private String link_name = "link_name";
    private String phone_no = "phone_no";
    private String description = "description";
    
    public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getApp_package_name() {
		return app_package_name;
	}
	public void setApp_package_name(String app_package_name) {
		this.app_package_name = app_package_name;
	}
	public String getLink_name() {
		return link_name;
	}
	public void setLink_name(String link_name) {
		this.link_name = link_name;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
