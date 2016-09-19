package com.larva.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IChargeCodeDao;
import com.larva.dao.IChargeCodeRuleDao;
import com.larva.model.ChargeCode;
import com.larva.model.ChargeCodeAndIsp;
import com.larva.model.ChargeCodeIsp;
import com.larva.model.ChargeDisableTime;
import com.larva.service.IChargeCodeRuleService;
import com.larva.service.IChargeCodeService;
import com.larva.utils.DateUtils;
import com.larva.utils.StrKit;
import com.larva.utils.UUIDUtil;
import com.larva.vo.ChargeCodeCreateVO;
import com.larva.vo.ChargeCodeDisableTimeCreateVo;
import com.larva.vo.ChargeCodeEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.mini.core.PageResult;
import com.mini.core.Record;

@Service("chargeCodeRuleService")
public class ChargeCodeRuleServiceImpl implements IChargeCodeRuleService {
    @Resource
    private IChargeCodeRuleDao chargeCodeRuleDao;

	@Override
	public Pager<Map<String, Object>> getChargeCodeDisabledTimes(PagerReqVO pagerReqVO,
			String chargeCodeId) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<ChargeDisableTime> pagers = chargeCodeRuleDao.selectPage( pagerReqVO.getLimit(), pagerReqVO.getPageNo(), chargeCodeId);
        List<ChargeDisableTime> list = pagers.getResults();
        for (ChargeDisableTime chargeDisableTime : list) {
        	results.add(getChargeDisableTimeMap(chargeDisableTime));
        }
        return new Pager(results, pagers.getResultCount());
	}
    
	private Map<String,Object> getChargeDisableTimeMap(ChargeDisableTime chargeDisableTime){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", chargeDisableTime.getId());
		m.put("charge_code_id", chargeDisableTime.getChargeCodeId());
		m.put("disable_start_time", DateUtils.date2String(chargeDisableTime.getDisableStartTime(), "HH:mm"));
		m.put("disable_end_time", DateUtils.date2String(chargeDisableTime.getDisableEndTime(), "HH:mm"));
		m.put("state", chargeDisableTime.getState());
		m.put("create_time", chargeDisableTime.getCreateTime());
		m.put("create_people_name", chargeDisableTime.getCreatePeopleName());
		m.put("update_time", chargeDisableTime.getUpdateTime());
		m.put("update_people_name", chargeDisableTime.getUpdatePeopleName());
		m.put("code_name", chargeDisableTime.getCodeName());
		return m;
	}

	@Override
	public ResultVO saveChargeCodeDisableTime(
			ChargeCodeDisableTimeCreateVo createVO) {
		ResultVO vo = new ResultVO(false);
		int result = 0;
		if(createVO==null||StrKit.isBlank(createVO.getCharge_code_id())||createVO.getDisable_start_time()==null||createVO.getDisable_end_time().length<=0){
			vo.setMsg("数据没有填充完整");
			return vo;
		}else{
			String[] beginTime = createVO.getDisable_start_time();
			String[] endTime = createVO.getDisable_end_time();
			String charge_code_id = createVO.getCharge_code_id();
			String createPeople = createVO.getCreate_people_name();
			for(int i=0;i<beginTime.length;i++){
				ChargeDisableTime cd = new ChargeDisableTime();
				cd.setId(UUIDUtil.getUUID());
				cd.setChargeCodeId(charge_code_id);
				cd.setCreatePeopleName(createPeople);
				cd.setCreateTime(new Date());
				cd.setState(1);
				cd.setDisableStartTime(DateUtils.string2Date(beginTime[i], "HH:mm"));
				cd.setDisableEndTime(DateUtils.string2Date(endTime[i], "HH:mm"));
				result += chargeCodeRuleDao.saveDisableTime(cd);
			}
		}
		if(result>0){
			vo.setOk(true);
			vo.setMsg("新增成功");
		}
		return vo;
	}

	@Override
	public ResultVO deleteChargeCodeDisableTimes(String[] chargeCodeDisableTimeIds,
			String updateUser) {
		int result = 0;
		ResultVO rv = new ResultVO(false);
		if(chargeCodeDisableTimeIds!=null&&chargeCodeDisableTimeIds.length>0){
			for(String id:chargeCodeDisableTimeIds){
				result += chargeCodeRuleDao.deleteChargeCodeDisableTime(id,updateUser);
			}
		}
		if(result >0){
			rv.setOk(true);
			rv.setMsg("删除成功");
		}else{
			rv.setMsg("删除失败");
		}
		return rv;
	}

	@Override
	public Pager<Map<String, Object>> getChargeCodeIsps(PagerReqVO pagerReqVO) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<ChargeCodeAndIsp> pagers = chargeCodeRuleDao.getChargeCodeAndIsps( pagerReqVO.getLimit(), pagerReqVO.getPageNo());
        List<ChargeCodeAndIsp> list = pagers.getResults();
        for (ChargeCodeAndIsp chargeCodeAndIsp : list) {
        	results.add(getChargeCodeAndIspMap(chargeCodeAndIsp));
        }
        return new Pager(results, pagers.getResultCount());
	}
	private Map<String,Object> getChargeCodeAndIspMap(ChargeCodeAndIsp chargeCodeAndIsp){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("code_id", chargeCodeAndIsp.getId());
		m.put("code_name", chargeCodeAndIsp.getCodeName());
		m.put("dx", chargeCodeAndIsp.getDx());
		m.put("lt", chargeCodeAndIsp.getLt());
		m.put("yd", chargeCodeAndIsp.getYd());
		m.put("qt", chargeCodeAndIsp.getQt());
		return m;
	}

	@Override
	public ResultVO createChargeIsps(String code_id, String isp_id,
			int isChecked,String update_people_name) {
		ResultVO vo = new ResultVO(false);
		int result = 0;
		//先查询
		Record ccis = chargeCodeRuleDao.getChargeCodeIsp(code_id,isp_id);
		int count = ccis.getInt("count");
		//如果有记录则更新状态
		if(count>0){
			result = chargeCodeRuleDao.updateState(code_id,isp_id,isChecked,update_people_name);
		}else{//如果没有记录则判断是添加还是删除。如果是删除则不用处理，如果是添加则入库
			if(isChecked==1){//添加
				ChargeCodeIsp isp = new ChargeCodeIsp();
				isp.setId(UUIDUtil.getUUID());
				isp.setChargeId(code_id);
				isp.setIspId(isp_id);
				isp.setCreateTime(new Date());
				isp.setCreatePeopleName(update_people_name);
				isp.setState(1);
				result = chargeCodeRuleDao.insertChargeCodeIsp(isp);
			}
		}
		if(result >0){
			vo.setOk(true);
			vo.setMsg("操作成功");
		}else{
			vo.setMsg("操作失败");
		}
		return vo;
	}

	@Override
	public ResultVO delChargeIsps(String code_id, String update_people_name) {
		int result = 0;
		ResultVO vo = new ResultVO(false);
		result += chargeCodeRuleDao.updateState(code_id,"1001",0,update_people_name);
		result += chargeCodeRuleDao.updateState(code_id,"1002",0,update_people_name);
		result += chargeCodeRuleDao.updateState(code_id,"1003",0,update_people_name);
		result += chargeCodeRuleDao.updateState(code_id,"1004",0,update_people_name);
		if(result>0){
			vo.setOk(true);
			vo.setMsg("操作成功");
		}else{
			vo.setMsg("操作失败");
		}
		return vo;
	}

	@Override
	public ResultVO getListChargeArea(String chargeCodeId) {
		ResultVO vo = new ResultVO(false);
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		List<Record> list = chargeCodeRuleDao.getListChargeArea(chargeCodeId);
		if(list!=null&&list.size()>0){
			for(Record r : list){
				results.add(getAreaChargeCodeMap(r));
			}
			vo.setOk(true);
			vo.setMsg("请求成功");
			vo.setData(results);
		}else{
			vo.setMsg("请求失败");
		}
		return vo;
	}

	private Map<String, Object> getAreaChargeCodeMap(Record r) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", r.getStr("id"));
		m.put("area_id", r.getStr("area_id"));
		m.put("code_id", r.getStr("charge_id"));
		m.put("rxl", r.getStr("date_limit"));
		m.put("yxl", r.getStr("month_limit"));
		m.put("code_name", r.getStr("code_name"));
		return m;
	}

	@Override
	public ResultVO createOneChargeArea(String id,String area_id, String charge_code_id,
			int checked, int rxl, int yxl) {
		ResultVO vo = new ResultVO(false);
		int result = 0;
		if(StrKit.notBlank(id)){//不为空则更新
			result+=chargeCodeRuleDao.updateChargeAreaById(id,rxl,yxl,checked);
		}else{//为空则入库
			if(checked==1){
				id = UUIDUtil.getUUID();
				result+=chargeCodeRuleDao.setChargeArea(id,area_id,charge_code_id,rxl,yxl,checked);
			}
		}
		if(result>0){
			vo.setOk(true);
			vo.setMsg("提交成功");
		}else{
			vo.setMsg("提交失败");
		}
		return vo;
	}
	
}
