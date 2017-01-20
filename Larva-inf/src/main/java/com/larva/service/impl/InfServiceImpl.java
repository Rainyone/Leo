package com.larva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.larva.dao.IInfDao;
import com.larva.model.AppInfLog;
import com.larva.model.LogOrder;
import com.larva.model.ReqLogOrder;
import com.larva.service.InfService;
import com.larva.vo.ResultVO;
import com.mini.core.Record;
@Service
public class InfServiceImpl implements InfService {
	private Logger logger = Logger.getLogger(InfServiceImpl.class);
	@Autowired
	private IInfDao iInfDao;
	//appid+appKey校验，查数据库看此appid和key 是否可用
	//判断是否在可用省份内
	@Override
	public ResultVO checkApp(String app_id, String app_key, String area_id,
			String isp) {
		logger.info("chechApp--app_id:" + app_id +",app_key:"+app_key + ",area_id:"+ area_id + ",isp:"+ isp);
		Record r = iInfDao.get(app_id, app_key);
		logger.debug(r);
		ResultVO result = new ResultVO(false);
		if(r==null){//appKey验证不通过,判断日限量、月限量
			result.setOk(false);
			logger.info("checkApp--app_id:" + app_id + ",appKey or limit is not available!");
			result.setMsg("appKey or limit is not available!");
			return result;
		}
		int is_have_area_check = r.getInt("is_have_area_check");
		if(is_have_area_check==1){//区域判断
			Integer appArea = iInfDao.getAppAreaCount(app_id,area_id);
			logger.debug(appArea);
			if(appArea<=0){//判断适用省份,判断日限量、月限量
				result.setOk(false);
				logger.info("checkApp--app_id:" + app_id + ",apparea or limit is not available!");
				result.setMsg("apparea or limit is not available!");
				return result;
			}
		}
		Integer appIsp = iInfDao.getAppIspCount(app_id,isp);
		logger.debug(appIsp);
		if(appIsp<=0){//判断运营商
			result.setOk(false);
			logger.info("checkApp--app_id:" + app_id + ",isp:"+ isp +",isp is not available!");
			result.setMsg("isp is not available!");
			return result;
		}
		result.setOk(true);
		result.setMsg("it's ok");
		result.setData(r);
		return result;
	}
	@Override
	public ResultVO checkAndGetChargeCode(String app_id, String area_id,
			String isp) {
		logger.info("checkAndGetChargeCode--app_id:"+ app_id + ",area_id:" + area_id +",isp:"+isp);
		List<Record> list = iInfDao.getChargeCodes(app_id);
		List<Record> returnList = new ArrayList<Record>();
		String errorStr = "";
		ResultVO result = new ResultVO(false);
		if(list!=null&&list.size()>0){//判断是否有可用的计费代码，日月限量是否超出
			for(Record r:list){//遍历去判断区域、区域限量、可用运营商、可用时间
				String id = r.getStr("ID");
				Integer chargeAreaCount = iInfDao.getChargeArea(id,area_id);
				if(chargeAreaCount<=0){//获取可用区域，判断日月限量 ,没有通过校验
					errorStr +="charge area not avalible:" + id + ";";
					logger.debug("charge area not avalible:"+id);
				}else{//继续其他判断
					Integer chargeIspCount = iInfDao.getChargeIsps(id,isp);
					if(chargeIspCount<=0){//运营商校验失败
						errorStr +="charge isp not avalible:" + id + ";";
						logger.debug("charge isp not avalible:"+id);
					}else{
						Integer chargeDisableTimeCount = iInfDao.getChargeDisableTimes(id);
						if(chargeDisableTimeCount>0){//在不可用时间范围内则校验不通过
							errorStr +="charge disable time:" + id + ";";
							logger.debug("charge disable time:"+id);
						}else{//通过考验的
							returnList.add(r);
							logger.debug("send msg to url" + r.getStr("IN_URL"));
						}
					}
				}
			}
		}
		logger.info("checkAndGetChargeCode--checkResult:" +errorStr);
		list = null;//释放资源
		if(returnList!=null&&returnList.size()>0){
			result.setOk(true);
			result.setMsg("It's ok!");
			result.setData(returnList);
		}else{
			result.setOk(false);
			result.setMsg("charge false");
		}
		return result;
	}
	@Override
	public Record getVerCodeUrlById(String code_id) {
		return iInfDao.getVerCodeUrlById(code_id);
	}
	@Override
	public Integer saveLogOrder(LogOrder logOrder) {
		if(logOrder.getOrderState()==-1){//请求记录
			ReqLogOrder r = new ReqLogOrder();
			r.setAppId(logOrder.getAppId());
			r.setAreaId(logOrder.getAreaId());
			r.setBscCid(logOrder.getBscCid());
			r.setBscLac(logOrder.getBscLac());
			r.setChargeCodeId(logOrder.getChargeCodeId());
			r.setChargePrice(logOrder.getChargePrice());
			r.setCpparm(logOrder.getCpparm());
			r.setCreateTime(logOrder.getCreateTime());
			r.setFmt(logOrder.getFmt());
			r.setIccid(logOrder.getIccid());
			r.setId(logOrder.getId());
			r.setImei(logOrder.getImei());
			r.setImsi(logOrder.getImsi());
			r.setIp(logOrder.getIp());
			r.setIspId(logOrder.getIspId());
			r.setMac(logOrder.getMac());
			r.setMid(logOrder.getMid());
			r.setMobile(logOrder.getMobile());
			r.setOrderNo(logOrder.getOrderNo());
			r.setOrderState(logOrder.getOrderState());
			r.setPrice(logOrder.getPrice());
			return iInfDao.saveReqLogOrder(r);
		}else{
			return iInfDao.saveLogOrder(logOrder);
		}
	}
	@Override
	public Integer updateLogOrder(String id, int inState, int oldState) {
		return iInfDao.updateLogOrder(id, inState, oldState);
	}
	@Override
	public String getAreaIdByImsi(String imsi) {
		return iInfDao.getAreaIdByImsi(imsi);
	}
	@Override
	public Integer updateCount(String app_id, String charge_id,String area_id) {
		Integer result = -1;
		result +=iInfDao.updateAppCount(app_id);
		result +=iInfDao.updateChargeCodeCount(charge_id,area_id);
		return result;
	}
	@Override
	public Integer updateLogOrderByOrderNo(String orderNo, Integer orderState) {
		return iInfDao.updateLogOrderByOrderNo(orderNo, orderState);
	}
	@Override
	public Integer updateOrderNoById(String id, String orderNo) {
		return iInfDao.updateOrderNoById(id,orderNo);
	}
	@Override
	public ResultVO checkOrderId(String order_id) {
		ResultVO vo = new ResultVO(false);
		Record r = iInfDao.checkOrderId(order_id);
		int count = r.getInt("count");
		if(count>=1){//有记录
			vo.setOk(true);
			vo.setData(count);
		}
		return vo;
	}
	@Override
	public Record getCallBackSuccessById(String charge_id) {
		return iInfDao.getCallBackSuccessById(charge_id);
	}
	@Override
	public int getCountChargeLog(String charge_key) {
		int result = iInfDao.getCountChargeLog(charge_key);
		return result;
	}
	@Override
	public int insertAppInfLog(AppInfLog aif) {
		return iInfDao.insertAppInfLog(aif);
	}
	
}
