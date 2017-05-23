package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_charge_code",id="id", strategy = StrategyType.NULL)
public class ChargeCode extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String codeName = "code_name";
		public static final String url = "url";
		public static final String chargeCode = "charge_code";
		public static final String sendType = "send_type";
		public static final String infType = "inf_type";
		public static final String backMsgType = "back_msg_type";
		public static final String orderBack = "order_back";
		public static final String backForm = "back_form";
		public static final String returnForm = "return_form";
		public static final String verCodeUrl = "ver_code_url";
		public static final String dateLimit = "date_limit";
		public static final String monthLimit = "month_limit";
		public static final String channelType = "channel_type";
		public static final String linkeName = "linke_name";
		public static final String phoneNo = "phone_no";
		public static final String detail = "detail";
		public static final String state = "state";
		public static final String createTime = "create_time";
		public static final String createPeopleName = "create_people_name";
		public static final String updateTime = "update_time";
		public static final String updatePeopleName = "update_people_name";
		public static final String dateCount = "date_count";
		public static final String monthCount = "month_count";
		public static final String successFlag = "success_flag";
		public static final String orderIdCode = "order_id_code";
		public static final String verCodeSuccessFlag = "ver_code_success_flag";
		public static final String callbackurl = "callbackurl";
		public static final String callbacksuccess = "callbacksuccess";
		public static final String callbackcolumn = "callbackcolumn";
		public static final String key_msg = "key_msg";
		public static final String charge_price = "charge_price";
		public static final String is_limit = "is_limit";

	}

	public Long getChargePrice() {
        return super.getLong(Columns.charge_price);
    }

    public ChargeCode setChargePrice(Long charge_price) {
        super.set(Columns.charge_price, charge_price);
        return this;
    }
	public String getKeyMsg() {
        return super.getStr(Columns.key_msg);
    }

    public ChargeCode setKeyMsg(String key_msg) {
        super.set(Columns.key_msg, key_msg);
        return this;
    }
	public String getCallbackcolumn() {
        return super.getStr(Columns.callbackcolumn);
    }

    public ChargeCode setCallbackcolumn(String callbackcolumn) {
        super.set(Columns.callbackcolumn, callbackcolumn);
        return this;
    }
	public String getCallbacksuccess() {
        return super.getStr(Columns.callbacksuccess);
    }

    public ChargeCode setCallbacksuccess(String callbacksuccess) {
        super.set(Columns.callbacksuccess, callbacksuccess);
        return this;
    }
	public String getCallbackurl() {
        return super.getStr(Columns.callbackurl);
    }

    public ChargeCode setCallbackurl(String callbackurl) {
        super.set(Columns.callbackurl, callbackurl);
        return this;
    }
	public String getVerCodeSuccessFlag() {
        return super.getStr(Columns.verCodeSuccessFlag);
    }

    public ChargeCode setVerCodeSuccessFlag(String verCodeSuccessFlag) {
        super.set(Columns.verCodeSuccessFlag, verCodeSuccessFlag);
        return this;
    }
	public String getOrderIdCode() {
        return super.getStr(Columns.orderIdCode);
    }

    public ChargeCode setOrderIdCode(String orderIdCode) {
        super.set(Columns.orderIdCode, orderIdCode);
        return this;
    }
	public String getSuccessFlag() {
        return super.getStr(Columns.successFlag);
    }

    public ChargeCode setSuccessFlag(String successFlag) {
        super.set(Columns.successFlag, successFlag);
        return this;
    }
	public Integer getMonthCount() {
        return super.getInt(Columns.monthCount);
    }

    public ChargeCode setMonthCount(Integer monthCount) {
        super.set(Columns.monthCount, monthCount);
        return this;
    }
	public Integer getDateCount() {
        return super.getInt(Columns.dateCount);
    }

    public ChargeCode setDateCount(Integer dateCount) {
        super.set(Columns.dateCount, dateCount);
        return this;
    }
	public String getUpdatePeopleName() {
        return super.getStr(Columns.updatePeopleName);
    }

    public ChargeCode setUpdatePeopleName(String updatePeopleName) {
        super.set(Columns.updatePeopleName, updatePeopleName);
        return this;
    }
	public Date getUpdateTime() {
        return super.getDate(Columns.updateTime);
    }

    public ChargeCode setUpdateTime(Date updateTime) {
        super.set(Columns.updateTime, updateTime);
        return this;
    }
	public String getCreatePeopleName() {
        return super.getStr(Columns.createPeopleName);
    }

    public ChargeCode setCreatePeopleName(String createPeopleName) {
        super.set(Columns.createPeopleName, createPeopleName);
        return this;
    }

	public Date getCreateTime() {
        return super.getDate(Columns.createTime);
    }

    public ChargeCode setCreateTime(Date createTime) {
        super.set(Columns.createTime, createTime);
        return this;
    }
	public String getDetail() {
        return super.getStr(Columns.detail);
    }

    public ChargeCode setDetail(String detail) {
        super.set(Columns.detail, detail);
        return this;
    }
	public String getPhoneNo() {
        return super.getStr(Columns.phoneNo);
    }

    public ChargeCode setPhoneNo(String phoneNo) {
        super.set(Columns.phoneNo, phoneNo);
        return this;
    }
	public String getLinkeName() {
        return super.getStr(Columns.linkeName);
    }

    public ChargeCode setLinkeName(String linkeName) {
        super.set(Columns.linkeName, linkeName);
        return this;
    }
	public Integer getChannelType() {
        return super.getInt(Columns.channelType);
    }

    public ChargeCode setChannelType(Integer channelType) {
        super.set(Columns.channelType, channelType);
        return this;
    }
	public Integer getMonthLimit() {
        return super.getInt(Columns.monthLimit);
    }

    public ChargeCode setMonthLimit(Integer monthLimit) {
        super.set(Columns.monthLimit, monthLimit);
        return this;
    }
    public String getId() {
        return super.getStr(Columns.id);
    }

    public ChargeCode setId(String id) {
        super.set(Columns.id, id);
        return this;
    }
    public Integer getState() {
        return super.getInt(Columns.state);
    }

    public ChargeCode setState(Integer state) {
        super.set(Columns.state, state);
        return this;
    }
	public String getCodeName() {
		return super.getStr(Columns.codeName);
	}
	public ChargeCode setCodeName(String codeName) {
        super.set(Columns.codeName, codeName);
        return this;
    }
	public String getUrl() {
		return super.getStr(Columns.url);
	}
	public ChargeCode setUrl(String url) {
        super.set(Columns.url, url);
        return this;
    }
	public String getChargeCode() {
		return super.getStr(Columns.chargeCode); 
	}
	public ChargeCode setChargeCode(String chargeCode) {
        super.set(Columns.chargeCode, chargeCode);
        return this;
    }
	public String getSendType() {
		return super.getStr(Columns.sendType);
	}
	public ChargeCode setSendType(String sendType) {
        super.set(Columns.sendType, sendType);
        return this;
    }
	public Integer getInfType() {
		return super.getInt(Columns.infType); 
	}
	public ChargeCode setInfType(Integer infType) {
        super.set(Columns.infType, infType);
        return this;
    }
	public String getBackMsgType() {
		return super.getStr(Columns.backMsgType);
	}
	public ChargeCode setBackMsgType(String backMsgType) {
        super.set(Columns.backMsgType, backMsgType);
        return this;
    }
	public String getOrderBack() {
		return super.getStr(Columns.orderBack); 
	}
	public ChargeCode setOrderBack(String orderBack) {
        super.set(Columns.orderBack, orderBack);
        return this;
    }
	public String getBackForm() {
		return super.getStr(Columns.backForm);
	}
	public ChargeCode setBackForm(String backForm) {
        super.set(Columns.backForm, backForm);
        return this;
    }
	public String getReturnForm() {
		return super.getStr(Columns.returnForm); 
	}
	public ChargeCode setReturnForm(String returnForm) {
        super.set(Columns.returnForm, returnForm);
        return this;
    }
	public String getVerCodeUrl() {
		return super.getStr(Columns.verCodeUrl); 
	}
	public ChargeCode setVerCodeUrl(String verCodeUrl) {
        super.set(Columns.verCodeUrl, verCodeUrl);
        return this;
    }
	public Integer getDateLimit() {
		return super.getInt(Columns.dateLimit); 
	}
	public ChargeCode setDateLimit(Integer dateLimit) {
        super.set(Columns.dateLimit, dateLimit);
        return this;
    }
	public Integer getIsLimit() {
        return super.getInt(Columns.is_limit);
    }

    public ChargeCode setIsLimit(Integer isLimit) {
        super.set(Columns.is_limit, isLimit);
        return this;
    }
}