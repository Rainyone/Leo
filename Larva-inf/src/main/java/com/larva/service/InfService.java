package com.larva.service;

import com.larva.vo.ResultVO;

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
	String getVerCodeUrlById(String code_id);
}
