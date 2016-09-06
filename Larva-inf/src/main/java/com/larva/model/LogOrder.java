package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_log_order",id="id", strategy = StrategyType.NULL)
public class LogOrder extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id"; 
		public static final String mid = "mid"; 
		public static final String order_no = "order_no"; 
		public static final String app_id = "app_id"; 
		public static final String area_id = "area_id"; 
		public static final String isp_id = "isp_id";
		public static final String charge_code_id = "charge_code_id"; 
		public static final String imei = "imei";
		public static final String imsi = "imsi";
		public static final String ip = "ip";
		public static final String bsc_lac = "bsc_lac";
		public static final String bsc_cid = "bsc_cid";
		public static final String mobile = "mobile";
		public static final String iccid = "iccid";
		public static final String mac = "mac";
		public static final String cpparm = "cpparm";
		public static final String fmt = "fmt";
		public static final String price = "price";
		public static final String order_state = "order_state";
		public static final String create_time = "create_time";
		public static final String update_time = "update_time";
	}
	public Date getUpdateTime() {
        return super.getDate(Columns.update_time);
    }
    public LogOrder setUpdateTime(Date update_time) {
        super.set(Columns.update_time, update_time);
        return this;
    }
	public Date getCreateTime() {
        return super.getDate(Columns.create_time);
    }
    public LogOrder setCreateTime(Date create_time) {
        super.set(Columns.create_time, create_time);
        return this;
    }
	public Integer getOrderState() {
        return super.getInt(Columns.order_state);
    }
    public LogOrder setOrderState(Integer order_state) {
        super.set(Columns.order_state, order_state);
        return this;
    }
    public String getPrice() {
        return super.getStr(Columns.price);
    }
    public LogOrder setPrice(String price) {
        super.set(Columns.price, price);
        return this;
    }

    public String getFmt() {
        return super.getStr(Columns.fmt);
    }
    public LogOrder setFmt(String fmt) {
        super.set(Columns.fmt, fmt);
        return this;
    }
	public String getCpparm() {
        return super.getStr(Columns.cpparm);
    }
    public LogOrder setCpparm(String cpparm) {
        super.set(Columns.cpparm, cpparm);
        return this;
    }
	public String getMac() {
        return super.getStr(Columns.mac);
    }
    public LogOrder setMac(String mac) {
        super.set(Columns.mac, mac);
        return this;
    }
    
	public String getIccid() {
        return super.getStr(Columns.iccid);
    }
    public LogOrder setIccid(String iccid) {
        super.set(Columns.iccid, iccid);
        return this;
    }
	public String getMobile() {
        return super.getStr(Columns.mobile);
    }
    public LogOrder setMobile(String mobile) {
        super.set(Columns.mobile, mobile);
        return this;
    }
    public String getBscCid() {
        return super.getStr(Columns.bsc_cid);
    }

    public LogOrder setBscCid(String bsc_cid) {
        super.set(Columns.bsc_cid, bsc_cid);
        return this;
    }
	public String getBscLac() {
        return super.getStr(Columns.bsc_lac);
    }

    public LogOrder setBscLac(String bsc_lac) {
        super.set(Columns.bsc_lac, bsc_lac);
        return this;
    }
	public String getIp() {
        return super.getStr(Columns.ip);
    }

    public LogOrder setIp(String ip) {
        super.set(Columns.ip, ip);
        return this;
    }
    public String getImsi() {
        return super.getStr(Columns.imsi);
    }
    public LogOrder setImsi(String imsi) {
        super.set(Columns.imsi, imsi);
        return this;
    }
	public String getImei() {
        return super.getStr(Columns.imei);
    }

    public LogOrder setImei(String imei) {
        super.set(Columns.imei, imei);
        return this;
    }
	public String getChargeCodeId() {
        return super.getStr(Columns.charge_code_id);
    }

    public LogOrder setChargeCodeId(String charge_code_id) {
        super.set(Columns.charge_code_id, charge_code_id);
        return this;
    }

	public String getIspId() {
        return super.getStr(Columns.isp_id);
    }

    public LogOrder setIspId(String isp_id) {
        super.set(Columns.isp_id, isp_id);
        return this;
    }
	public String getAreaId() {
        return super.getStr(Columns.area_id);
    }

    public LogOrder setAreaId(String area_id) {
        super.set(Columns.area_id, area_id);
        return this;
    }
	public String getAppId() {
        return super.getStr(Columns.app_id);
    }

    public LogOrder setAppId(String app_id) {
        super.set(Columns.app_id, app_id);
        return this;
    }
    public String getOrderNo() {
        return super.getStr(Columns.order_no);
    }

    public LogOrder setOrderNo(String order_no) {
        super.set(Columns.order_no, order_no);
        return this;
    }
    public String getMid() {
        return super.getStr(Columns.mid);
    }

    public LogOrder setMid(String mid) {
        super.set(Columns.mid, mid);
        return this;
    }
    public String getId() {
        return super.getStr(Columns.id);
    }

    public LogOrder setId(String id) {
        super.set(Columns.id, id);
        return this;
    }
}