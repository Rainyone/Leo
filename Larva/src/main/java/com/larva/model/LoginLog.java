package com.larva.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.mini.core.BaseEntity;
import com.mini.core.WeakEntity;

public class LoginLog extends WeakEntity implements Serializable  {
    private Integer id;

    private String account;

    private String loginIp;

    private Date loginTime;

    private String detailAddress;

    private String province;

    private String city;

    private String cityCode;

    private String address;

    private BigDecimal pointX;

    private BigDecimal pointY;

    private String showLoginTime;

    public String getShowLoginTime() {
        return showLoginTime;
    }

    public void setShowLoginTime(String showLoginTime) {
        this.showLoginTime = showLoginTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress == null ? null : detailAddress.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getPointX() {
        return pointX;
    }

    public void setPointX(BigDecimal pointX) {
        this.pointX = pointX;
    }

    public BigDecimal getPointY() {
        return pointY;
    }

    public void setPointY(BigDecimal pointY) {
        this.pointY = pointY;
    }
}