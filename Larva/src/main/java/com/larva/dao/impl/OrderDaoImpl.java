package com.larva.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IOrderDao;
import com.larva.model.LogOrder;
import com.larva.utils.StrKit;
import com.larva.vo.OrderVo;
import com.mini.core.PageResult;
import com.mini.core.Record;
import com.mini.core.dao.MiniDao;

@Repository("orderDao")
public class OrderDaoImpl extends MiniDao  implements IOrderDao {

	@Override
	public PageResult<LogOrder> getOrderList(int pageNo, int limit,
			OrderVo orderVo) {
		String stateStr = "";
		if(orderVo.getOrder_state()==-1){
			stateStr = " <> -1";
		}else{
			stateStr = " = " + orderVo.getOrder_state();
		}
		String sql = "select l.id,l.order_no,l.app_id,a.app_name ,c.code_name as charge_code_name,l.order_state,area.areaName,isp.isp_name,"
				+ " l.imei,l.imsi,l.ip,l.bsc_lac,l.bsc_cid,l.mobile,l.iccid,l.mac,l.cpparm,l.price,l.create_time"
				+ " from t_log_order l,t_app_manage a,t_charge_code c,t_area area,t_static_isp isp"
				+ " where l.app_id = a.id and a.state = 1 and l.charge_code_id = c.id and c.state = 1 and l.area_id = area.areaId and l.isp_id = isp.id "
				+ " and l.order_state " + stateStr;
		 List<String> args=new ArrayList<String>();
		if(orderVo!=null){
			if(StrKit.notBlank(orderVo.getApp_name())){
				sql += " and a.app_name like ? ";
				args.add("%" + orderVo.getApp_name() +"%");
			}
			if(StrKit.notBlank(orderVo.getCode_name())){
				sql += " and c.code_name like ? ";
				args.add( "%" + orderVo.getCode_name() +"%");
			}
			
			if(StrKit.notBlank(orderVo.getDatetimeEnd())&&StrKit.notBlank(orderVo.getDatetimeStart())){
				sql += "  and l.create_time BETWEEN ? and ? ";
				args.add(orderVo.getDatetimeStart());
				args.add(orderVo.getDatetimeEnd());
			}else if(StrKit.notBlank(orderVo.getDatetimeEnd())&&StrKit.isBlank(orderVo.getDatetimeStart())){
				sql += "  and l.create_time >= DATE_SUB( now(), '%Y-%m-%d'), INTERVAL 3 MONTH )  "
						+ " and l.create_time<=? ";
				args.add(orderVo.getDatetimeEnd());
			}else if(StrKit.isBlank(orderVo.getDatetimeEnd())&&StrKit.notBlank(orderVo.getDatetimeStart())){
				sql += "  and l.create_time >= ?  "
						+ " and l.create_time<=now() ";
				args.add(orderVo.getDatetimeStart());
			}
			if(StrKit.notBlank(orderVo.getOrder_id())){
				sql +=" and l.id = ? ";
				args.add(orderVo.getOrder_id());
			}
		}
		sql += " order by l.create_time desc ";
		if(args!=null&&args.size()>0){
			return this.paginateResult(sql, pageNo, limit, LogOrder.class,args.toArray());
		}else{
			return this.paginateResult(sql, pageNo, limit, LogOrder.class);
		}
	}

	@Override
	public PageResult<Record> getPlatformQuery(int pageNo, int limit,
			String datetimeStart, String datetimeEnd) {
		String sql ="";
		List<String> args=new ArrayList<String>();
		if(StrKit.notBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
					+ " where td.datelist BETWEEN ? and ?   "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(datetimeStart);
			args.add(datetimeEnd);
		}else if(StrKit.notBlank(datetimeEnd)&&StrKit.isBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
					+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist and td.datelist < ? "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(datetimeEnd);
		}else if(StrKit.isBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
				+ " where  td.datelist >= ? and td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(datetimeStart);
		}else{//默认查30天前的
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
				+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist AND td.datelist < NOW()"
				+ " group by td.datelist order by td.datelist desc  ";
		}
		sql = "select * from ("+sql+") temp ";
		if(args!=null&&args.size()>0){
			return this.paginateResult(sql, pageNo, limit, Record.class,args.toArray());
		}else{
			return this.paginateResult(sql, pageNo, limit, Record.class);
		}
	}

	@Override
	public Record getPlatformQueryCount(String app_id,String charge_id) {
		String sql = "";
		List<String> args=new ArrayList<String>();
		if(StrKit.isBlank(app_id)&&!StrKit.isBlank(charge_id)){
			sql = "SELECT * FROM ( SELECT date_format(td.datelist,'%Y-%m') AS datelist, count(o.id) AS request_count, "
					+ " SUM(o.charge_price) / 1000 AS request_account, SUM( CASE WHEN o.order_state IN (1, 3, 5, 7) THEN 1 "
					+ " ELSE 0 END ) AS request_success_count FROM t_date td "
					+ " LEFT JOIN t_log_order o ON DATE_FORMAT(td.datelist, '%Y-%m-%d') = DATE_FORMAT(o.create_time, '%Y-%m-%d') and charge_code_id=? "
					+ " WHERE date_format(td.datelist,'%Y-%m')=date_format(now(),'%Y-%m') and td.datelist<=NOW() "
					+ " GROUP BY date_format(td.datelist,'%Y-%m') ORDER BY td.datelist DESC ) temp";
			args.add(charge_id);
		}else if(!StrKit.isBlank(app_id)&&StrKit.isBlank(charge_id)){
			sql = "SELECT * FROM ( SELECT date_format(td.datelist,'%Y-%m') AS datelist, count(o.id) AS request_count, "
					+ " SUM(o.charge_price) / 1000 AS request_account, SUM( CASE WHEN o.order_state IN (1, 3, 5, 7) THEN 1 "
					+ " ELSE 0 END ) AS request_success_count FROM t_date td "
					+ " LEFT JOIN t_log_order o ON DATE_FORMAT(td.datelist, '%Y-%m-%d') = DATE_FORMAT(o.create_time, '%Y-%m-%d') and app_id=? "
					+ " WHERE date_format(td.datelist,'%Y-%m')=date_format(now(),'%Y-%m') and td.datelist<=NOW() "
					+ " GROUP BY date_format(td.datelist,'%Y-%m') ORDER BY td.datelist DESC ) temp";
			args.add(app_id);
		}else{
			sql = "SELECT * FROM ( SELECT date_format(td.datelist,'%Y-%m') AS datelist, count(o.id) AS request_count, "
					+ " SUM(o.charge_price) / 1000 AS request_account, SUM( CASE WHEN o.order_state IN (1, 3, 5, 7) THEN 1 "
					+ " ELSE 0 END ) AS request_success_count FROM t_date td "
					+ " LEFT JOIN t_log_order o ON DATE_FORMAT(td.datelist, '%Y-%m-%d') = DATE_FORMAT(o.create_time, '%Y-%m-%d') "
					+ " WHERE date_format(td.datelist,'%Y-%m')=date_format(now(),'%Y-%m') and td.datelist<=NOW() "
					+ " GROUP BY date_format(td.datelist,'%Y-%m') ORDER BY td.datelist DESC ) temp";
		}
		return this.find(sql, Record.class,args.toArray());
	}

	@Override
	public List<Record> getAppAllList() {
		String sql = "select id,app_name from t_app_manage where state = 1 order by create_time desc ";
		return this.findList(sql, Record.class);
	}

	@Override
	public PageResult<Record> getAppQuery(int pageNo, int limit,
			String datetimeStart, String datetimeEnd,String app_id) {
		String sql ="";
		List<String> args=new ArrayList<String>();
		if(StrKit.notBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
					+ " where td.datelist BETWEEN ? and ?   "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
			args.add(datetimeStart);
			args.add(datetimeEnd);
		}else if(StrKit.notBlank(datetimeEnd)&&StrKit.isBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
					+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist and td.datelist < ? "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
			args.add(datetimeEnd);
		}else if(StrKit.isBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
				+ " where  td.datelist >= ? and td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
			args.add(datetimeStart);
		}else{//默认查30天前的
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
				+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist AND td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
		}
		sql = "select * from ("+sql+") temp ";
		if(args!=null&&args.size()>0){
			return this.paginateResult(sql, pageNo, limit, Record.class,args.toArray());
		}else{
			return this.paginateResult(sql, pageNo, limit, Record.class);
		}
	}

	@Override
	public List<Record> getChargeAllList() {
		String sql = "select id,code_name from t_charge_code where state = 1 order by create_time desc ";
		return this.findList(sql, Record.class);
	}

	@Override
	public PageResult<Record> getChargeQuery(int pageNo, int limit,
			String datetimeStart, String datetimeEnd, String charge_id) {
		String sql ="";
		List<String> args=new ArrayList<String>();
		if(StrKit.notBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
					+ " where td.datelist BETWEEN ? and ?   "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
			args.add(datetimeStart);
			args.add(datetimeEnd);
		}else if(StrKit.notBlank(datetimeEnd)&&StrKit.isBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
					+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist and td.datelist < ? "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
			args.add(datetimeEnd);
		}else if(StrKit.isBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
				+ " where  td.datelist >= ? and td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
			args.add(datetimeStart);
		}else{//默认查30天前的
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
				+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist AND td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
		}
		sql = "select * from ("+sql+") temp ";
		if(args!=null&&args.size()>0){
			return this.paginateResult(sql, pageNo, limit, Record.class,args.toArray());
		}else{
			return this.paginateResult(sql, pageNo, limit, Record.class);
		}
	}
	@Override
	public List<Record> getAppCharts(String datetimeStart, String datetimeEnd,String app_id) {
		String sql ="";
		List<String> args=new ArrayList<String>();
		if(StrKit.notBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
					+ " where td.datelist BETWEEN ? and ?   "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
			args.add(datetimeStart);
			args.add(datetimeEnd);
		}else if(StrKit.notBlank(datetimeEnd)&&StrKit.isBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
					+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist and td.datelist < ? "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
			args.add(datetimeEnd);
		}else if(StrKit.isBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
				+ " where  td.datelist >= ? and td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
			args.add(datetimeStart);
		}else{//默认查30天前的
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and app_id=? "
				+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist AND td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(app_id);
		}
		sql = "select * from ("+sql+") temp ";
		if(args!=null&&args.size()>0){
			return this.findList(sql,Record.class,args.toArray());
		}else{
			return this.findList(sql,Record.class);
		}
	}

	@Override
	public List<Record> getChargeCharts(String datetimeStart,
			String datetimeEnd, String charge_id) {
		String sql ="";
		List<String> args=new ArrayList<String>();
		if(StrKit.notBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
					+ " where td.datelist BETWEEN ? and ?   "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
			args.add(datetimeStart);
			args.add(datetimeEnd);
		}else if(StrKit.notBlank(datetimeEnd)&&StrKit.isBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
					+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist and td.datelist < ? "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
			args.add(datetimeEnd);
		}else if(StrKit.isBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
				+ " where  td.datelist >= ? and td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
			args.add(datetimeStart);
		}else{//默认查30天前的
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') and o.charge_code_id=? "
				+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist AND td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(charge_id);
		}
		sql = "select * from ("+sql+") temp ";
		if(args!=null&&args.size()>0){
			return this.findList(sql,Record.class,args.toArray());
		}else{
			return this.findList(sql,Record.class);
		}
	}

	@Override
	public List<Record> getPlatformCharts(String datetimeStart,
			String datetimeEnd) {
		String sql ="";
		List<String> args=new ArrayList<String>();
		if(StrKit.notBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
					+ " where td.datelist BETWEEN ? and ?   "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(datetimeStart);
			args.add(datetimeEnd);
		}else if(StrKit.notBlank(datetimeEnd)&&StrKit.isBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
					+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
					+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist and td.datelist < ? "
					+ " group by td.datelist order by td.datelist desc  ";
			args.add(datetimeEnd);
		}else if(StrKit.isBlank(datetimeEnd)&&StrKit.notBlank(datetimeStart)){
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
				+ " where  td.datelist >= ? and td.datelist < NOW() "
				+ " group by td.datelist order by td.datelist desc  ";
			args.add(datetimeStart);
		}else{//默认查30天前的
			sql = "SELECT td.datelist as datelist,count(o.id) as request_count,  SUM(o.charge_price)/1000 as request_account, SUM(CASE WHEN o.order_state in(1,3,5,7) THEN 1 else 0 END  ) as request_success_count  "
				+ " FROM t_date td LEFT JOIN t_log_order o on DATE_FORMAT(td.datelist,'%Y-%m-%d') =  DATE_FORMAT(o.create_time,'%Y-%m-%d') "
				+ " where  DATE_SUB(CURDATE(), INTERVAL 30 DAY) <=td.datelist AND td.datelist < NOW()"
				+ " group by td.datelist order by td.datelist desc  ";
		}
		sql = "select * from ("+sql+") temp ";
		if(args!=null&&args.size()>0){
			return this.findList(sql,Record.class,args.toArray());
		}else{
			return this.findList(sql,Record.class);
		}
	}
	
}
