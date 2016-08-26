package com.larva.dao;

import com.mini.core.Record;

import java.util.List;

public interface IInfDao  {
	/**
	 * 获取app并校验app_key
	 * @param app_id
	 * @param app_key
	 * @return
	 */
    Record get(String app_id,String app_key);
    List<Record> getList(String app_id,String area_id);
    /**
     * 获取可用区域，判断日限量、月限量
     * @param app_id
     * @param app_key
     * @return
     */
    Integer getAppAreaCount(String app_id,String area_id);
    /**
     * 判断是否为可用运营商
     * @param app_id
     * @param isp
     * @return
     */
    Integer getAppIspCount(String app_id,String isp);
    /**
     * 获取可用计费代码，判断计费代码的日月限量
     * @param app_id
     * @return
     */
	List<Record> getChargeCodes(String app_id);
	/**
	 * 获取可用区域，判断计费代码的区域日月限量
	 * @param id
	 * @param area_id
	 * @return
	 */
	Integer getChargeArea(String id, String area_id);
	/**
	 * 计费代码 判断可用运营商
	 * @param id
	 * @param isp
	 * @return
	 */
	Integer getChargeIsps(String id,String isp);
	/**
	 * 计费代码判断不可用时间
	 * @param id
	 * @return
	 */
	Integer getChargeDisableTimes(String id);
	/**
	 * 根据计费代码id获取验证码的请求url
	 * @param order_id
	 * @return
	 */
	String getVerCodeUrlById(String order_id);
}
