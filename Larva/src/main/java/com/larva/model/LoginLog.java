package com.larva.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.larva.model.LogOrder.Columns;
import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="login_log",id="id", strategy = StrategyType.NULL)
public class LoginLog extends WeakEntity implements Serializable  {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id"; 
	    public static final String account = "account";
	    public static final String loginIp = "login_ip";
	    public static final String loginTime = "login_time";
	    public static final String detailAddress = "detail_address";
	    public static final String province = "province";
	    public static final String city = "city";
	    public static final String cityCode = "city_code";
	    public static final String address = "address";
	    public static final String pointX = "point_x";
	    public static final String pointY = "point_y";
	    public static final String showLoginTime = "showLoginTime";

	}
	public String getShowLoginTime() {
        return super.getStr(Columns.showLoginTime);
    }
    public LoginLog setShowLoginTime(String showLoginTime) {
        super.set(Columns.showLoginTime, showLoginTime);
        return this;
    }
	
	public BigDecimal getPointY() {
        return super.getBigDecimal(Columns.pointY);
    }
    public LoginLog setPointY(BigDecimal pointY) {
        super.set(Columns.pointY, pointY);
        return this;
    }
	
	public BigDecimal getPointX() {
        return super.getBigDecimal(Columns.pointX);
    }
    public LoginLog setPointX(BigDecimal pointX) {
        super.set(Columns.pointX, pointX);
        return this;
    }
	
	public String getAddress() {
        return super.getStr(Columns.address);
    }
    public LoginLog setAddress(String address) {
        super.set(Columns.address, address);
        return this;
    }
	
	public String getCityCode() {
        return super.getStr(Columns.cityCode);
    }
    public LoginLog setCityCode(String cityCode) {
        super.set(Columns.cityCode, cityCode);
        return this;
    }
	
	public String getCity() {
        return super.getStr(Columns.city);
    }
    public LoginLog setCity(String city) {
        super.set(Columns.city, city);
        return this;
    }
	
	public String getProvince() {
        return super.getStr(Columns.province);
    }
    public LoginLog setProvince(String province) {
        super.set(Columns.province, province);
        return this;
    }
	
	public String getDetailAddress() {
        return super.getStr(Columns.detailAddress);
    }
    public LoginLog setDetailAddress(String detailAddress) {
        super.set(Columns.detailAddress, detailAddress);
        return this;
    }
	
	public Date getLoginTime() {
        return super.getDate(Columns.loginTime);
    }
    public LoginLog setLoginTime(Date loginTime) {
        super.set(Columns.loginTime, loginTime);
        return this;
    }
	
	public String getLoginIp() {
        return super.getStr(Columns.loginIp);
    }
    public LoginLog setLoginIp(String loginIp) {
        super.set(Columns.loginIp, loginIp);
        return this;
    }
	
	public String getAccount() {
        return super.getStr(Columns.account);
    }
    public LoginLog setAccount(String account) {
        super.set(Columns.account, account);
        return this;
    }
    
	public String getId() {
        return super.getStr(Columns.id);
    }
    public LoginLog setId(String id) {
        super.set(Columns.id, id);
        return this;
    }
	
}