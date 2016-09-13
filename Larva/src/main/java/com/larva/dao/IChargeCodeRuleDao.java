package com.larva.dao;

import java.util.List;

import com.larva.model.ChargeCodeAndIsp;
import com.larva.model.ChargeCodeIsp;
import com.larva.model.ChargeDisableTime;
import com.mini.core.PageResult;
import com.mini.core.Record;

public interface IChargeCodeRuleDao {
	PageResult<ChargeDisableTime> selectPage(int limit,int ffset,String chargeCodeId);

	int saveDisableTime(ChargeDisableTime cd);

	int deleteChargeCodeDisableTime(String id, String updateUser);
	/**
	 * 获取计费代码的运营商设置信息
	 * @param limit
	 * @param ffset
	 * @return
	 */
	PageResult<ChargeCodeAndIsp> getChargeCodeAndIsps(int limit,int ffset);

	Record getChargeCodeIsp(String code_id, String isp_id);

	int updateState(String code_id, String isp_id, int isChecked,String update_people_name);

	int insertChargeCodeIsp(ChargeCodeIsp isp);

	List<Record> getListChargeArea(String chargeCodeId);
}
