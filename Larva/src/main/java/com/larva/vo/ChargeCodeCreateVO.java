package com.larva.vo;

import javax.validation.constraints.NotNull;

/**
 */
public class ChargeCodeCreateVO {
    @NotNull(message = "计费代码名称不能为空")
    private String code_name = "code_name";
    @NotNull(message = "请求URL不能为空")
    private String url = "url";
    private String charge_code = "charge_code";
	private String[] send_type;
    private String[] inf_type;
	private String[] back_msg_type;
	private String order_back;
	private String back_form;
	private String return_form;
	private String ver_code_url;	
	private Integer date_limit=-1;
	private Integer month_limit = -1;
	private String[] channel_type;
    private String linke_name = "联系人";
    private String phone_no = "联系电话";
	private String detail = "detail";
    private String success_flag = "判断成功字符串";
    private String order_id_code = "验证码的order_id字段";
    private String ver_code_success_flag ;
	private String create_people_name;
    private String update_people_name;
    private String callbackurl ;
	private String callbacksuccess;
    private String callbackcolumn;
    
    
    public String getCallbackurl() {
		return callbackurl;
	}
	public void setCallbackurl(String callbackurl) {
		this.callbackurl = callbackurl;
	}
	public String getCallbacksuccess() {
		return callbacksuccess;
	}
	public void setCallbacksuccess(String callbacksuccess) {
		this.callbacksuccess = callbacksuccess;
	}
	public String getCallbackcolumn() {
		return callbackcolumn;
	}
	public void setCallbackcolumn(String callbackcolumn) {
		this.callbackcolumn = callbackcolumn;
	}
	public String getVer_code_success_flag() {
		return ver_code_success_flag;
	}
	public void setVer_code_success_flag(String ver_code_success_flag) {
		this.ver_code_success_flag = ver_code_success_flag;
	}
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
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCharge_code() {
		return charge_code;
	}
	public void setCharge_code(String charge_code) {
		this.charge_code = charge_code;
	}
	public String[] getSend_type() {
		return send_type;
	}
	public void setSend_type(String[] send_type) {
		this.send_type = send_type;
	}
	public String[] getInf_type() {
		return inf_type;
	}
	public String[] getBack_msg_type() {
		return back_msg_type;
	}
	public void setBack_msg_type(String[] back_msg_type) {
		this.back_msg_type = back_msg_type;
	}
	public String getOrder_back() {
		return order_back;
	}
	public void setOrder_back(String order_back) {
		this.order_back = order_back;
	}
	public void setInf_type(String[] inf_type) {
		this.inf_type = inf_type;
	}
	public String[] getChannel_type() {
		return channel_type;
	}
	public void setChannel_type(String[] channel_type) {
		this.channel_type = channel_type;
	}
	public String getBack_form() {
		return back_form;
	}
	public void setBack_form(String back_form) {
		this.back_form = back_form;
	}
	public String getReturn_form() {
		return return_form;
	}
	public void setReturn_form(String return_form) {
		this.return_form = return_form;
	}
	public String getVer_code_url() {
		return ver_code_url;
	}
	public void setVer_code_url(String ver_code_url) {
		this.ver_code_url = ver_code_url;
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
	public String getLinke_name() {
		return linke_name;
	}
	public void setLinke_name(String linke_name) {
		this.linke_name = linke_name;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getSuccess_flag() {
		return success_flag;
	}
	public void setSuccess_flag(String success_flag) {
		this.success_flag = success_flag;
	}
	public String getOrder_id_code() {
		return order_id_code;
	}
	public void setOrder_id_code(String order_id_code) {
		this.order_id_code = order_id_code;
	}
    
    
}
