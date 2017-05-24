package com.larva.dao;

import com.larva.model.AppInfLog;
import com.larva.model.LogOrder;
import com.larva.model.ReqLogOrder;
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
	Record getVerCodeUrlById(String order_id);
	/**
	 * 入库日志记录
	 * @param logOrder
	 * @return
	 */
	Integer saveLogOrder(LogOrder logOrder);
	Integer saveReqLogOrder(ReqLogOrder logOrder);
	/**
	 * 更新日志记录状态
	 * @param id
	 * @param inState
	 * @param oldState
	 * @return
	 */
	Integer updateLogOrder(String id,int inState,int oldState);
	/**
	 * 根据imsi号获取areaid
	 * @param imsi
	 * @return
	 */
	String getAreaIdByImsi(String imsi);
	/**
	 * 更新app的日月限量统计
	 * @param app_id
	 * @return
	 */
	Integer updateAppCount(String app_id);
	/**
	 * 更新计费代码的日月限量统计
	 * @param charge_id
	 * @return
	 */
	Integer updateChargeCodeCount(String charge_id,String area_id);
	/**
	 * 根据orderNo更新定单状态
	 * @param orderNo
	 * @param orderState
	 * @return
	 */
	Integer updateLogOrderByOrderNo(String orderNo, Integer orderState);
	/**
	 * 根据id更新OrderNO
	 * @param id
	 * @param orderNo
	 * @return
	 */
	Integer updateOrderNoById(String id, String orderNo);
	/**
	 * 根据order_id查询是否存在需要处理的验证码请求
	 * @param order_id
	 * @return
	 */
	Record checkOrderId(String order_id);
	/**
	 * 根据charge_id查询运营商回调成功标示
	 * @param charge_id
	 * @return
	 */
	Record getCallBackSuccessById(String charge_id);
	/**
	 * 根据计费log id 查询是否有数据
	 * @param charge_key
	 * @return
	 */
	int getCountChargeLog(String charge_key);
	/**
	 * 记录客户端发送过来的日志
	 * @param aif
	 * @return
	 */
	int insertAppInfLog(AppInfLog aif);
	/**
	 * 根据id获取日志记录
	 * @param orderId
	 * @return
	 */
	LogOrder getLogOrderById(String orderId);
}
