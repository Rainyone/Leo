package com.larva.dao;

import com.larva.model.LogOrder;
import com.larva.vo.OrderVo;
import com.mini.core.PageResult;

public interface IOrderDao {

	PageResult<LogOrder> getOrderList(int pageNo, int limit, OrderVo orderVo);

}
