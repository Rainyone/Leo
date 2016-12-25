package com.larva.inf.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.larva.enums.InfStatic;
import com.larva.model.AppInfLog;
import com.larva.model.LogOrder;
import com.larva.service.InfService;
import com.larva.utils.DateUtils;
import com.larva.utils.IPSeeker;
import com.larva.utils.JSONUtil;
import com.larva.utils.ServiceUtils;
import com.larva.utils.StrKit;
import com.larva.utils.UUIDUtil;
import com.larva.vo.ResultVO;
import com.mini.core.Record;


@Controller
public class InfController {
	private Logger logger = Logger.getLogger(InfController.class);
	private static IPSeeker ipSeeker = IPSeeker.getInstance();
	@Autowired
	private InfService infService;	
	
	@RequestMapping(value="/appInfLog", method=RequestMethod.GET)
	@ResponseBody
	public ResultVO appInfLog(
			@RequestParam(value="charge_key", required=false, defaultValue="") String charge_key,//日志id
			@RequestParam(value="imsi", required=false, defaultValue="") String imsi,//imsi
			@RequestParam(value="channel", required=false, defaultValue="") String channel,//channel
			@RequestParam(value="logtime", required=false, defaultValue="") String logtime,//logtime
			@RequestParam(value="stepname", required=false, defaultValue="") String stepname,//stepname
			@RequestParam(value="context", required=false, defaultValue="") String context,//context
			@RequestParam(value="timestamp", required=false, defaultValue="") String timestamp,//timestamp
			HttpServletRequest request){
		ResultVO vo = new ResultVO(false);
		logger.info("charge_key:"+charge_key +",imsi:"+imsi + ",channel:"+channel +  ",logtime:"+logtime +
				",stepname:"+stepname +  ",context:"+context +  ",timestamp:"+timestamp );
		//参数判断
		if(StringUtils.isBlank(charge_key)) {
			vo.setOk(false);
			vo.setMsg("charge_key is null");
			return vo;
		}
		if(StringUtils.isBlank(imsi)) {
			vo.setOk(false);
			vo.setMsg("imsi is null");
			return vo;
		}
		if(StringUtils.isBlank(stepname)) {
			vo.setOk(false);
			vo.setMsg("stepname is null");
			return vo;
		}
		if(StringUtils.isBlank(stepname)) {
			vo.setOk(false);
			vo.setMsg("stepname is null");
			return vo;
		}
		if(StringUtils.isBlank(timestamp)) {
			vo.setOk(false);
			vo.setMsg("timestamp is null");
			return vo;
		}
		//判断charge_key是否有效
		//判断此order_id是否存在状态为1的记录
		int result= infService.getCountChargeLog(charge_key);
		if(result>0){//存在
			//新增日志记录
			AppInfLog ail = new AppInfLog();
			ail.setId(UUIDUtil.getUUID());
			ail.setChargerKey(charge_key);
			ail.setChannel(channel);
			ail.setImsi(imsi);
			ail.setLogtime(DateUtils.string2Date(logtime));
			ail.setStepname(stepname);
			ail.setContext(context);
			ail.setCreateTime(new Date());
			ail.setIp(ServiceUtils.getRealAddr(request));
			infService.insertAppInfLog(ail);
			vo.setOk(true);
			vo.setMsg("success");
		}else{
			vo.setMsg("have no charge log!");
		}
		return vo;
	}
	
	@RequestMapping(value="/callback", method=RequestMethod.GET)
	@ResponseBody
	public ResultVO callback(HttpServletRequest request){
		String charge_id = request.getParameter("charge_id");
		logger.info(charge_id);
		//找计费代码
		Record r = infService.getCallBackSuccessById(charge_id);
		String callbacksuccess = r.getStr("callbackcolumn");
		String callbackcolumn = r.getStr("callbackcolumn");
		String charge_key = request.getParameter(callbackcolumn);//回调函数中包含的透传参数为记录id
		String queryStr = request.getQueryString();
		logger.info("queryStr:" + queryStr);
		LogOrder log = new LogOrder();
		if(StrKit.notBlank(queryStr)){
			if(StrKit.notBlank(charge_key)){//有值id
				if(queryStr.indexOf(callbacksuccess)>=0){//成功
					log.setOrderState(3);
				}else{//失败
					log.setOrderState(4);
				}
				logOrder(infService, log, -1, 1);
			}
		}
		ResultVO vo = new ResultVO(true);
		vo.setMsg("success");
		return vo;
	}
	
	
	
	@RequestMapping(value="/setCharge", method=RequestMethod.GET)
	@ResponseBody
    public ResultVO login(@RequestParam(value="app_id", required=false, defaultValue="") String app_id,//应用id
			@RequestParam(value="app_key", required=false, defaultValue="") String app_key,//appKey
			@RequestParam(value="request_type", required=false, defaultValue="") String request_type,//渠道id
			@RequestParam(value="channel", required=false, defaultValue="") String channel,//渠道id
			@RequestParam(value="price", required=false, defaultValue="") String price,//价格：1000分
			@RequestParam(value="imei", required=false, defaultValue="") String imei,//手机IMSI信息
			@RequestParam(value="imsi", required=false, defaultValue="") String imsi,//手机IMEI信息
			@RequestParam(value="bsc_lac", required=false, defaultValue="") String bsc_lac,//移动基站信息
			@RequestParam(value="bsc_cid", required=false, defaultValue="") String bsc_cid,//移动基站信息
			@RequestParam(value="mobile", required=false, defaultValue="") String mobile,//手机号码
			@RequestParam(value="iccid", required=false, defaultValue="") String iccid,//sim卡iccid号
			@RequestParam(value="mac", required=false, defaultValue="") String mac,//mac
			@RequestParam(value="cpparm", required=false, defaultValue="") String cpparm,//cpparm
			@RequestParam(value="fmt", required=false, defaultValue="") String fmt,//报文输出格式 json
			@RequestParam(value="timestamp", required=false, defaultValue="") String timestamp,//时间戳
			@RequestParam(value="isp", required=false, defaultValue="") String isp,//运营商编码
			@RequestParam(value="code_id", required=false, defaultValue="") String code_id,//计费代码编码
			@RequestParam(value="order_id", required=false, defaultValue="") String order_id,//验证码回传的订单号
			@RequestParam(value="ver_code", required=false, defaultValue="") String ver_code,//验证码
			@RequestParam(value="charge_key", required=false, defaultValue="") String charge_key,//验证码
			@RequestParam(value="charge_success", required=false, defaultValue="") String charge_success,//验证码
			HttpServletRequest request){
		logger.debug("para--app_id:" + app_id + ";app_key:"+app_key+ ";request_type:"+request_type+ ";channel:"+channel+ ";price:"+price
				+ ";imei:"+imei + ";imsi:"+imsi + ";bsc_lac:"+bsc_lac + ";bsc_cid:"+bsc_cid + ";mobile:"+mobile + ";iccid:"+iccid + ";mac:"+mac 
				+ ";cpparm:"+cpparm + ";fmt:"+fmt + ";timestamp:"+timestamp + ";isp:"+isp + ";code_id:"+code_id + ";order_id:"+order_id + ";ver_code:"+ver_code );
		ResultVO vo = new ResultVO(true);
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
		if(StringUtils.isBlank(request_type)) {
			vo.setOk(false);
			vo.setMsg("request_type is null");
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
		String realIp = ServiceUtils.getRealAddr(request);//获取ip地址
		String mid = UUIDUtil.getUUID();
		LogOrder log = new LogOrder();
		log.setId(mid);
		log.setAppId(app_id);
		log.setIspId(isp);
		log.setImei(imei);
		log.setImsi(imsi);
		log.setIp(realIp);
		log.setBscCid(bsc_cid);
		log.setBscLac(bsc_lac);
		log.setMobile(mobile);
		log.setIccid(iccid);
		log.setMac(mac);
		log.setCpparm(cpparm);
		log.setFmt(fmt);
		log.setPrice(price);
		log.setOrderState(-1);
		log.setCreateTime(new Date());
		//  逻辑判断，获取计费代码，请求计费代码，解析反馈数据，反馈客户端数据
		if("1".equals(request_type)){//计费请求
			area_id = infService.getAreaIdByImsi(imsi);//根据imsi号获取省份
			if(area_id==null||"".equals(area_id)){//如果没有获取到再根据ip获取省份
				logger.info("realIp:" + realIp);
				String address = ipSeeker.getAddress(realIp);
				area_id = address.split(" ")[0];//获取区域编码
//				area_id = "110000";//测试用
//				isp = "1002";
			}
			//测试用
//			area_id = "110000";
//			realIp = "132.33.32.12";
			log.setAreaId(area_id);
			logger.debug("imsi:"+imsi+";area:" + area_id);
			if(area_id!=null&&!"".equals(area_id)){//如果有区域编码
				//记录日志：接收到请求
				//（一个请求可能有多个计费代码）
				logOrder(infService, log, -1, 0);
				
				//appid+appKey校验，查数据库看此appid和key 是否可用，APP日月限量额度
				//判断是否在可用省份内，APP省内日月限量额度
				vo = infService.checkApp(app_id,app_key,area_id,isp);
				if(!vo.isOk()){//APP校验不通过
					return vo;
				}
				Record app = (Record) vo.getData();//app数据
	//			String charge_code = 
				//获取可用计费代码（判断是否超过地区日限量、地区月限量、app日限量、app月限量）
				vo = infService.checkAndGetChargeCode(app_id,area_id,isp);
				if(!vo.isOk()){//校验不通过
					return vo;
				}
				
				List<Record>  list = (List<Record>) vo.getData();
				logger.debug(list);
				List<Object> backList = new ArrayList<Object>();
				List<String> errorBackList = new ArrayList<String>();
				for(Record r:list){
					String logId = UUIDUtil.getUUID();//单个计费代码的日志id
					
					String id = r.getStr("id");
					String codeName = r.getStr("code_name");
					String sendType = r.getStr("send_type");
					String chargeCode = r.getStr("charge_code");
					String url = r.getStr("url");
					String backMsgType = r.getStr("back_msg_type");
					String returnForm = r.getStr("return_form"); 
					String backForm = r.getStr("back_form"); 
					String backMsg = "";
					String analysisBackMsg = "";
					String successFlag = r.getStr("success_flag");
					Integer inf_type = r.getInt("inf_type");//1：不需要验证码，2需要通过接口反馈验证码，3.需要短信反馈验证码
					String order_id_code = r.getStr("order_id_code");//验证码的order_id字段
					String keyMsg = r.getStr("key_msg");
					Long chargePrice = r.getLong("charge_price");
					try{
						//记录日志：发起请求
						log.setId(logId);
						log.setMid(mid);
						log.setChargeCodeId(id);
						log.setChargePrice(chargePrice);
						log.setOrderState(0);//发起
						log.setCreateTime(new Date());
						logOrder(infService, log, -1, 0);
						if(inf_type==1||inf_type==2){//1：不需要验证码，直接请求运营商2需要通过接口反馈验证码
							if(InfStatic.SEND_TYPE_GET.equals(sendType)){//get方式没有charge_code
								//参数替换
								url = url.replace("${imei}", imei).replace("${imsi}", imsi).replace("${bsc_lac}", bsc_lac)
										.replace("${bsc_cid}", bsc_cid).replace("${mobile}", mobile).replace("${iccid}", iccid)
										.replace("${mac}", mac).replace("${cpparm}", cpparm).replace("${fmt}", fmt)
										.replace("${isp}", isp).replace("${ip}", realIp).replace("${logid}",logId);
								//发送请求
//								if(id.equals("5e5adfa42eb4461c8cdf39de20eaf42a")){
//									backMsg = "{ \"msg\":\"success\", \"port1\": \"dddd\", \"msg1\": \"ssss\", \"type1\": \"1\"}";
//								}else if(id.equals("90deb3c8b8f64a4dbede34fbd19d7452")){
//									backMsg = "{ \"ok\": \"true\", \"msg\": \"请求成功\", \"data_list\": [ { \"port-no\": \"10086\", \"message\": \"6\", \"type\": \"0\" }, { \"port-no\": \"10087\", \"message\": \"7\", \"type\": \"0\" }, { \"port-no\": \"10088\", \"message\": \"8\", \"type\": \"0\" } ]}";
//								}
								backMsg = this.sendGet(url, id, logId);
								logger.info("backmsg:" + backMsg);
								//记录下反馈报文
							}else if(InfStatic.SEND_TYPE_POST.equals(sendType)){	//post方式
								chargeCode = chargeCode.replace("${imei}", imei).replace("${imsi}", imsi).replace("${bsc_lac}", bsc_lac)
										.replace("${bsc_cid}", bsc_cid).replace("${mobile}", mobile).replace("${iccid}", iccid)
										.replace("${mac}", mac).replace("${cpparm}", cpparm).replace("${fmt}", fmt)
										.replace("${isp}", isp).replace("${ip}", realIp);;
								//发送请求
								backMsg = this.sendPost(url,chargeCode,id,logId);
							}else{
								errorBackList.add("id:" + id + ",code_name:"+codeName+";请求方式不对POST/GET");
							}
						}else if(inf_type==3){//不调用运营商接口
							backMsg="\"msg\":\"success\"";
							logger.info("inf_type=3,默认backMsg=" + backMsg);
						}else{//后期扩展
							// TODO 扩展
						}
						Map<String, Object> charge = new TreeMap<String, Object>();
						charge.put("code_id", id);
						charge.put("charge_key", logId);
						charge.put("inf_type", String.valueOf(inf_type));
						
						if(backMsg.indexOf(successFlag)<0){//判断成功标示(不包含则反馈的是失败信息)
							logger.debug("charge_id:" + id +";logId:"+logId+";运营商反馈失败信息");
							log.setOrderState(2);//失败
						}else{
							if(inf_type==1||inf_type==2){//1：不需要验证码，直接请求运营商2需要通过接口反馈验证码
								if(!"{}".equals(backForm)){//不需要反馈给客户端
									if(backMsg!=null&&!"".equals(backMsg)){
										if(InfStatic.BACK_MSG_JSON.equals(backMsgType)){//json方式
											analysisBackMsg = this.analysisJson(backMsg, returnForm,backForm);
										}else if(InfStatic.BACK_MSG_XML.equals(backMsgType)){
											analysisBackMsg = this.analysisXml(backMsg, returnForm);
										}else{
											errorBackList.add("id:" + id + ",code_name:"+codeName+";反馈报文格式不对JSON/XML");
										}
									}
								}
							}else if(inf_type==3){//不调用运营商接口
								analysisBackMsg = backForm;
							}else{//后期扩展
								// TODO 扩展
							}
							
							//analysisBackMsg = analysisBackMsg.replace("${code_id}", id);
							List<Object> msg_list = new ArrayList<Object>();
							Object o = null;
							String o_type = "o";
							try{
								o = (analysisBackMsg!=null&&!"".equals(analysisBackMsg))?JSONUtil.getJSONFromString(analysisBackMsg):null;
								msg_list.add(o);
							}catch(Exception e1){
								o = (analysisBackMsg!=null&&!"".equals(analysisBackMsg))?JSONUtil.getJSONFromString("{list:["+analysisBackMsg+"]}"):"";
								if(o!=null&&!"".equals(o.toString())){
									JSONArray js = (JSONArray) ((JSONObject)o).get("list");
									for(int i=0;i<js.size();i++){
										JSONObject jo = (JSONObject) js.get(i);
										msg_list.add(jo);
									}
								}
								o_type = "list";
							}
							try{
								String back_order_id = "";
								//获取order_id(验证码反馈的订单编码)
								if(o!=null&&inf_type==2){
									if("o".equals(o_type)){//json 对象
										JSONArray dataList = ((JSONObject)o).getJSONArray("list");
										JSONObject onelist = dataList.getJSONObject(0);
										back_order_id = onelist.getString(order_id_code);
									}
								}else if("list".equals(o_type)&&inf_type==2){
									back_order_id = ((JSONObject)o).getString(order_id_code);
								}
								if(back_order_id!=null&&!"".equals(back_order_id)){//记录验证码订单
									log.setOrderNo(back_order_id);
									infService.updateOrderNoById(logId, order_id_code);
									charge.put("orderId", back_order_id);
								}else{
									charge.put("orderId", "");
								}
								charge.put("msg_list", msg_list);
							}catch(Exception e){
								e.printStackTrace();
								logger.error("charge_id:" + id +";验证码字段获取失败");
								log.setOrderState(2);//失败
							}
							if(inf_type!=2){//除掉需要接口反馈验证码
								//更新日月限量总数
								infService.updateCount(app_id, id,area_id);
							}
							
							log.setOrderState(1);//成功
						}
						if(charge.get("msg_list")!=null){
//							keyMsg = "\"keyMsg\": [ { \"keyport\": \"10086123\", \"keyword\": \"游戏点数\" }, { \"keyport\": \"10086122\", \"keyword\": \"\" }, { \"keyport\": \"\", \"keyword\": \"法制文萃\" },{ \"keyport\": \"10086\", \"keyword\": \"计费成功\" }]";
							if(StrKit.notBlank(keyMsg)){
								JSONObject keyMsgList = JSONUtil.getJSONFromString("{"+keyMsg+"}");
								JSONArray js = (JSONArray) ((JSONObject)keyMsgList).get("keyMsg");
								charge.put("keyMsg", js);
							}
							backList.add(charge);
						}
					}catch(Exception e){
						e.printStackTrace();
						logger.error(e);
						logger.debug("charge_id:" + id+";请求运营商异常");
						log.setOrderState(2);//失败
					}
					log.setUpdateTime(new Date());
					logOrder(infService, log, 0,1);//修改
				}
				Map<String, List<Object>> backMap = new HashMap<String,List<Object>>();
				backMap.put("charge_list", backList);
				vo.setData(backMap);//只反馈请求成功的计费代码
				vo.setMsg("请求成功");
				vo.setOk(true);
//				System.out.println(JSONUtil.toJson(vo));
			}else{
				vo.setOk(false);
				vo.setMsg("can't get area");
				return vo;
			}
		}else if("2".equals(request_type)){//验证请求
			if(StringUtils.isBlank(code_id)) {
				vo.setOk(false);
				vo.setMsg("code_id is null");
				return vo;
			}
			if(StringUtils.isBlank(order_id)) {
				vo.setOk(false);
				vo.setMsg("order_id is null");
				return vo;
			}
			if(StringUtils.isBlank(ver_code)) {
				vo.setOk(false);
				vo.setMsg("ver_code is null");
				return vo;
			}
			if(StringUtils.isBlank(mobile)) {
				vo.setOk(false);
				vo.setMsg("mobile is null");
				return vo;
			}
			//判断此order_id是否存在状态为1的记录
			vo = infService.checkOrderId(order_id);
			if(vo.isOk()){
				Record charge = infService.getVerCodeUrlById(code_id);
				String ver_code_url = charge.getStr("ver_code_url");
				String id = charge.getStr("id");
				String ver_code_success_flag = charge.getStr("ver_code_success_flag");
				if(ver_code_url!=null||!"".equals(ver_code_url)){
					ver_code_url = ver_code_url.replace("${imei}", imei).replace("${imsi}", imsi).replace("${bsc_lac}", bsc_lac)
							.replace("${bsc_cid}", bsc_cid).replace("${mobile}", mobile).replace("${iccid}", iccid)
							.replace("${mac}", mac).replace("${cpparm}", cpparm).replace("${fmt}", fmt)
							.replace("${isp}", isp).replace("${order_id}", order_id).replace("${ver_code}", ver_code)
							.replace("${mobile}", mobile);
					String backMsg = this.sendGet(ver_code_url,id,order_id);
					if(backMsg.indexOf(ver_code_success_flag)>0){//反馈成功
						//更新日月限量总数
						infService.updateCount(app_id, id,area_id);
						//更新状态
						log.setAppId(app_id);
						log.setOrderNo(order_id);
						log.setOrderState(7);
						log.setUpdateTime(new Date());
						vo.setOk(true);
						vo.setMsg("charge success");
					}else{
						log.setAppId(app_id);
						log.setOrderNo(order_id);
						log.setOrderState(8);
						log.setUpdateTime(new Date());
						vo.setOk(false);
						vo.setMsg("charge false");
					}
					logOrder(infService, log, 0,2);//修改
					Map<String, String> backMap = new HashMap<String,String>();
					backMap.put("order_id", order_id);
					vo.setData(backMap);
					return vo;
				}else{
					vo.setOk(false);
					vo.setMsg("ver_code_url is null");
					return vo;
				}
			}else{
				vo.setOk(false);
				vo.setMsg("Have already been asked or There is no data on this platform");
				return vo;
			}
		}else if("3".equals(request_type)){
			if(StringUtils.isBlank(charge_key)) {
				vo.setOk(false);
				vo.setMsg("charge_key is null");
				return vo;
			}
			if(StringUtils.isBlank(charge_success)) {
				vo.setOk(false);
				vo.setMsg("charge_success is null");
				return vo;
			}
			log.setId(charge_key);
			if("1".equals(charge_success)){
				log.setOrderState(5);
			}else{
				log.setOrderState(6);
			}
			logOrder(infService, log, 0,1);//修改
			vo.setOk(true);
			vo.setMsg("请求成功");
			return vo;
		}else{//请求类型不对
			vo.setOk(false);
			vo.setMsg("request_type is error");
			return vo;
		}
		return vo;
	}
	/**
	 * 
	 * @param infService 
	 * @param logOrder 订单日志对象
	 * @param oldState 原来的状态
	 * @param flag 更新标示：0新增
	 */
	private void logOrder(InfService infService,LogOrder logOrder,int oldState,int flag){
//		Runnable r = new InfLogOrderThread(infService, logOrder, oldState, flag);
//		Thread t = new Thread(r);
//		t.start();
		if(flag==0){//新增
			infService.saveLogOrder(logOrder);
		}else if(flag==1){//更新
			infService.updateLogOrder(logOrder.getId(), logOrder.getOrderState(), oldState);
		}else if(flag==2){//验证码更新
			infService.updateLogOrderByOrderNo(logOrder.getOrderNo(), logOrder.getOrderState());
		}
	}
	
	/**
	 * 解析xml报文
	 * @param backMsg
	 * @param returnForm
	 * @return
	 */
	private String analysisXml(String backMsg, String returnForm) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 解析json报文
	 * @param backMsg
	 * @param returnForm
	 * @return
	 */
	private static String analysisJson(String backMsg, String returnForm,String backForm) {
		JSONObject backJson = JSONUtil.getJSONFromString(backMsg);
		//节点名(single/more):子节点(single/more):末节点->反馈报文参数字段|节点名(single/more):子节点(single/more):末节点->反馈报文参数字段
		//datas(s):data(m):msg(m):port->port,content->msg|datas(s):data(m):isHaveVer->needVerCode(解析出的结果要么是多个对应，要么只有一个)
		String[] returnForms = returnForm.split("\\|");//判断需要解析那些参数
		Map<String, List<String>> map = new TreeMap<String, List<String>>();
		for(String one:returnForms){
		//	String[] temps = one.split("->");//解析参数和反馈参数
			String reqpara = one.substring(0,one.lastIndexOf(":"));
			String[] reqEnds = one.substring(one.lastIndexOf(":")+1,one.length()).split(",");
			for(String reqEnd:reqEnds){
				String[] t = reqEnd.split("->");//前面的是原始节点，后面的是替换参数
				List<String> result = getElement(backJson,(reqpara!=null&&!"".equals(reqpara)&&!"\"\"".equals(reqpara))? reqpara+"e":"", t[0], null);
				map.put(t[1], result);
				
			}
		}
		if(map!=null&&map.size()>0){
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();//记录转换后的map
			Map<String, String> singleList = new HashMap<String, String>();//单个map值需要遍历后填充所有
			Set<Entry<String,List<String>>> mapSet = map.entrySet();
			for(Entry<String,List<String>> oneMap:mapSet){
				String key = oneMap.getKey();
				List<String> valueList = oneMap.getValue();
				if(valueList.size()>1){
					for(int i=0;i<valueList.size();i++){
						if(list!=null&&list.size()>i){//list有map了
							list.get(i).put(key, valueList.get(i));
						}else{
							Map<String, String> tempMap = new HashMap<String, String>();
							tempMap.put(key, valueList.get(i));
							list.add(tempMap);
						}
					}
				}else{
					//单个
					singleList.put(key, valueList.get(0));
				}
			}
			//处理单个
			if(singleList!=null&&singleList.size()>0){
				if(list!=null&&list.size()>0){
					for(Entry<String,String> single:singleList.entrySet()){
						String key = single.getKey();
						String value = single.getValue();
						for(Map<String, String> oneMap:list){
							oneMap.put(key, value);
						}
					}
				}else{
					list.add(singleList);
				}
			}
			String oldbackForm = backForm;//保存一份原始参数
			//遍历list替换参数
			int maxPara = 1;
			for(int i=0;i<list.size();i++){
				Map<String, String> endMap = list.get(i); 
				for(Entry<String,String> sm:endMap.entrySet()){
					backForm = backForm.replace("${"+sm.getKey()+"}", sm.getValue());
				}
				backForm = backForm.replace("${orderby}", String.valueOf(maxPara));
				if(maxPara<list.size()){
					backForm+=oldbackForm;
					maxPara++;
				}
			}
		}
		return backForm.replace("}{", "},{");
	}
	
	
	/**
	 * 获取json子节点单个datas(m):data(m):msg(e):port
	 * @param json
	 * @param reqElem
	 * @param reqEnd
	 * @param list
	 * @return
	 */
	private static List<String> getElement(JSONObject json,String reqElem,String reqEnd,List<String> list){
		if(list==null||list.size()<=0){
			list = new ArrayList<String>();
		}
		if(reqElem!=null&&!"".equals(reqElem)){
			String [] reqElems = reqElem.split(":");
			String s = reqElems[0];
			String tempReqElem = reqElem.replace(s+":", "");
			if(s.contains("(m)")){//多条数据-数组
				if(s.contains("(m)e")){//判断是否为最后一个元素
					s = s.replace("(m)e", "");
					JSONArray js = json.getJSONArray(s);
					for(int i=0;i<js.size();i++){
						list = getElement(js.getJSONObject(i), "", reqEnd,list);
					}
				}else{
					s = s.replace("(m)", "");
					JSONArray js = json.getJSONArray(s);
					for(int i=0;i<js.size();i++){
						list = getElement(js.getJSONObject(i), tempReqElem, reqEnd,list);
					}
				}
			}else if(s.contains("(s)")){//单条数据
				if(s.contains("(s)e")){//判断是否为最后一个元素
					s = s.replace("(s)e", "");
					json = json.getJSONObject(s);
					list = getElement(json, "",reqEnd,list);
				}else{
					s = s.replace("(s)", "");
					json = json.getJSONObject(s);
					list = getElement(json, tempReqElem,reqEnd,list);
				}
			}
		}else{
			String str =  json.getString(reqEnd);
			if(str!=null){
				str = str.replace("\\", "\\\\").replace("\"","\\\"");
				list.add(str);
			}
		}
		return list ;
	}
	/**
	 * post方式发送报文
	 * @param url
	 * @param charge_code
	 * @return
	 */
	private String sendPost(String url, String charge_code,String charge_id,String logid) {
		return sendURL(url, charge_code, InfStatic.SEND_TYPE_POST,charge_id,logid);
	}
	/**
	 * get方式发送报文
	 * @param url
	 * @return
	 */
	private String sendGet(String url,String charge_id,String logid) {
		return sendURL(url, null, InfStatic.SEND_TYPE_GET,charge_id,logid);
	}
	/**
	 * 发送get\post请求
	 * @param msg
	 * @param postGetFlag
	 * @param returType
	 * @return
	 */
	private String sendURL(String msgUrl,String msgContent,String postGetFlag,String charge_id,String logid) {
		HttpURLConnection httpConn = null;
		String resultXml = "";
		try {
			logger.info("===***==创建http连接并设置参数。。。。");
			logger.info("===charge_id:"+charge_id+";logid:"+logid+";***==msgUrl：" + msgUrl);
			URL url = new URL(msgUrl);
			URLConnection conn = url.openConnection();
			// 设置超时时间
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			httpConn = (HttpURLConnection) conn;
			//byte[] contentByte = requestXml.getBytes();
			if(InfStatic.SEND_TYPE_GET.equals(postGetFlag)){//get请求
				logger.debug("===***get begin***====");
				// 进行连接
				// 但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到 服务器
				conn.connect();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(),
								"UTF-8"));
				StringBuffer sb = new StringBuffer();
				String lines;
				while ((lines = reader.readLine()) != null) {
					sb.append(lines);
				}
				resultXml = sb.toString();
				reader.close();
				// 断开连接
				logger.debug("===***get end***====");
			}else if(InfStatic.SEND_TYPE_POST.equals(postGetFlag)){//post请求
				logger.info("===***post begin***====");
				byte[] contentByte = null;
				contentByte = msgContent.getBytes("UTF-8");
				httpConn.setRequestProperty("Content-Length",String.valueOf(contentByte.length));
				httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpConn.setRequestProperty("Charset", "UTF-8");
				httpConn.setRequestMethod("POST");
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				OutputStream os = httpConn.getOutputStream();
				os.write(contentByte);
				os.flush();
				os.close();
				logger.info("===***post request***====");
				int status = httpConn.getResponseCode();
				if (status == HttpURLConnection.HTTP_OK) {
					InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), "UTF-8");
					BufferedReader br = new BufferedReader(isr);
					StringBuffer sb = new StringBuffer();
					String inputLine = "";
					
					while ((inputLine = br.readLine()) != null) {
						sb.append(inputLine);
					}
					
					resultXml = sb.toString();
					br.close();
					isr.close();
				} else {
					// 通讯异常处理
					resultXml = "-9999";
				}
				logger.info("===***post end***====");
			}
		} catch(SocketTimeoutException e) { 
			logger.error("SocketTimeoutException:" + e.getMessage(), e);
			// 通讯异常处理
			resultXml = "-9999";
		} catch (Exception e) {
			logger.error("Exception:" + e.getMessage(), e);
		} finally {
			logger.debug("===***release conn***====");
			if(httpConn != null) {
				httpConn.disconnect();
			}
		}
		String str5 = "{\"resultCode\": 0,\"count\": 1,\"port1\": \"10086\",\"msg1\": \"1\", " +
				" \"type1\": 0, \"port2\": \"10086\",\"msg2\": \"2\",\"type2\": 0}";
		logger.debug("==charge_id:"+charge_id+";logid:"+logid+";***response msg==:" + resultXml);
		return resultXml;
	}
	/**
	 * 失败跳转
	 * @return
	 */
	@RequestMapping(value="/fail")
	@ResponseBody
	public ResultVO fail() {
		ResultVO vo = new ResultVO(false);
		vo.setData(null);
		vo.setMsg("url error ");
		return vo;
	}
	
	
	public static void main(String[] args) {
		String str = "{\"ok\": \"true\",  \"msg\": \"请求成功\",  \"data_list\": [ { \"port-no\": \"10086\",";
		            str += "\"message\": \"1\", \"type\": \"0\"},{ \"port-no\": \"10086\", \"message\": \"1\",\"type\": \"0\"}], \"original\": {}}";
		System.out.println(str); 
		String str2 = "{                               							"	
				+"    \"datas\": [                              "
				+"        {                                     "
				+"            \"data\": [                       "
				+"                {                             "
				+"                    \"msg\": [                "
				+"                        {\"port\": \"9\", },     "
				+"                        {\"tel\": \"10\", },     "
				+"                        {\"cont\": \"11\" }     "
				+"                    ],                        "
				+"                    \"dx\": \"103\"           "
				+"                },                            "
				+"                {                             "
				+"                    \"msg\": [                "
				+"                       { \"port\": \"12\"},     "
				+"                        {\"tel\": \"13\"},      "
				+"                        {\"cont\": \"14\"}      "
				+"                    ],                        "
				+"                    \"dx\": \"104\"           "
				+"                }                             "
				+"            ]                                 "
				+"        },                                     "
				+"        {                                     "
				+"            \"data\": [                       "
				+"                {                             "
				+"                    \"msg\": [                "
				+"                        {\"port\": \"9\", },     "
				+"                        {\"tel\": \"10\", },     "
				+"                        {\"cont\": \"11\" }     "
				+"                    ],                        "
				+"                    \"dx\": \"103\"           "
				+"                },                            "
				+"                {                             "
				+"                    \"msg\": [                "
				+"                       { \"port\": \"12\"},     "
				+"                        {\"tel\": \"13\"},      "
				+"                        {\"cont\": \"14\"}      "
				+"                    ],                        "
				+"                    \"dx\": \"104\"           "
				+"                }                             "
				+"            ]                                 "
				+"        }                                     "
				+"    ]                                         "
				+"}                                             ";
		String  str3 = "{                               							"	
				+"    \"datas\": [                              "
				+"        {                                     "
				+"            \"data\":                       "
				+"                {                             "
				+"                    \"msg\": [                "
				+"                        {\"port\": \"9\", },     "
				+"                        {\"port\": \"10\", },     "
				+"                        {\"cont\": \"17\" },     "
				+"                        {\"cont\": \"11\" },     "
				+"                        {\"cont\": \"12\" },     "
				+"                        {\"cont\": \"13\" },     "
				+"                        {\"tel\": \"119\" }     "     
				+"                    ],                        "
				+"                    \"dx\": \"103\"           "
				+"                },"
				+"                    \"msg\": [                "
				+"                       { \"port\": \"12\"},     "
				+"                        {\"tel\": \"13\"},      "
				+"                        {\"cont\": \"14\"}      "
				+"                    ]          "
				+"                                            "
				+"                                             "
				+"        }                                     "
				+"    ]                                         "
				+"}                                             ";
		String str4 = "{                               							"	
				+"    \"datas\": [                              "
				+"        {                                     "
				+"            \"data\":                       "
				+"                {                             "
				+"                    \"msg\": [                "
				+"                        {\"port\": \"9\", },     "
				+"                        {\"tel\": \"10\", },     "
				+"                        {\"cont\": \"11\" }     "
				+"                    ],                        "
				+"                    \"dx\": \"103\"           "
				+"                },"
				+"                    \"msg\":                 "
				+"                       { \"port\": \"12\"}     "
				+"                                            "
				+"                                             "
				+"        }                                     "
				+"    ]                                         "
				+"}                                             ";
		String str5 = "{\"resultCode\": 0,\"count\": 1,\"port1\": \"10086\",\"msg1\": \"1\", " +
				" \"type1\": 0, \"port2\": \"10086\",\"msg2\": \"2\",\"type2\": 0}";
		String str6 = "{\"msg\":\"getsmsok\",\"sms\":\"MVSUP3,3789182294806,460029058022644,fe93309021cdded53bcda0e660884eaffbf3cec4f2595b832993e5f0f47e9dd73fb43d291de33e8b\",\"serviceno\":\"10658423\"}";
		String str7 = "{list:[{\"code_id\": \"${code_id}\", \"inf_type\": \"3\", \"orderId\": \"\", \"port\": \"1069055070421\", \"msg\": \"0710022H\", \"type\": \"1\"},{\"code_id\": \"${code_id}\", \"inf_type\": \"3\", \"orderId\": \"\", \"port\": \"1069055070421\", \"msg\": \"verCode\", \"type\": \"1\"}]}";
		String str8 = "{ \"code\": 0, \"message\": \"success\", \"orderId\": \"201609270113947725\", \"port0\": \"10658423\", \"port1\": \"1065842232\", \"sms0\": \"mvwlan,3acc7350d8db75ce508975e1c42034f6,RXQS\", \"sms1\": \"AE2000394k7z634*+z275f4U24\\\"60b2+x19l5r8a7x1Z]FWp+JzBliR1PnxeY+!i=gsg4M{\\2RevfV)-8201A092ZE 540X\\k{-0bs000?0MI0TWpWLSKz[Co7c&aHdVLnw6%V9x=\"}";
		String str9 = "{\"sms1\": {\"status\": \"\",\"serviceno\": \"10658423\",\"sms\": \"bXZ3bGFuLDFmODQ2NmVmZTE2ZDlhNGUyMjE2NDY4MjQ3YmExMmE0LGpubEY=\",\"msg\": \"getsmsok\",\"id\": 31551997,\"reportUrl\": \"http://101.251.100.8/migu.vsdk.servlet2/login_mo_sent\"},\"sms2\": {\"serviceno\": \"1065842232\",\"sms\": \"QUUyMDAwMzM4bDBxMzA4KyxyMzY5MDNcNTciNjBiMit4MTlsNXI4YTdOR05e Tkd3WzR6eU5nemw/QTJzMHcrPz03MEo0NzZbNF8yU0Uzfmw1MjAxQTExMV1E IDE2MFNcYnstMGJzMDAwPzBNSVtYd1Jrd3IwXzdYQ1NwM3RrRTdpMTFlckIz Qj0=\",\"msg\": \"getsmsok\",\"id\": 23588804,\"reportUrl\": \"http://101.251.100.8/migu.vsdk.servlet2/pay_mo_sent\"},\"limitInfo\": {\"provinceName\": \"未知省份\",\"limited\": false}}";
		String str10 = "{\"status\":0,\"stepcount\":1,\"codetype\":1,\"smss\":{\"sms1\":{\"smscontent\":\"131000RI000001B002RB00100000120390000000000003h33\",\"smsvcode\":0,\"smsretport\":\"10001888,10005888\",\"smsretcontent\":\"爱动漫,由爱动漫话费代收,客服4008867686,天翼爱动漫,爱动漫互动点播产品,失败原因\",\"smsport\":\"11802115100\",\"smsbase64\":0,\"smsbinary\":0}},\"orderid\":\"131000RI000001B002RB00100000120390000000000003h33\"}";
		String str11 = "{\"status\":0,\"stepcount\":2,\"codetype\":2,\"smss\":{\"sms1\":{\"smscontent\":\"bXZ3bGFuLDM0MWQwNGVmM2IzYWU2ZTAwZDNhYmQ4NTZkNmFiM2ZjLGdQcWU=\",\"smsvcode\":0,\"smsretport\":\"\",\"smsretcontent\":\"包月\",\"smsport\":\"10658423\",\"smsbase64\":1,\"smsbinary\":0},\"sms2\":{\"smscontent\":\"QUUyMDAwMzU5aTN6NjIzLS56Mjc3YTBbNzMiNjBiMiF9MjRrNXIyZzVwTHd1akkrP205M0o1Q0ktVUN3YzAxQT1jM0hkNDhcNlszY2ZGfTBHMjAxQTEyMVlFLDM2MV5canstMGJzMDAwPzBNSU1QV2U1QmdsbCB5M3FPMlAybEd1OHA5KE9iaD0=\",\"smsvcode\":0,\"smsretport\":\"\",\"smsretcontent\":\"包月\",\"smsport\":\"1065842232\",\"smsbase64\":1,\"smsbinary\":0}},\"orderid\":\"0000000000003mc7\"}";
		JSONObject backJson = JSONUtil.getJSONFromString(str4);
		System.out.println(str2);
		//System.out.println(backJson.getJSONArray("data_list").get(0));
		String r = "data_list(m):port1";
//		List<String > list = getElement(backJson,"resultCode(s)e","resultCode",null);
//		for(String s :list){
//			System.out.println(s);
//		}
		System.out.println(str6.indexOf("\"msg\":\"getsmsok\""));
		//String result = analysisJson(str, "data_list(m):port-no->port,message->msg,type->type","{\"code_id\": \"${code_id}\", \"inf_type\": \"1\", \"orderId\": \"\", \"port\": \"${port}\", \"msg\": \"${msg}\", \"type\": \"${type}\"}");
		String result = analysisJson(str11, "smss(s):sms1(s):smsport->smsport1,smscontent->smscontent1|smss(s):sms2(s):smsport->smsport2,smscontent->smscontent2","{\"port\":\"${smsport1}\",\"msg\":\"${smscontent1}\",\"type\":\"0\",\"orderby\":\"1\",\"msgtype\":1},{\"port\":\"${smsport2}\",\"msg\":\"${smscontent2}\",\"type\":\"0\",\"orderby\":\"2\",\"msgtype\":1}");
		System.out.println(result);
//		System.out.println("sdf\\asf".replace("\"", "\\\\"));
	}
	
}