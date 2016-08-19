package com.larva.inf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.utils.IPSeeker;
import com.larva.utils.ServiceUtils;
import com.larva.vo.ResultVO;


@Controller
public class InfController {
	private Logger logger = Logger.getLogger(InfController.class);
	private static IPSeeker ipSeeker = IPSeeker.getInstance();
	@RequestMapping(value="/setCharge", method=RequestMethod.GET)
	@ResponseBody
    public ResultVO login(@RequestParam(value="appId", required=false, defaultValue="") String appId,
			@RequestParam(value="appSecret", required=false, defaultValue="") String appSecret,//验证app合法性
			@RequestParam(value="imsi", required=false, defaultValue="") String imsi,
			@RequestParam(value="imei", required=false, defaultValue="") String imei,
			@RequestParam(value="osVersion", required=false, defaultValue="") String osVersion,
			@RequestParam(value="platform", required=false, defaultValue="") String platform,
			@RequestParam(value="netState", required=false, defaultValue="") String netState,
			@RequestParam(value="ua", required=false, defaultValue="") String ua,
			@RequestParam(value="brand", required=false, defaultValue="") String brand,
			@RequestParam(value="advertType", required=false, defaultValue="") String advertType,//
			@RequestParam(value="adSize", required=false, defaultValue="") String adSize,
			@RequestParam(value="state", required=false, defaultValue="") String state,//不用
			@RequestParam(value="timestamp", required=false, defaultValue="") String timestamp,
			@RequestParam(value="sdkVersion", required=false, defaultValue="") String sdkVersion,
			@RequestParam(value="adId", required=false, defaultValue="") String adId,//
			@RequestParam(value="attachApp", required=false, defaultValue="") String attachApp,//智能广告展现的应用（值为包名）
			@RequestParam(value="event", required=false, defaultValue="") String event, //展示1，点击2，安装3
			HttpServletRequest request){
		ResultVO vo = new ResultVO(true);
		boolean result = true;
		String area = "";
		//参数判断
		if(StringUtils.isBlank(adId)) {
			vo.setOk(false);
			vo.setMsg("adId is null");
			result = false;
		}
		if(StringUtils.isBlank(imei)) {
			vo.setOk(false);
			vo.setMsg("imei is null");
			result = false;
		}
		if(StringUtils.isBlank(event)) {
			vo.setOk(false);
			vo.setMsg("event is null");
			result = false;
		}
		//appid+appKey校验，查数据库看此appid和key 是否可用
		if(this.checkKey(appId, appSecret)){
			vo.setOk(false);
			vo.setMsg("appKey is not available");
			result = false;
		}
		if(result){
			// TODO 逻辑判断，获取计费代码，请求计费代码，解析反馈数据，反馈客户端数据
			//获取ip地址
			String realIp = ServiceUtils.getRealAddr(request);
			logger.info("realIp:" + realIp);
			String address = ipSeeker.getAddress(realIp);
			//获取区域编码
			area = address.split(" ")[0];
			logger.info("area:" + area);
			//获取计费代码
			List<String> chargeCode = this.getChargeCode(appId,area);
			String backStr = "";
			//发送请求到运营商
			for(String one :chargeCode){
				String returnCode = this.reqYYS(one);
				backStr += this.analysis(returnCode); 
			}
			//反馈给客户端
			vo.setData(backStr);
			return vo;
		}else{
			return vo;
		}
	}
	/**
	 * 解析反馈的报文数据
	 * @param returnCode
	 * @return
	 */
	private String analysis(String returnCode) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 请求运营商
	 * @param one
	 * @return
	 */
	private String reqYYS(String one) {
		// TODO Auto-generated method stub
		return null;
	}
	@RequestMapping(value = "/fail")
	@ResponseBody
	public ResultVO fail() {
		ResultVO vo = new ResultVO(true);
		vo.setData(null);
		vo.setMsg("url error 2");
		return vo;
	}
	/**
	 * 校验appid和appkey
	 * @param appId
	 * @param appKey
	 * @return
	 */
	private boolean checkKey(String appId,String appKey){
		//TODO 查库校验
		return true;
	}
	/**
	 * 获取计费代码
	 * @param appId
	 * @param area
	 * @return
	 */
	private List<String> getChargeCode(String appId, String area) {
		// TODO Auto-generated method stub
		return null;
	}
}