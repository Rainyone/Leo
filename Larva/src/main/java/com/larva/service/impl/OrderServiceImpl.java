package com.larva.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.larva.dao.IAppManageDao;
import com.larva.dao.IOrderDao;
import com.larva.model.AppInfLog;
import com.larva.model.AppManage;
import com.larva.model.LogOrder;
import com.larva.service.IOrderService;
import com.larva.utils.DateUtils;
import com.larva.utils.StrKit;
import com.larva.vo.OrderVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.mini.core.PageResult;
import com.mini.core.Record;
@Service("orderService")
public class OrderServiceImpl implements IOrderService {
	@Resource
	private IOrderDao orderDao;
	@Resource
	private IAppManageDao appManageDao;
	@Override
	public Pager<Map<String, Object>> getOrderList(PagerReqVO pagerReqVO,
			OrderVo orderVo) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<LogOrder> pagers = orderDao.getOrderList(pagerReqVO.getPageNo(),pagerReqVO.getLimit(),orderVo);
        List<LogOrder> list = pagers.getResults();
        for (LogOrder logOrder : list) {
        	results.add(getOrderMap(logOrder));
        }
        return new Pager(results, pagers.getResultCount());
	}
	private Map<String, Object> getOrderMap(LogOrder logOrder) {
		int state = logOrder.getOrderState();
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", logOrder.getId());
		m.put("order_no",  logOrder.getOrderNo());
		m.put("app_id",  logOrder.getAppId());
		m.put("app_name",  logOrder.getAppName());
		m.put("charge_code_name", logOrder.getChargeCodeName()); 
		m.put("order_state", state==0?"订单发起":state==1?"运营商反馈成功":state==2?"运营商反馈失败":state==3?"运营商回调成功"
				:state==4?"运营商回调失败":state==5?"客户端回调成功":state==6?"客户端回调失败":state==7?"验证码发送成功":"");
		m.put("area_name", logOrder.getAreaName());
		m.put("isp_name", logOrder.getIspName());
		m.put("imei", logOrder.getImei());
		m.put("imsi", logOrder.getImsi());
		m.put("bsc_lac", logOrder.getBscLac());
		m.put("bsc_cid", logOrder.getBscCid());
		m.put("mobile", logOrder.getMobile());
		m.put("iccid", logOrder.getIccid());
		m.put("mac", logOrder.getMac());
		m.put("cpparm", logOrder.getCpparm());
		m.put("price", logOrder.getPrice());
		m.put("charge_price", logOrder.getChargePrice());
		m.put("ip", logOrder.getIp());
		Date createDate = logOrder.getCreateTime();
		m.put("create_time", createDate!=null?DateUtils.date2String(createDate,DateUtils.FULL_DATE_FORMAT):"");
		
		return m;
	}
	@Override
	public Pager<Map<String, Object>> getAppLog(PagerReqVO pagerReqVO, String charge_key) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<AppInfLog> pagers = orderDao.getAppLog(pagerReqVO.getPageNo(),pagerReqVO.getLimit(),charge_key);
		List<AppInfLog> list = pagers.getResults();
		for (AppInfLog r : list) {
        	results.add(getAppLogMap(r));
        }
		return new Pager(results, pagers.getResultCount());
	}
	private Map<String, Object> getAppLogMap (AppInfLog r){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", r.getId());
		m.put("charge_key", r.getChargeKey());
		m.put("imsi", r.getImsi());
		m.put("channel", r.getChannel());
		Date logtime = r.getLogtime();
		m.put("logtime",logtime!=null?DateUtils.date2String(logtime):"" );
		m.put("stepname", r.getStepname());
		m.put("context", r.getContext());
		Date create_time = r.getCreateTime();
		m.put("create_time", create_time!=null?DateUtils.date2String(create_time):"");
		m.put("ip", r.getIp());
		return m;
	}
	@Override
	public Pager<Map<String, Object>> getPlatformQuery(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<Record> pagers = orderDao.getPlatformQuery(pagerReqVO.getPageNo(),pagerReqVO.getLimit(),datetimeStart,datetimeEnd);
        List<Record> list = pagers.getResults();
        for (Record r : list) {
        	results.add(getPlatformQueryMap(r));
        }
        return new Pager(results, pagers.getResultCount());
	}
	private Map<String, Object> getPlatformQueryMap(Record r) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("datelist", r.getStr("datelist"));
		String request_count =  r.getStr("request_count");
		if(StrKit.isBlank(request_count)){
			request_count = "0";
		}
		m.put("request_count", request_count);
		
		String request_account =  r.getStr("request_account");
		if(StrKit.isBlank(request_account)){
			request_account = "0";
		}
		m.put("request_account", request_account);
		
		String request_success_count =  r.getStr("request_success_count");
		if(StrKit.isBlank(request_success_count)){
			request_success_count = "0";
		}
		m.put("request_success_count",request_success_count);
		return m;
	}
	@Override
	public Map<String, Object>  getPlatformQueryCount() {
		Record r = orderDao.getPlatformQueryCount(null,null);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("datelist", r.getStr("datelist"));
		String request_count =  r.getStr("request_count");
		if(StrKit.isBlank(request_count)){
			request_count = "0";
		}
		m.put("request_count", request_count);
		
		String request_account =  r.getStr("request_account");
		if(StrKit.isBlank(request_account)){
			request_account = "0";
		}
		m.put("request_account", request_account);
		
		String request_success_count =  r.getStr("request_success_count");
		if(StrKit.isBlank(request_success_count)){
			request_success_count = "0";
		}
		m.put("request_success_count",request_success_count);
		return m;
	}
	@Override
	public List<Map<String, Object>> getAppAllList() {
		List<Record> list = orderDao.getAppAllList();
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(list!=null&&list.size()>0){
			for(Record r:list){
				Map<String, Object> m = getAppAllListMap(r);
				resultList.add(m);
			}
		}
		return resultList;
	}
	private Map<String, Object> getAppAllListMap(Record r) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("app_id", r.getStr("id"));
		m.put("app_name", r.getStr("app_name"));
		return m;
	}
	@Override
	public Map<String, Object> getAppQueryCount(String app_id) {
		Record r = orderDao.getPlatformQueryCount(app_id,null);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("datelist", r.getStr("datelist"));
		String request_count =  r.getStr("request_count");
		if(StrKit.isBlank(request_count)){
			request_count = "0";
		}
		m.put("request_count", request_count);
		
		String request_account =  r.getStr("request_account");
		if(StrKit.isBlank(request_account)){
			request_account = "0";
		}
		m.put("request_account", request_account);
		
		String request_success_count =  r.getStr("request_success_count");
		if(StrKit.isBlank(request_success_count)){
			request_success_count = "0";
		}
		m.put("request_success_count",request_success_count);
		return m;
	}
	@Override
	public Pager<Map<String, Object>> getAppQuery(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd,String app_id) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<Record> pagers = orderDao.getAppQuery(pagerReqVO.getPageNo(),pagerReqVO.getLimit(),datetimeStart,datetimeEnd,app_id);
        List<Record> list = pagers.getResults();
        for (Record r : list) {
        	results.add(getPlatformQueryMap(r));
        }
        return new Pager(results, pagers.getResultCount());
	}
	@Override
	public List<Map<String, Object>> getChargeAllList() {
		List<Record> list = orderDao.getChargeAllList();
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(list!=null&&list.size()>0){
			for(Record r:list){
				Map<String, Object> m = getChargeAllListMap(r);
				resultList.add(m);
			}
		}
		return resultList;
	}
	private Map<String, Object> getChargeAllListMap(Record r) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("charge_id", r.getStr("id"));
		m.put("charge_name", r.getStr("code_name"));
		return m;
	}
	@Override
	public Map<String, Object> getChargeQueryCount(String charge_id) {
		Record r = orderDao.getPlatformQueryCount(null,charge_id);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("datelist", r.getStr("datelist"));
		String request_count =  r.getStr("request_count");
		if(StrKit.isBlank(request_count)){
			request_count = "0";
		}
		m.put("request_count", request_count);
		
		String request_account =  r.getStr("request_account");
		if(StrKit.isBlank(request_account)){
			request_account = "0";
		}
		m.put("request_account", request_account);
		
		String request_success_count =  r.getStr("request_success_count");
		if(StrKit.isBlank(request_success_count)){
			request_success_count = "0";
		}
		m.put("request_success_count",request_success_count);
		return m;
	}
	@Override
	public Pager<Map<String, Object>> getChargeQuery(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd, String charge_id) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<Record> pagers = orderDao.getChargeQuery(pagerReqVO.getPageNo(),pagerReqVO.getLimit(),datetimeStart,datetimeEnd,charge_id);
        List<Record> list = pagers.getResults();
        for (Record r : list) {
        	results.add(getPlatformQueryMap(r));
        }
        return new Pager(results, pagers.getResultCount());
	}
	@Override
	public Map<String, Object> getAppCharts(String datetimeStart,
			String datetimeEnd, String app_id) {
		List<Record> list = orderDao.getAppCharts(datetimeStart, datetimeEnd, app_id);
		Map<String,Object> m = new HashMap<String,Object>();
		List<String> xdata = new ArrayList<String>();//日期
		List<String>  ydata_request_count = new ArrayList<String>();
		List<String> ydata_request_account = new ArrayList<String>();
		List<String>  ydata_request_success_count = new ArrayList<String>();
		
		for(Record r:list){
			xdata.add(r.getStr("datelist"));
			String request_count =  r.getStr("request_count");
			if(StrKit.isBlank(request_count)){
				request_count = "0";
			}
			ydata_request_count.add(request_count);
			
			String request_account =  r.getStr("request_account");
			if(StrKit.isBlank(request_account)){
				request_account = "0";
			}
			ydata_request_account.add(request_account);
			
			String request_success_count =  r.getStr("request_success_count");
			if(StrKit.isBlank(request_success_count)){
				request_success_count = "0";
			}
			ydata_request_success_count.add(request_success_count);
		}
		m.put("xdata", xdata);
		m.put("ydata_request_count", ydata_request_count);
		m.put("ydata_request_account", ydata_request_account);
		m.put("ydata_request_success_count", ydata_request_success_count);
		return m;
	}
	@Override
	public Map<String, Object> getChargeCharts(String datetimeStart,
			String datetimeEnd, String charge_id) {
		List<Record> list = orderDao.getChargeCharts(datetimeStart, datetimeEnd, charge_id);
		Map<String,Object> m = new HashMap<String,Object>();
		List<String> xdata = new ArrayList<String>();//日期
		List<String>  ydata_request_count = new ArrayList<String>();
		List<String> ydata_request_account = new ArrayList<String>();
		List<String>  ydata_request_success_count = new ArrayList<String>();
		
		for(Record r:list){
			xdata.add(r.getStr("datelist"));
			String request_count =  r.getStr("request_count");
			if(StrKit.isBlank(request_count)){
				request_count = "0";
			}
			ydata_request_count.add(request_count);
			
			String request_account =  r.getStr("request_account");
			if(StrKit.isBlank(request_account)){
				request_account = "0";
			}
			ydata_request_account.add(request_account);
			
			String request_success_count =  r.getStr("request_success_count");
			if(StrKit.isBlank(request_success_count)){
				request_success_count = "0";
			}
			ydata_request_success_count.add(request_success_count);
		}
		m.put("xdata", xdata);
		m.put("ydata_request_count", ydata_request_count);
		m.put("ydata_request_account", ydata_request_account);
		m.put("ydata_request_success_count", ydata_request_success_count);
		return m;
	}
	@Override
	public Map<String, Object> getPlatformCharts(String datetimeStart,
			String datetimeEnd) {
		List<Record> list = orderDao.getPlatformCharts(datetimeStart, datetimeEnd);
		Map<String,Object> m = new HashMap<String,Object>();
		List<String> xdata = new ArrayList<String>();//日期
		List<String>  ydata_request_count = new ArrayList<String>();
		List<String> ydata_request_account = new ArrayList<String>();
		List<String>  ydata_request_success_count = new ArrayList<String>();
		
		for(Record r:list){
			xdata.add(r.getStr("datelist"));
			String request_count =  r.getStr("request_count");
			if(StrKit.isBlank(request_count)){
				request_count = "0";
			}
			ydata_request_count.add(request_count);
			
			String request_account =  r.getStr("request_account");
			if(StrKit.isBlank(request_account)){
				request_account = "0";
			}
			ydata_request_account.add(request_account);
			
			String request_success_count =  r.getStr("request_success_count");
			if(StrKit.isBlank(request_success_count)){
				request_success_count = "0";
			}
			ydata_request_success_count.add(request_success_count);
		}
		m.put("xdata", xdata);
		m.put("ydata_request_count", ydata_request_count);
		m.put("ydata_request_account", ydata_request_account);
		m.put("ydata_request_success_count", ydata_request_success_count);
		return m;
	}
	@Override
	public List<Map<String,String>> queryCols(String datetimeStart,
			String datetimeEnd) {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		if(StringUtils.isNotBlank(datetimeStart)||StringUtils.isNotBlank(datetimeEnd)){//有选择时间
			if(StringUtils.isNotBlank(datetimeStart)&&!StringUtils.isNotBlank(datetimeEnd)){//只选了开始时间
				//当前时间减开始时间，如果小于60则允许查询。如果大于60在需要提醒
				int days = DateUtils.daysBetween(DateUtils.string2Date(datetimeStart), new Date());//开始时间在最近2个月内
				if(days<=60){
					result = getCols(datetimeStart,DateUtils.date2String(new Date(),DateUtils.SIMPLE_DATE_FORMAT));
				}
			}else if(!StringUtils.isNotBlank(datetimeStart)&&StringUtils.isNotBlank(datetimeEnd)){//只选了结束时间
				//默认往前推15天查询
				Date thisDate = DateUtils.addDays(DateUtils.string2Date(datetimeEnd), -15);
				String startTime = DateUtils.date2String(thisDate,DateUtils.SIMPLE_DATE_FORMAT);
				String endTime = DateUtils.date2String(DateUtils.string2Date(datetimeEnd),DateUtils.SIMPLE_DATE_FORMAT);
				result = getCols(startTime,endTime);
			}else{//两个都选择了
				//判断间隔是否小于60天，小于则允许查询
				int days = DateUtils.daysBetween(DateUtils.string2Date(datetimeStart), new Date());//开始时间在最近2个月内
				if(days<=60){
					result = getCols(datetimeStart,datetimeEnd);
				}
			}
		}else{
			//默认只查最近半个月
			Date thisDate = DateUtils.addDays(new Date(), -15);
			String startTime = DateUtils.date2String(thisDate,DateUtils.SIMPLE_DATE_FORMAT);
			String endTime = DateUtils.date2String(new Date(),DateUtils.SIMPLE_DATE_FORMAT);
			result = getCols(startTime,endTime);
		}
		return result;
	}
	
	public List<Map<String,String>> getCols(String datetimeStart,
			String datetimeEnd) {
		List<Record> list = orderDao.getCols(datetimeStart, datetimeEnd);
		List<Map<String,String>> cols = new ArrayList<Map<String,String>>();
		Map<String,String> filedName = new HashMap<String,String>();
		filedName.put("field", "filedName");
		filedName.put("title", "APP名称\\时间");
		cols.add(filedName);
		for(Record r:list){
			Map<String,String> datelist = new HashMap<String,String>();
			Date d = r.getDate("datelist");
			String date = DateUtils.date2String(d,DateUtils.SIMPLE_DATE_FORMAT);
			datelist.put("field", date);
			datelist.put("title", date);
			cols.add(datelist);
		}
		return cols;
	}
	
	@Override
	public Pager<Map<String, Object>> queryColsResults(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd,String app_id,int queryType) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		//时间处理
		if(StringUtils.isNotBlank(datetimeStart)||StringUtils.isNotBlank(datetimeEnd)){//有选择时间
			if(StringUtils.isNotBlank(datetimeStart)&&!StringUtils.isNotBlank(datetimeEnd)){//只选了开始时间
				//当前时间减开始时间，如果小于60则允许查询。如果大于60在需要提醒
				int days = DateUtils.daysBetween(DateUtils.string2Date(datetimeStart), new Date());//开始时间在最近2个月内
				if(days<=60){
					datetimeEnd = DateUtils.date2String(new Date(),DateUtils.SIMPLE_DATE_FORMAT);
				}else{
					return null;
				}
			}else if(!StringUtils.isNotBlank(datetimeStart)&&StringUtils.isNotBlank(datetimeEnd)){//只选了结束时间
				//默认往前推15天查询
				Date thisDate = DateUtils.addDays(DateUtils.string2Date(datetimeEnd), -15);
				datetimeStart = DateUtils.date2String(thisDate,DateUtils.SIMPLE_DATE_FORMAT);
				datetimeEnd = DateUtils.date2String(DateUtils.string2Date(datetimeEnd),DateUtils.SIMPLE_DATE_FORMAT);
			}else{//两个都选择了
				//判断间隔是否小于60天，小于则允许查询
				int days = DateUtils.daysBetween(DateUtils.string2Date(datetimeStart), new Date());//开始时间在最近2个月内
				if(days<=60){
					return null;
				}
			}
		}else{
			//默认只查最近半个月
			Date thisDate = DateUtils.addDays(new Date(), -15);
			datetimeStart = DateUtils.date2String(thisDate,DateUtils.SIMPLE_DATE_FORMAT);
			datetimeEnd = DateUtils.date2String(new Date(),DateUtils.SIMPLE_DATE_FORMAT);
		}
		
		List<Record> listCols = orderDao.getCols(datetimeStart, datetimeEnd);
		if(listCols!=null&&listCols.size()>0){
			for(Record r:listCols){
				Date datetime = r.getDate("datelist");
				String date = DateUtils.date2String(datetime,DateUtils.SIMPLE_DATE_FORMAT);
				PageResult<AppManage> pagers = null;
				if(StrKit.notBlank(app_id)){
					Record logAppCount = orderDao.getLogAppCount(date, app_id);
					if(logAppCount!=null){
						
						Map<String, Object> map = new HashMap<String, Object>();
						if(queryType == 1){//请求量
							map.put(date, logAppCount.getInt("request_count"));
						}else if(queryType == 2) {//成功数
							map.put(date, logAppCount.getInt("success_count"));
						}else if(queryType == 3){//金额
							map.put(date, logAppCount.getBigDecimal("success_count"));
						}
						results.add(map);
					}
					break;
				}else{
					pagers = appManageDao.selectAppManages(1,Integer.MAX_VALUE);
					List<AppManage> list = pagers.getResults();
					if(list!=null&&list.size()>0){
						//然后遍历APP查日期值
						for (AppManage app : list) {
							app_id = app.getId();
							Record logAppCount = orderDao.getLogAppCount(date, app_id);
							if(logAppCount!=null){
								Map<String, Object> map = new HashMap<String, Object>();
								if(queryType == 1){//请求量
									map.put(date, logAppCount.getInt("request_count"));
								}else if(queryType == 2) {//成功数
									map.put(date, logAppCount.getInt("success_count"));
								}else if(queryType == 3){//金额
									map.put(date, logAppCount.getBigDecimal("success_count"));
								}
								results.add(map);
							}
						}
					}
				}
				
			}
		}
        return new Pager(results, results.size());
	}
}
