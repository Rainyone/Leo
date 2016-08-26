package com.larva.inf.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.larva.enums.InfStatic;
import com.larva.service.InfService;
import com.larva.utils.IPSeeker;
import com.larva.utils.JSONUtil;
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
			HttpServletRequest request){
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
		if("1".equals(request_type)){//计费请求
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
			Record app = (Record) vo.getData();//app数据
//			String charge_code = 
			//获取可用计费代码（判断是否超过地区日限量、地区月限量、app日限量、app月限量）
			vo = infService.checkAndGetChargeCode(app_id,area_id,isp);
			if(!vo.isOk()){//校验不通过
				return vo;
			}
			List<Record>  list = (List<Record>) vo.getData();
			logger.debug(list);
			List<String> backList = new ArrayList<String>();
			List<String> errorBackList = new ArrayList<String>();
			for(Record r:list){
				String id = r.getStr("id");
				String code_name = r.getStr("code_name");
				String send_type = r.getStr("send_type");
				String charge_code = r.getStr("charge_code");
				String url = r.getStr("url");
				String back_msg_type = r.getStr("back_msg_type");
				String returnForm = r.getStr("return_form"); 
				String backForm = r.getStr("back_form"); 
				String backMsg = "";
				String analysisBackMsg = "";
				if(InfStatic.SEND_TYPE_GET.equals(send_type)){//get方式没有charge_code
					//参数替换
					url = url.replace("${imei}", imei).replace("${imsi}", imsi).replace("${bsc_lac}", bsc_lac)
							.replace("${bsc_cid}", bsc_cid).replace("${mobile}", mobile).replace("${iccid}", iccid)
							.replace("${mac}", mac).replace("${cpparm}", cpparm).replace("${fmt}", fmt)
							.replace("${isp}", isp);
					//发送请求
					backMsg = this.sendGet(url);
				}else if(InfStatic.SEND_TYPE_POST.equals(send_type)){	//post方式
					charge_code = charge_code.replace("${imei}", imei).replace("${imsi}", imsi).replace("${bsc_lac}", bsc_lac)
							.replace("${bsc_cid}", bsc_cid).replace("${mobile}", mobile).replace("${iccid}", iccid)
							.replace("${mac}", mac).replace("${cpparm}", cpparm).replace("${fmt}", fmt)
							.replace("${isp}", isp);
					//发送请求
					backMsg = this.sendPost(url,charge_code);
				}else{
					errorBackList.add("id:" + id + ",code_name:"+code_name+";请求方式不对POST/GET");
				}
				if(InfStatic.BACK_MSG_JSON.equals(back_msg_type)){//json方式
					analysisBackMsg = this.analysisJson(backMsg, returnForm,backForm);
				}else if(InfStatic.BACK_MSG_XML.equals(back_msg_type)){
					analysisBackMsg = this.analysisXml(backMsg, returnForm);
				}else{
					errorBackList.add("id:" + id + ",code_name:"+code_name+";反馈报文格式不对JSON/XML");
				}
				backList.add(analysisBackMsg.replace("${code_id}", id));
			}
			Map<String, List> backMap = new HashMap<String,List>();
			backMap.put("data_list", backList);
			backMap.put("error", errorBackList);
			vo.setOk(true);
			vo.setMsg("ok");
			vo.setData(backMap);//只反馈请求成功的计费代码
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
			String ver_code_url = infService.getVerCodeUrlById(code_id);
			if(ver_code_url!=null||!"".equals(ver_code_url)){
				ver_code_url = ver_code_url.replace("${imei}", imei).replace("${imsi}", imsi).replace("${bsc_lac}", bsc_lac)
						.replace("${bsc_cid}", bsc_cid).replace("${mobile}", mobile).replace("${iccid}", iccid)
						.replace("${mac}", mac).replace("${cpparm}", cpparm).replace("${fmt}", fmt)
						.replace("${isp}", isp).replace("${order_id}", order_id).replace("${ver_code}", ver_code)
						.replace("${mobile}", mobile);
				String backMsg = this.sendGet(ver_code_url);
				vo.setOk(true);
				vo.setMsg("ver_code send sucess");
				Map<String, String> backMap = new HashMap<String,String>();
				backMap.put("order_id", order_id);
				vo.setData(backMap);
			}else{
				vo.setOk(false);
				vo.setMsg("ver_code_url is null");
				return vo;
			}
		}else{//请求类型不对
			vo.setOk(false);
			vo.setMsg("request_type is error");
			return vo;
		}
		return vo;
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
		JSONObject respJson = new JSONObject(); 
		Map<String, List<String>> map = new TreeMap<String, List<String>>();
		for(String one:returnForms){
		//	String[] temps = one.split("->");//解析参数和反馈参数
			String reqpara = one.substring(0,one.lastIndexOf(":"));
			String[] reqEnds = one.substring(one.lastIndexOf(":")+1,one.length()).split(",");
			for(String reqEnd:reqEnds){
				String[] t = reqEnd.split("->");//前面的是原始节点，后面的是替换参数
				List<String> result = getElement(backJson, reqpara+"e", t[0], null);
				map.put(t[1], result);
				
			}
		}
		Map<String, List<String>> oneParaMap = new TreeMap<String, List<String>>();
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
				for(Entry<String,String> single:singleList.entrySet()){
					String key = single.getKey();
					String value = single.getValue();
					for(Map<String, String> oneMap:list){
						oneMap.put(key, value);
					}
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
				list.add(json.getString(reqEnd));
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
	private String sendPost(String url, String charge_code) {
		return sendURL(url, charge_code, InfStatic.SEND_TYPE_POST);
	}
	/**
	 * get方式发送报文
	 * @param url
	 * @return
	 */
	private String sendGet(String url) {
		return sendURL(url, null, InfStatic.SEND_TYPE_GET);
	}
	/**
	 * 发送get\post请求
	 * @param msg
	 * @param postGetFlag
	 * @param returType
	 * @return
	 */
	private String sendURL(String msgUrl,String msgContent,String postGetFlag) {
		HttpURLConnection httpConn = null;
		String resultXml = "";
		try {
			logger.info("===***==创建http连接并设置参数。。。。");
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
		logger.debug("===***response msg==:" + resultXml);
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
		String str = "{\"ok\": \"true\",  \"msg\": \"请求成功\",  \"data_list\": [ { \"port1\": \"10086\",";
		            str += "\"msg1\": \"1\", \"type1\": \"0\"},{ \"port1\": \"10086\", \"msg1\": \"1\",\"type1\": \"0\"}], \"original\": {}}";
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
		String str3 = "{                               							"	
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
		JSONObject backJson = JSONUtil.getJSONFromString(str3);
		System.out.println(backJson);
		//System.out.println(backJson.getJSONArray("data_list").get(0));
		String r = "data_list(m):port1";
		List<String > list = getElement(backJson,"datas(m):data(s):msg(m)e","port",null);
		for(String s :list){
			System.out.println(s);
		}
		String result = analysisJson(str3, "datas(m):data(s):msg(m):port->port,cont->sum,tel->phone","{\"code_id\": \"${port}\",\"needVerCode\": \"${sum}\",\"phone\": \"${phone}\"}");
		System.out.println(result);
	}
	
}