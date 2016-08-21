package com.larva.inf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.larva.service.InfService;
import com.larva.utils.IPSeeker;
import com.larva.utils.ServiceUtils;
import com.larva.vo.ResultVO;
import com.mini.core.Record;


@Controller
public class InfController {
	private Logger logger = Logger.getLogger(InfController.class);
	private static IPSeeker ipSeeker = IPSeeker.getInstance();
	@Autowired
	private InfService infService;	
	@RequestMapping(value="/setCharge", method=RequestMethod.GET)
	@ResponseBody
    public ResultVO login(@RequestParam(value="app_id", required=false, defaultValue="") String app_id,//应用id
			@RequestParam(value="app_key", required=false, defaultValue="") String app_key,//appKey
			@RequestParam(value="channel", required=false, defaultValue="") String channel,//渠道id
			@RequestParam(value="price", required=false, defaultValue="") String price,//价格：1000分
			@RequestParam(value="imei", required=false, defaultValue="") String imei,//手机IMSI信息
			@RequestParam(value="imsi", required=false, defaultValue="") String imsi,//手机IMEI信息
			@RequestParam(value="bsc_lac", required=false, defaultValue="") String netState,//移动基站信息
			@RequestParam(value="bsc_cid", required=false, defaultValue="") String ua,//移动基站信息
			@RequestParam(value="mobile", required=false, defaultValue="") String brand,//手机号码
			@RequestParam(value="iccid", required=false, defaultValue="") String advertType,//sim卡iccid号
			@RequestParam(value="mac", required=false, defaultValue="") String adSize,//mac
			@RequestParam(value="cpparm", required=false, defaultValue="") String state,//cpparm
			@RequestParam(value="fmt", required=false, defaultValue="") String fmt,//报文输出格式 json
			@RequestParam(value="timestamp", required=false, defaultValue="") String timestamp,//时间戳
			@RequestParam(value="isp", required=false, defaultValue="") String isp,//运营商编码
			HttpServletRequest request){
		ResultVO vo = new ResultVO(true);
		boolean result = true;
		String area_id = "";
		//参数判断
		if(StringUtils.isBlank(app_id)) {
			vo.setOk(false);
			vo.setMsg("app_id is null");
			return vo;
		}
		if(StringUtils.isBlank(app_key)) {
			vo.setOk(false);
			vo.setMsg("app_key is null");
			return vo;
		}
		if(StringUtils.isBlank(imei)) {
			vo.setOk(false);
			vo.setMsg("imei is null");
			return vo;
		}
		if(StringUtils.isBlank(imsi)) {
			vo.setOk(false);
			vo.setMsg("imsi is null");
			return vo;
		}
		if(StringUtils.isBlank(fmt)) {
			vo.setOk(false);
			vo.setMsg("fmt is null");
			return vo;
		}
		if(StringUtils.isBlank(timestamp)) {
			vo.setOk(false);
			vo.setMsg("timestamp is null");
			return vo;
			
		}
		// TODO 逻辑判断，获取计费代码，请求计费代码，解析反馈数据，反馈客户端数据
		String realIp = ServiceUtils.getRealAddr(request);//获取ip地址
		logger.info("realIp:" + realIp);
		String address = ipSeeker.getAddress(realIp);
		area_id = address.split(" ")[0];//获取区域编码
		area_id = "110000";//测试用
		isp = "1001";
		logger.info("area:" + area_id);

		//appid+appKey校验，查数据库看此appid和key 是否可用，APP日月限量额度
		//判断是否在可用省份内，APP省内日月限量额度
		vo = infService.checkApp(app_id,app_key,area_id,isp);
		if(!vo.isOk()){//APP校验不通过
			return vo;
		}
		Record r = (Record) vo.getData();
		
		//获取可用计费代码（判断是否超过地区日限量、地区月限量、app日限量、app月限量）
		vo = infService.checkAndGetChargeCode(app_id,area_id,isp);
		if(!vo.isOk()){//校验不通过
			return vo;
		}
		Record chargeCodes = (Record) vo.getData();
		logger.debug(chargeCodes);
		/*if(chargeCodes.size()>0){
			String backStr = "";
			for(String[] one :chargeCodes){
				//循环每个计费代码（判断计费代码是否在可用省份内）
					//判断是否在可用时间段内
					//判断计费代码的日限量、月限量
				//发送计费代码请求
					//计费代码参数填充
					//计费代码请求
					//计费代码反馈报文解析
					//发送请求到运营商
//				String returnCode = this.reqYYS(one[0]);
				backStr += this.analysis(one[0],one[1]); 
			}
			//反馈给客户端
			vo.setOk(true);
			vo.setMsg("success");
			vo.setData(backStr);
		}else{
			vo.setOk(false);
			vo.setMsg("have no charging");
		}*/
		return vo;
	}
	/**
	 * 解析反馈的报文数据
	 * @param returnCode
	 * @return
	 */
	private String analysis(String returnCode,String returnForm) {
		// TODO Auto-generated method stub
		return null;
	}
	@ResponseBody
	public ResultVO fail() {
		ResultVO vo = new ResultVO(false);
		vo.setData(null);
		vo.setMsg("url error ");
		return vo;
	}
}