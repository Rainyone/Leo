package com.larva.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.larva.dao.IInfDao;
import com.larva.model.AppInfLog;
import com.larva.model.AppManage;
import com.larva.model.ChargeCode;
import com.larva.model.ChargeCodeArea;
import com.larva.model.LogOrder;
import com.larva.model.ReqLogOrder;
import com.larva.utils.DateUtils;
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
	public Record getVerCodeUrlById(String order_id) {
		String sql = "select ver_code_url, id from t_charge_code where id=?";
		return  this.find(sql, Record.class, new Object []{order_id});
	}

	@Override
	public Integer saveLogOrder(LogOrder logOrder) {
		return this.insert(logOrder);
	}

	public Integer saveReqLogOrder(ReqLogOrder logOrder) {
		return this.insert(logOrder);
	}
	@Override
	public Integer updateLogOrder(String id, int inState, int oldState) {
		String update = "";
		if(oldState>0){
			update = "update t_log_order set order_state=? where id=? and order_state = ?";
			return this.execute(update, inState,id,oldState);
		}else{
			update = "update t_log_order set order_state=? where id=? ";
			return this.execute(update, inState,id);
		}
	}

	@Override
	public String getAreaIdByImsi(String imsi) {
		String sql = "select get_province(?) from dual";
		return this.find(sql, String.class, new Object []{imsi});
	}

	@Override
	public Integer updateAppCount(String app_id) {
		//日统计：只要更新时间是今天的则累计，不是今天的则初始化为1
		//月统计：只要更新时间是本月的则累计，不是则初始化为1
		String selectSql = "select id,update_time from t_app_manage where state=1 and id = ? ";
		AppManage appManage = this.find(selectSql, AppManage.class, app_id);
		if(appManage!=null){
			Date update_time = appManage.getUpdateTime();
			String oldDate = null;
			String oldMonth = null;
			if(update_time!=null){
				oldDate = DateUtils.date2String(update_time,"yyyy-MM-dd") ;
				oldMonth = DateUtils.date2String(update_time,"yyyy-MM") ;
			}
			
			String nowDate = DateUtils.date2String(new Date(),"yyyy-MM-dd");
			String updateSql = "update t_app_manage set ";
			if(!nowDate.equals(oldDate)){//如果不等，说明需要初始化单天记录为1
				updateSql += " date_count = 1,";
			}else{
				updateSql += " date_count = date_count+1,";
			}
			String nowMonth= DateUtils.date2String(new Date(),"yyyy-MM");
			if(!nowMonth.equals(oldMonth)){//如果不等，说明需要初始化月份记录为1
				updateSql +=" month_count = 1,";
			}else{
				updateSql +=" month_count = month_count + 1,";
			}
			updateSql +=" update_time=now() where state=1 and  id = ? ";
			return this.execute(updateSql, app_id);
		}else{
			return 0;
		}
	}

	@Override
	public Integer updateChargeCodeCount(String charge_id,String area_id) {
		Integer result = -1;
		//更新区域限量
		String selectSql = "select id,update_time from t_charge_code where state=1 and id = ? ";
		ChargeCode chargeCode = this.find(selectSql, ChargeCode.class, charge_id);
		if(chargeCode!=null){
			Date update_time = chargeCode.getUpdateTime();
			String oldDate = null;
			String oldMonth = null;
			if(update_time!=null){
				oldDate = DateUtils.date2String(update_time,"yyyy-MM-dd") ;
				oldMonth = DateUtils.date2String(update_time,"yyyy-MM") ;
			}
			String nowDate = DateUtils.date2String(new Date(),"yyyy-MM-dd");
			String updateSql = "update t_charge_code set ";
			if(!nowDate.equals(oldDate)){//如果不等，说明需要初始化单天记录为1
				updateSql += " date_count = 1,";
			}else{
				updateSql += " date_count = date_count+1,";
			}
			
			String nowMonth= DateUtils.date2String(new Date(),"yyyy-MM");
			if(!nowMonth.equals(oldMonth)){//如果不等，说明需要初始化月份记录为1
				updateSql +=" month_count = 1,";
			}else{
				updateSql +=" month_count = month_count + 1,";
			}
//		updateSql = updateSql.substring(0,updateSql.length()-1);
			updateSql +=" update_time=now() where state=1 and id = ? ";
			result += this.execute(updateSql, charge_id);
			
			//更新计费代码限量
			String selectAreaSql = "select id,update_time from t_charge_code_area where state=1 and charge_id = ? and area_id = ?";
			ChargeCodeArea chargeCodeArea = this.find(selectAreaSql, ChargeCodeArea.class, charge_id,area_id);
			Date area_update_time =  chargeCodeArea.getUpdateTime();
			String oldAreaDate = null;
			String oldAreaMonth = null;
			if(area_update_time!=null){
				oldAreaDate = DateUtils.date2String(update_time,"yyyy-MM-dd") ;
				oldAreaMonth = DateUtils.date2String(area_update_time,"yyyy-MM") ;
			}
			String nowAreaDate = DateUtils.date2String(new Date(),"yyyy-MM-dd");
			String updateAreaSql = "update t_charge_code_area set ";
			if(!nowAreaDate.equals(oldAreaDate)){//如果不等，说明需要初始化单天记录为1
				updateAreaSql += " date_count = 1,";
			}else{
				updateAreaSql += " date_count = date_count+1,";
			}
			String nowAreaMonth= DateUtils.date2String(new Date(),"yyyy-MM");
			if(!nowAreaMonth.equals(oldAreaMonth)){//如果不等，说明需要初始化月份记录为1
				updateAreaSql +=" month_count = 1,";
			}else{
				updateAreaSql +=" month_count = month_count + 1,";
			}
			updateAreaSql +=" update_time=now() where state=1 and charge_id = ? and  area_id = ? ";
			result += this.execute(updateAreaSql, charge_id,area_id);
		}
		return result;
	}

	@Override
	public Integer updateLogOrderByOrderNo(String orderNo, Integer orderState) {
		String update = "update t_log_order set order_state = ? where order_no=? ";
		return this.execute(update, orderState,orderNo);
	}

	@Override
	public Integer updateOrderNoById(String id, String orderNo) {
		String update = "update t_log_order set order_no= ? where id = ? ";
		return this.execute(update, orderNo,id);
	}

	@Override
	public Record checkOrderId(String order_id) {
		String select = "select count(1) as count from t_log_order where order_no=? and order_state = 1 ";
		return this.find(select, Record.class, order_id);
	}

	@Override
	public Record getCallBackSuccessById(String charge_id) {
		String get = "select callbacksuccess,callbackcolumn from t_charge_code where id=?";
		return this.find(get, Record.class, charge_id);
	}

	@Override
	public int getCountChargeLog(String charge_key) {
		String str = "select count(1) as count from t_log_order where id=? and order_state <> -1";
		Record r = this.find(str, Record.class, charge_key);
		int result = -1;
		if(r!=null){
			result = r.getInt("count");
		}
		return result;
	}

	@Override
	public int insertAppInfLog(AppInfLog aif) {
		return this.insert(aif);
	}
}
