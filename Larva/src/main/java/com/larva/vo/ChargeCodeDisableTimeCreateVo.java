package com.larva.vo;

import javax.validation.constraints.NotNull;

public class ChargeCodeDisableTimeCreateVo {
	@NotNull(message = "计费代码Id不能为空")
    private String charge_code_id = "charge_code_id";
    @NotNull(message = "请求disable_start_time不能为空")
    private String[] disable_start_time;
    @NotNull(message = "请求disable_end_time不能为空")
    private String[] disable_end_time;	
    private String create_people_name;
    private String update_people_name;
    
	public String getCreate_people_name() {
		return create_people_name;
	}
	public void setCreate_people_name(String create_people_name) {
		this.create_people_name = create_people_name;
	}
	public String getUpdate_people_name() {
		return update_people_name;
	}
	public void setUpdate_people_name(String update_people_name) {
		this.update_people_name = update_people_name;
	}
	public String getCharge_code_id() {
		return charge_code_id;
	}
	public void setCharge_code_id(String charge_code_id) {
		this.charge_code_id = charge_code_id;
	}
	public String[] getDisable_start_time() {
		return disable_start_time;
	}
	public void setDisable_start_time(String[] disable_start_time) {
		this.disable_start_time = disable_start_time;
	}
	public String[] getDisable_end_time() {
		return disable_end_time;
	}
	public void setDisable_end_time(String[] disable_end_time) {
		this.disable_end_time = disable_end_time;
	}
    
    
}
