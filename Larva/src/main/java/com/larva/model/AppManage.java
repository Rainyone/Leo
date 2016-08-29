package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_app_manage",id="id", strategy = StrategyType.NULL)
public class AppManage extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String app_key = "app_key";
		public static final String channel = "channel";
		public static final String app_name = "app_name";
		public static final String app_package_name = "app_package_name";
		public static final String date_limit = "date_limit";
		public static final String month_limit = "month_limit";
		public static final String link_name = "link_name";
		public static final String phone_no = "phone_no";
		public static final String description = "description";
		public static final String create_time = "create_time";
		public static final String create_user_name = "create_user_name";
		public static final String update_time = "update_time";
		public static final String update_user_name = "update_user_name";
		public static final String state = "state";
		public static final String date_count = "date_count";
		public static final String month_count = "month_count";
	}
    public String getId() {
        return super.getStr(Columns.id);
    }

    public AppManage setId(String id) {
        super.set(Columns.id, id);
        return this;
    }
    public String getAppKey() {
    	return super.getStr(Columns.app_key);
	}
	public AppManage setAppKey(String appKey) {
		super.set(Columns.app_key, appKey);
		return this;
	}
	public String getChannel() {
		return super.getStr(Columns.channel);
	}
	public AppManage setChannel(String channel) {
		super.set(Columns.channel, channel);
		return this;
	}
	public int getDateLimit() {
		return super.getInt(Columns.date_limit);
	}
	public AppManage setDateLimit(int dateLimit) {
		super.set(Columns.date_limit, dateLimit);
		return this;
	}
	public int getMonthLimit() {
		return super.getInt(Columns.month_limit);
	}
	public AppManage setMonthLimit(int monthLimit) {
		super.set(Columns.month_limit, monthLimit);
		return this;
	}
	public String getCreateUserName() {
		return super.getStr(Columns.create_user_name);
	}
	public AppManage setCreateUserName(String createUserName) {
		super.set(Columns.create_user_name, createUserName);
		return this;
	}
	public String getUpdateUserName() {
		return super.getStr(Columns.update_user_name);
	}
	public AppManage setUpdateUserName(String updateUserName) {
		super.set(Columns.update_user_name, updateUserName);
		return this;
	}
	public int getDateCount() {
		return super.getInt(Columns.date_count);
	}
	public AppManage setDateCount(int dateCount) {
		super.set(Columns.date_count, dateCount);
		return this;
	}
	public int getMonthCount() {
		return super.getInt(Columns.month_count);
	}
	public AppManage setMonthCount(int monthCount) {
		super.set(Columns.month_count, monthCount);
		return this;
	}
    public String getState() {
        return super.getStr(Columns.state);
    }

    public AppManage setState(String state) {
        super.set(Columns.state, state);
        return this;
    }
	public String getAppName() {
		return super.getStr(Columns.app_name);
	}
	public AppManage setAppName(String app_name) {
        super.set(Columns.app_name, app_name);
        return this;
    }
	public String getAppPackageName() {
		return super.getStr(Columns.app_package_name);
	}
	public AppManage setAppPackageName(String app_package_name) {
        super.set(Columns.app_package_name, app_package_name);
        return this;
    }
	public String getLinkName() {
		return super.getStr(Columns.link_name); 
	}
	public AppManage setLinkName(String link_name) {
        super.set(Columns.link_name, link_name);
        return this;
    }
	public String getPhoneNo() {
		return super.getStr(Columns.phone_no);
	}
	public AppManage setPhoneNo(String phone_no) {
        super.set(Columns.phone_no, phone_no);
        return this;
    }
	public String getDescription() {
		return super.getStr(Columns.description); 
	}
	public AppManage setDescription(String description) {
        super.set(Columns.description, description);
        return this;
    }
	public Date getCreateTime() {
		return super.getDate(Columns.create_time);
	}
	public AppManage setCreateTime(Date create_time) {
        super.set(Columns.create_time, create_time);
        return this;
    }
	public Date getUpdateTime() {
		return super.getDate(Columns.update_time);
	}
	public AppManage setUpdateTime(Date update_time) {
        super.set(Columns.update_time, update_time);
        return this;
    }
    
}