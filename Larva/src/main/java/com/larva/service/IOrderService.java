package com.larva.service;

import java.util.List;
import java.util.Map;

import com.larva.vo.OrderVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.mini.core.Record;

public interface IOrderService {

	Pager<Map<String, Object>> getOrderList(PagerReqVO pagerReqVO,
			OrderVo orderVo);

	Pager<Map<String, Object>> getPlatformQuery(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd);

	Map<String, Object>  getPlatformQueryCount();

	List<Map<String, Object>> getAppAllList();

	Map<String, Object> getAppQueryCount(String app_id);

	Pager<Map<String, Object>> getAppQuery(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd,String app_id);

	List<Map<String, Object>> getChargeAllList();

	Map<String, Object> getChargeQueryCount(String charge_id);

	Pager<Map<String, Object>> getChargeQuery(PagerReqVO pagerReqVO,
			String datetimeStart, String datetimeEnd, String charge_id);

	Map<String, Object> getAppCharts(String datetimeStart, String datetimeEnd,
			String app_id);

	Map<String, Object> getChargeCharts(String datetimeStart,
			String datetimeEnd, String charge_id);

	Map<String, Object> getPlatformCharts(String datetimeStart,
			String datetimeEnd);

	Pager<Map<String, Object>> getAppLog(PagerReqVO pagerReqVO, String charge_key);

}
