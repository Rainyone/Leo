package com.larva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.larva.dao.IInfDao;
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
		Record r = iInfDao.get(app_id, app_key);
		logger.debug(r);
		ResultVO result = new ResultVO(false);
		if(r==null){//appKey验证不通过,判断日限量、月限量
			result.setOk(false);
			result.setMsg("appKey is not available!");
			return result;
		}
		Integer appArea = iInfDao.getAppAreaCount(app_id,area_id);
		logger.debug(appArea);
		if(appArea<=0){//判断适用省份,判断日限量、月限量
			result.setOk(false);
			result.setMsg("area is not available!");
			return result;
		}
		Integer appIsp = iInfDao.getAppIspCount(app_id,isp);
		logger.debug(appIsp);
		if(appIsp<=0){//判断运营商
			result.setOk(false);
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
		List<Record> list = iInfDao.getChargeCodes(app_id);
		List<Record> returnList = new ArrayList<Record>();
		ResultVO result = new ResultVO(false);
		if(list!=null&&list.size()>0){//判断是否有可用的计费代码，日月限量是否超出
			for(Record r:list){//遍历去判断区域、区域限量、可用运营商、可用时间
				String id = r.getStr("ID");
				Integer chargeAreaCount = iInfDao.getChargeArea(id,area_id);
				if(chargeAreaCount<=0){//获取可用区域，判断日月限量 ,没有通过校验
//					list.remove(r);
					logger.debug("area not avalible:"+id);
				}else{//继续其他判断
					Integer chargeIspCount = iInfDao.getChargeIsps(id,isp);
					if(chargeIspCount<=0){//运营商校验失败
//						list.remove(r);
						logger.debug("isp not avalible:"+id);
					}else{
						Integer chargeDisableTimeCount = iInfDao.getChargeDisableTimes(id);
						if(chargeDisableTimeCount>0){//在不可用时间范围内则校验不通过
//							list.remove(r);
							logger.debug("disable time:"+id);
						}else{//通过考验的
							returnList.add(r);
							logger.debug("send msg to url" + r.getStr("IN_URL"));
						}
					}
				}
			}
		}
		list = null;//释放资源
		if(returnList!=null&&returnList.size()>0){
			result.setOk(true);
			result.setMsg("It's ok!");
			result.setData(returnList);
		}
		return result;
	}
	@Override
	public String getVerCodeUrlById(String code_id) {
		return iInfDao.getVerCodeUrlById(code_id);
	}
	
}
