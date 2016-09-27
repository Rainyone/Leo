package com.larva.service;

import java.util.Map;

import com.larva.vo.OrderVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;

public interface IOrderService {

	Pager<Map<String, Object>> getOrderList(PagerReqVO pagerReqVO,
			OrderVo orderVo);

}
