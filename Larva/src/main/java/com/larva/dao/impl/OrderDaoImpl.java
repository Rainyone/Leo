package com.larva.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IOrderDao;
import com.larva.model.LogOrder;
import com.larva.utils.StrKit;
import com.larva.vo.OrderVo;
import com.mini.core.PageResult;
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
				sql += "  and l.create_time >= DATE_SUB( date_format('2016-09-04', '%Y-%m-%d'), INTERVAL 3 MONTH )  "
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
}
