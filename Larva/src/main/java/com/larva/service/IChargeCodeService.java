package com.larva.service;

import java.util.Map;

import com.larva.vo.ChargeCodeCreateVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

/**
 * @author Rainy Wang
 * @time 2016/8/14
 */
public interface IChargeCodeService {

    ResultVO  saveChargeCode(ChargeCodeCreateVO createVO);

    Pager<Map<String, Object>> getChargeCodes(PagerReqVO pagerReqVO);
}
