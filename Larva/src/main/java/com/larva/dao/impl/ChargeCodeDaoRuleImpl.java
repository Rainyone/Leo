package com.larva.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IChargeCodeRuleDao;
import com.larva.model.ChargeCodeAndIsp;
import com.larva.model.ChargeCodeIsp;
import com.larva.model.ChargeDisableTime;
import com.mini.core.PageResult;
import com.mini.core.Record;
import com.mini.core.dao.MiniDao;


@Repository("chargeCodeDaoRule")
public class ChargeCodeDaoRuleImpl extends MiniDao implements IChargeCodeRuleDao {

	@Override
	public PageResult<ChargeDisableTime> selectPage(int limit, int ffset,
			String chargeCodeId) {
		return this.paginateResult("select a.*,b.code_name from t_charge_disable_time a,t_charge_code b where a.state=1 and  b.state=1 and a.charge_code_id = b.id and a.charge_code_id =? order by a.create_time asc ", 
				ffset, limit, ChargeDisableTime.class,chargeCodeId);
	}

	@Override
	public int saveDisableTime(ChargeDisableTime cd) {
		String insert = "insert into t_charge_disable_time (id,charge_code_id,disable_start_time,disable_end_time,state,create_time,create_people_name)"
				+ " values(?,?,?,?,?,?,?)";
		return this.execute(insert, cd.getId(),cd.getChargeCodeId(),cd.getDisableStartTime(),cd.getDisableEndTime(),cd.getState(),
				cd.getCreateTime(),cd.getCreatePeopleName());
	}

	@Override
	public int deleteChargeCodeDisableTime(String id, String updateUser) {
		String delete = "update t_charge_disable_time set state=0,update_people_name=?,update_time=now() where id=? and state=1 ";
		return this.execute(delete, updateUser,id);
	}

	@Override
	public PageResult<ChargeCodeAndIsp> getChargeCodeAndIsps(int limit,
			int ffset) {
		return this.paginateResult("select * from charge_code_and_isp ", 
				ffset, limit, ChargeCodeAndIsp.class);
	}

	@Override
	public Record getChargeCodeIsp(String code_id, String isp_id) {
		String select = "select count(1) as count from t_charge_code_isp where charge_id = ? and isp_id = ? ";
		return this.find(select, Record.class, code_id,isp_id);
	}

	@Override
	public int updateState(String code_id, String isp_id, int isChecked,String update_people_name) {
		String updateState = "update t_charge_code_isp set state = ?,update_time=now(),update_people_name=? where charge_id = ? and isp_id = ? ";
		return this.execute(updateState, isChecked,update_people_name,code_id,isp_id);
	}

	@Override
	public int insertChargeCodeIsp(ChargeCodeIsp isp) {
		return this.insert(isp);
	}

	@Override
	public List<Record> getListChargeArea(String chargeCodeId) {
		String str = "select t.id,t.area_id,t.charge_id,t.date_limit,t.month_limit,c.code_name from t_charge_code_area t,t_charge_code c where t.charge_id = c.id and t.state = 1 and c.state = 1 and c.id = ? ";
		return this.findList(str, Record.class, chargeCodeId);
	}

	@Override
	public Integer updateChargeAreaById(String id, int rxl, int yxl, int checked) {
		String str = "update t_charge_code_area set state=?,date_limit=?,month_limit=? where id = ? ";
		return this.execute(str, checked,rxl,yxl,id);
	}

	@Override
	public Integer setChargeArea(String id,String area_id,String charge_code_id, int rxl, int yxl, int checked) {
		String str = "insert t_charge_code_area (id,charge_id,area_id,date_limit,month_limit,state,create_time)"
				+ " values(?,?,?,?,?,1,now())";
		return this.execute(str, id,charge_code_id,area_id,rxl,yxl);
	}
}
