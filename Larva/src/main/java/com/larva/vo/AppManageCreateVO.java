package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 */
public class AppManageCreateVO {
    @NotNull(message = "APP名称不能为空")
    private String app_name = "app_name";
    @NotNull(message = "APP包名不能为空")
    private String app_package_name = "app_package_name";
    private String app_id = "app_id";
    private String app_key = "app_key";
    private String channel = "channel";
	private Integer date_limit;
    private Integer month_limit;
    private String state = "state";
    private Integer date_count;
    private Integer month_count;
	private String link_name = "link_name";
    private String phone_no = "phone_no";
    private String description = "description";
    private String create_user_name = "create_user_name";
    private String update_user_name = "update_user_name";
    
    public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getCreate_user_name() {
		return create_user_name;
	}
	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}
	public String getUpdate_user_name() {
		return update_user_name;
	}
	public void setUpdate_user_name(String update_user_name) {
		this.update_user_name = update_user_name;
	}
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
	
	 public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getDate_limit() {
		return date_limit;
	}
	public void setDate_limit(Integer date_limit) {
		this.date_limit = date_limit;
	}
	public Integer getMonth_limit() {
		return month_limit;
	}
	public void setMonth_limit(Integer month_limit) {
		this.month_limit = month_limit;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getDate_count() {
		return date_count;
	}
	public void setDate_count(Integer date_count) {
		this.date_count = date_count;
	}
	public Integer getMonth_count() {
		return month_count;
	}
	public void setMonth_count(Integer month_count) {
		this.month_count = month_count;
	}
}
