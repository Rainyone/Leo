package com.larva.vo;

public class OrderVo {
	private String order_id;
	private String code_name;
	private String app_name;
	private String mobile;
	private String datetimeStart;
	private String datetimeEnd;
	private int order_state=-1;
	
	public int getOrder_state() {
		return order_state;
	}
	public void setOrder_state(int order_state) {
		this.order_state = order_state;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDatetimeStart() {
		return datetimeStart;
	}
	public void setDatetimeStart(String datetimeStart) {
		this.datetimeStart = datetimeStart;
	}
	public String getDatetimeEnd() {
		return datetimeEnd;
	}
	public void setDatetimeEnd(String datetimeEnd) {
		this.datetimeEnd = datetimeEnd;
	}
	
	
}
