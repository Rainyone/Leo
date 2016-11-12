package com.larva.model;

import java.io.Serializable;
import java.util.Date;

import com.mini.core.WeakEntity;
import com.mini.core.annotation.Entity;
import com.mini.core.utils.EnumClazz.StrategyType;

@Entity(table="t_app_inf_log",id="id", strategy = StrategyType.NULL)
public class AppInfLog extends WeakEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 表字段定义静态类
	 */
	public static final class Columns {
		public static final String id = "id";
		public static final String charge_key = "charge_key";
		public static final String imsi = "imsi";
		public static final String channel = "channel";
		public static final String logtime = "logtime";
		public static final String stepname = "stepname";
		public static final String context = "context";
		public static final String create_time = "create_time";
		public static final String ip = "ip";
	}
    public String getIp() {
        return super.getStr(Columns.ip);
    }

    public AppInfLog setIp(String ip) {
        super.set(Columns.ip, ip);
        return this;
    }
    public Date getCreateTime() {
        return super.getDate(Columns.create_time);
    }

    public AppInfLog setCreateTime(Date create_time) {
        super.set(Columns.create_time, create_time);
        return this;
    }
    public String getContext() {
        return super.getStr(Columns.context);
    }

    public AppInfLog setContext(String context) {
        super.set(Columns.context, context);
        return this;
    }
    public String getStepname() {
        return super.getStr(Columns.stepname);
    }

    public AppInfLog setStepname(String stepname) {
        super.set(Columns.stepname, stepname);
        return this;
    }
    public Date getLogtime() {
        return super.getDate(Columns.logtime);
    }

    public AppInfLog setLogtime(Date logtime) {
        super.set(Columns.logtime, logtime);
        return this;
    }
    public String getChannel() {
        return super.getStr(Columns.channel);
    }

    public AppInfLog setChannel(String channel) {
        super.set(Columns.channel, channel);
        return this;
    }
    public String getImsi() {
        return super.getStr(Columns.imsi);
    }

    public AppInfLog setImsi(String imsi) {
        super.set(Columns.imsi, imsi);
        return this;
    }
    public String getChargeKey() {
        return super.getStr(Columns.charge_key);
    }

    public AppInfLog setChargerKey(String charge_key) {
        super.set(Columns.charge_key, charge_key);
        return this;
    }
    public String getId() {
        return super.getStr(Columns.id);
    }

    public AppInfLog setId(String id) {
        super.set(Columns.id, id);
        return this;
    }

}