package com.larva.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IOrderDao;
import com.larva.model.LogOrder;
import com.larva.service.IOrderService;
import com.larva.utils.DateUtils;
import com.larva.vo.OrderVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.mini.core.PageResult;
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
		m.put("ip", logOrder.getIp());
		Date createDate = logOrder.getCreateTime();
		m.put("create_time", createDate!=null?DateUtils.date2String(createDate,DateUtils.SIMPLE_DATE_FORMAT):"");
		
		return m;
	}

}
