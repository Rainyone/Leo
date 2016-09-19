package com.larva.service;

import java.util.Map;

import com.larva.vo.ChargeCodeDisableTimeCreateVo;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

public interface IChargeCodeRuleService {
	Pager<Map<String, Object>> getChargeCodeDisabledTimes(PagerReqVO pagerReqVO, String chargeCodeId);

	ResultVO saveChargeCodeDisableTime(ChargeCodeDisableTimeCreateVo createVO);

	ResultVO deleteChargeCodeDisableTimes(String[] chargeCodeIds, String updateUser);

	Pager<Map<String, Object>> getChargeCodeIsps(PagerReqVO pagerReqVO);

	ResultVO createChargeIsps(String code_id, String isp_id, int isChecked,String update_people_name);

	ResultVO delChargeIsps(String code_id, String update_people_name);

	ResultVO getListChargeArea(String chargeCodeId);

	ResultVO createOneChargeArea(String id, String area_id,String charge_code_id, int checked,
			int rxl, int yxl);
}
