package com.larva.service;

import java.util.Map;

import com.larva.vo.ChargeCodeCreateVO;
import com.larva.vo.ChargeCodeEditVO;
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
    /**
     * 删除计费代码
     * @param chargeCodeIds
     * @return
     */
	ResultVO deleteChargeCodes(String[] chargeCodeIds,String userId);
	/**
	 * 修改计费代码
	 * @param editVo
	 * @return
	 */
	ResultVO editChargeCode(ChargeCodeEditVO editVo);
}
