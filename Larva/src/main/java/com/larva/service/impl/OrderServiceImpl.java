package com.larva.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IOrderDao;
import com.larva.model.AppInfLog;
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
	

}
