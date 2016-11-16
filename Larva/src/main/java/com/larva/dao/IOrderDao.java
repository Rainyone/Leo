package com.larva.dao;

import java.util.List;

import com.larva.model.AppInfLog;
import com.larva.model.LogOrder;
import com.larva.vo.OrderVo;
import com.mini.core.PageResult;
import com.mini.core.Record;

public interface IOrderDao {

	PageResult<LogOrder> getOrderList(int pageNo, int limit, OrderVo orderVo);

	PageResult<Record> getPlatformQuery(int pageNo, int limit,
			String datetimeStart, String datetimeEnd);

	Record getPlatformQueryCount(String app_id,String charge_id);

	List<Record> getAppAllList();

	PageResult<Record> getAppQuery(int pageNo, int limit, String datetimeStart,
			String datetimeEnd,String app_id);

	List<Record> getChargeAllList();

	PageResult<Record> getChargeQuery(int pageNo, int limit,
			String datetimeStart, String datetimeEnd, String charge_id);
	
	List<Record> getAppCharts(String datetimeStart, String datetimeEnd,String app_id);

	List<Record> getChargeCharts(String datetimeStart, String datetimeEnd,
			String charge_id);

	List<Record> getPlatformCharts(String datetimeStart, String datetimeEnd);

	PageResult<AppInfLog> getAppLog(int pageNo, int limit, String charge_key);

}
