package com.larva.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IInfDao;
import com.mini.core.Record;
import com.mini.core.dao.MiniDao;
@Repository("iInfDao")
public class IInfDaoImpl extends MiniDao implements IInfDao {

	@Override
	public Record get(String app_id, String app_key) {
		String sql = "select * from t_app_manage t where t.id=? and t.app_key=? and t.state =1 and (t.date_limit >= t.date_count+1 or t.date_limit=-1) and (t.month_limit>=t.month_count+1 or t.month_limit=-1)";
		return this.find(sql, Record.class, new Object []{app_id,app_key});
	}

	@Override
	public List<Record> getList(String app_id,String area_id) {
		// TODO Auto-generated method stub
		String sql = "select * from t_app_area a where a.app_id = ? and a.area_id=? and a.state = 1";
		return null;
	}
	
	@Override
	public Integer getAppAreaCount(String app_id, String area_id) {
		String sql = "select count(1)as count from t_app_area a where a.app_id = ? and a.area_id=? and a.state = 1 and (a.date_limit >= a.date_count+1 or a.date_limit=-1)and (a.month_limit>=a.month_count+1 or a.month_limit=-1)";
		return this.find(sql, Integer.class, new Object []{app_id,area_id});
	}

	@Override
	public Integer getAppIspCount(String app_id, String isp) {
		String sql = "SELECT count(1) FROM t_app_isp A WHERE A.state = 1 AND A.app_id = ? AND A.isp_id = ?";
		return this.find(sql, Integer.class, new Object []{app_id,isp});
	}

	@Override
	public List<Record> getChargeCodes(String app_id) {
		String sql = "select b.* from t_app_charge_code a,t_charge_code b where a.state = 1 and b.state = 1 and a.charge_code_id = b.id " +
				" and (b.date_limit>b.date_count+1 or b.date_limit=-1) and (b.month_limit>b.month_count+1 or b.date_limit = -1)"+
				" and a.app_id = ? "; 
		return this.findList(sql, Record.class, new Object []{app_id});
	}

	@Override
	public Integer getChargeArea(String id, String area_id) {
		String sql = "select count(1) from t_charge_code_area a where a.state = 1 and a.charge_id = ? and a.area_id = ? "+ 
				" and (a.date_limit >= a.date_count+1 or a.date_limit=-1)and (a.month_limit>=a.month_count+1 or a.month_limit=-1)";
		return this.find(sql, Integer.class, new Object []{id,area_id});
	}

	@Override
	public Integer getChargeIsps(String id,String isp) {
		String sql = "SELECT count(1) from t_charge_code_isp a where a.state =1 and a.charge_id = ? and a.isp_id = ? ";
		return this.find(sql, Integer.class, new Object []{id,isp});
	}

	@Override
	public Integer getChargeDisableTimes(String id) {
		String sql = "SELECT count(1) from t_charge_disable_time a where a.state = 1 " +
				" and a.disable_start_time <= NOW() and a.disable_end_time >=NOW() and a.charge_code_id = ? ";	
		return this.find(sql, Integer.class, new Object []{id});
	}

	@Override
	public String getVerCodeUrlById(String order_id) {
		String sql = "select ver_code_url from t_charge_code where id=?";
		return  this.find(sql, String.class, new Object []{order_id});
	}
	
}
