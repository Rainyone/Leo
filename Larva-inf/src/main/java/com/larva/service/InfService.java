package com.larva.service;

import com.larva.model.LogOrder;
import com.larva.vo.ResultVO;
import com.mini.core.Record;

public interface InfService {
	/**
	 * appid+appKey校验，查数据库看此appid和key 是否可用，APP日月限量额度<br>
	 * 判断是否在可用省份内，APP省内日月限量额度
	 * @param app_id
	 * @param app_key
	 * @param area_id
	 * @param isp
	 * @return
	 */
	ResultVO checkApp(String app_id, String app_key , String area_id, String isp);
	/**
	 * 获取可用计费代码<br>
	 * 判断是否超出计费代码的日月限量，是否在可用区域内，<br>
	 * 是否到达区域日月限量，是否在可用运营商内，是否在可用是时间段内
	 * @param app_id
	 * @param area_id
	 * @param isp
	 * @return
	 */
	ResultVO checkAndGetChargeCode(String app_id, String area_id, String isp);
	/**
	 * 根据id获取验证码的请求url
	 * @param order_id
	 * @return
	 */
	Record getVerCodeUrlById(String code_id);
	/**
	 * 入库日志记录
	 * @param logOrder
	 * @return
	 */
	Integer saveLogOrder(LogOrder logOrder);
	/**
	 * 更新日志记录状态
	 * @param id
	 * @param inState
	 * @param oldState
	 * @return
	 */
	Integer updateLogOrder(String id,int inState,int oldState);
	/**
	 * 根据imsi号获取区域id
	 * @return
	 */
	String getAreaIdByImsi(String imsi);
	/**
	 * 更新每天、每月的限量统计数
	 * @return
	 */
	Integer updateCount(String app_id,String charge_id,String area_id);
	/**
	 * 根据orderNo更新状态
	 * @param orderNo
	 * @param orderState
	 */
	Integer updateLogOrderByOrderNo(String orderNo, Integer orderState);
	/**
	 * 入库order_id记录
	 * @param orderNo
	 * @param orderState
	 */
	Integer updateOrderNoById(String id, String orderNo);
	/**
	 * 根据order_id查询日志记录。判断是否有记录
	 * @param order_id
	 * @return
	 */
	ResultVO checkOrderId(String order_id);
	/**
	 * 根据计费代码id查询运营商回调成功标示
	 * @param charge_id
	 * @return
	 */
	Record getCallBackSuccessById(String charge_id);
}
