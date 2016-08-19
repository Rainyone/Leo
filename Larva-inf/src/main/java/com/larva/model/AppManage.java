package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="app_manage",id="id", strategy = StrategyType.AUTO)
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
		public static final String app_name = "app_name";
		public static final String app_package_name = "app_package_name";
		public static final String link_name = "link_name";
		public static final String phone_no = "phone_no";
		public static final String description = "description";
		public static final String create_time = "create_time";
		public static final String create_people_name = "create_people_name";
		public static final String update_time = "update_time";
		public static final String update_people_name = "update_people_name";
		public static final String state = "state";
	}
    public Integer getId() {
        return super.getInt(Columns.id);
    }

    public AppManage setId(Integer id) {
        super.set(Columns.id, id);
        return this;
    }
    public Integer getState() {
        return super.getInt(Columns.state);
    }

    public AppManage setState(Integer state) {
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
	public String getCreatePeopleName() {
		return super.getStr(Columns.create_people_name); 
	}
	public AppManage setCreatePeopleName(String create_people_name) {
        super.set(Columns.create_people_name, create_people_name);
        return this;
    }
	public Date getUpdateTime() {
		return super.getDate(Columns.update_time);
	}
	public AppManage setUpdateTime(Date update_time) {
        super.set(Columns.update_time, update_time);
        return this;
    }
	public String getUpdatePeopleName() {
		return super.getStr(Columns.update_people_name); 
	}
	public AppManage setUpdatePeopleName(Date update_people_name) {
        super.set(Columns.update_people_name, update_people_name);
        return this;
    }
    
}