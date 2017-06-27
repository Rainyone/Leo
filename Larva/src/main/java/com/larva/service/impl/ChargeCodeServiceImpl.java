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
import com.larva.model.ChargeCode;
import com.larva.service.IChargeCodeService;
import com.larva.utils.UUIDUtil;
import com.larva.vo.ChargeCodeCreateVO;
import com.larva.vo.ChargeCodeEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.mini.core.PageResult;

@Service("chargeCodeService")
public class ChargeCodeServiceImpl implements IChargeCodeService {
    @Resource
    private IChargeCodeDao chargeCodeDao;
	@Override
	public ResultVO saveChargeCode(ChargeCodeCreateVO createVO) {
		ResultVO resultVO = new ResultVO(true);
        //保存
		String id = UUIDUtil.getUUID();
		ChargeCode chargeCode = new ChargeCode();
		chargeCode.setId(id);
		chargeCode.setCodeName(createVO.getCode_name());
		chargeCode.setUrl(createVO.getUrl());
		chargeCode.setChargeCode(createVO.getCharge_code());
		chargeCode.setSendType(createVO.getSend_type()[0]);
		chargeCode.setInfType(Integer.valueOf(createVO.getInf_type()[0]));
		chargeCode.setBackMsgType(createVO.getBack_msg_type()[0]);
		chargeCode.setOrderBack(createVO.getOrder_back());
		chargeCode.setBackForm(createVO.getBack_form());
		chargeCode.setReturnForm(createVO.getReturn_form());
		chargeCode.setVerCodeUrl(createVO.getVer_code_url());
		chargeCode.setDateLimit(createVO.getDate_limit());
		chargeCode.setMonthLimit(createVO.getMonth_limit());
		chargeCode.setChannelType(Integer.valueOf(createVO.getChannel_type()[0]));
		chargeCode.setLinkeName(createVO.getLinke_name());
		chargeCode.setPhoneNo(createVO.getPhone_no());
		chargeCode.setDetail(createVO.getDetail());
		chargeCode.setState(1);
		chargeCode.setCreateTime(new Date());
		chargeCode.setDateCount(0);
		chargeCode.setMonthCount(0);
		chargeCode.setSuccessFlag(createVO.getSuccess_flag());
		chargeCode.setOrderIdCode(createVO.getOrder_id_code());
		chargeCode.setCreatePeopleName(createVO.getCreate_people_name());
		chargeCode.setVerCodeSuccessFlag(createVO.getVer_code_success_flag());
		chargeCode.setCallbackcolumn(createVO.getCallbackcolumn());
		chargeCode.setCallbacksuccess(createVO.getCallbacksuccess());
		chargeCode.setCallbackurl("http://139.196.169.27:8087/Larva-inf/callback/" + id);
		chargeCode.setKeyMsg(createVO.getKey_msg());
		chargeCode.setChargePrice(createVO.getCharge_price());
		chargeCode.setIsLimit(createVO.getIs_limit());
		chargeCodeDao.save(chargeCode);
        resultVO.setMsg("操作成功!");
        return resultVO;
	}
	
	
	@Override
	public Pager<Map<String, Object>> getChargeCodes(PagerReqVO pagerReqVO) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<ChargeCode> pagers = chargeCodeDao.selectChargeCodes(pagerReqVO.getPageNo(),pagerReqVO.getLimit());
        List<ChargeCode> list = pagers.getResults();
        for (ChargeCode chargeCode : list) {
        	results.add(getAppManageMap(chargeCode));
        }
        return new Pager(results, pagers.getResultCount());
	}
	private Map<String,Object> getAppManageMap(ChargeCode chargeCode){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", chargeCode.getId());
		m.put("code_name", chargeCode.getCodeName());
		m.put("url", chargeCode.getUrl());
		m.put("charge_code", chargeCode.getChargeCode());
		m.put("send_type",chargeCode.getSendType());
		m.put("inf_type", chargeCode.getInfType());
		m.put("back_msg_type", chargeCode.getBackMsgType());
		m.put("order_back", chargeCode.getOrderBack());
		m.put("back_form", chargeCode.getBackForm());
		m.put("return_form",chargeCode.getReturnForm());
		m.put("ver_code_url", chargeCode.getVerCodeUrl());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		m.put("date_limit", chargeCode.getDateLimit());
		m.put("month_limit", chargeCode.getMonthLimit());
		m.put("channel_type", chargeCode.getChannelType());
		m.put("linke_name", chargeCode.getLinkeName());
		m.put("phone_no", chargeCode.getPhoneNo());
		m.put("detail", chargeCode.getDetail());
		m.put("state", chargeCode.getState());
		Date createTime = chargeCode.getCreateTime();
		m.put("create_time", createTime!=null?format.format(chargeCode.getCreateTime()):"");
		m.put("create_people_name", chargeCode.getCreatePeopleName());
		Date updateTime = chargeCode.getUpdateTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		m.put("update_time", updateTime!=null?format1.format(chargeCode.getUpdateTime()):"");
		m.put("update_people_name", chargeCode.getUpdatePeopleName());
		m.put("date_count", chargeCode.getDateCount());
		m.put("month_count", chargeCode.getMonthCount());
		m.put("success_flag", chargeCode.getSuccessFlag());
		m.put("order_id_code", chargeCode.getOrderIdCode());
		m.put("ver_code_success_flag", chargeCode.getVerCodeSuccessFlag());
		m.put("callbackcolumn", chargeCode.getCallbackcolumn());
		m.put("callbacksuccess", chargeCode.getCallbacksuccess());
		m.put("callbackurl", chargeCode.getCallbackurl());
		m.put("key_msg", chargeCode.getKeyMsg());
		m.put("charge_price", chargeCode.getChargePrice());
		m.put("is_limit", chargeCode.getIsLimit());
		return m;
	}


	@Override
	public ResultVO deleteChargeCodes(String[] chargeCodeIds,String userId) {
		int result = 0;
		ResultVO rv = new ResultVO(false);
		if(chargeCodeIds!=null&&chargeCodeIds.length>0){
			for(String id:chargeCodeIds){
				result += chargeCodeDao.deleteChargeCode(id,userId);
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
	public ResultVO editChargeCode(ChargeCodeEditVO editVo) {
		ChargeCode cc = new ChargeCode();
		cc.setId(editVo.getId());
		cc.setCodeName(editVo.getCode_name());
		cc.setUrl(editVo.getUrl());
		cc.setChargeCode(editVo.getCharge_code());
		cc.setSendType(editVo.getSend_type()[0]);
		cc.setInfType(Integer.valueOf(editVo.getInf_type()[0]));
		cc.setBackMsgType(editVo.getBack_msg_type()[0]);
		cc.setOrderBack(editVo.getOrder_back());
		cc.setBackForm(editVo.getBack_form());
		cc.setReturnForm(editVo.getReturn_form());
		cc.setVerCodeUrl(editVo.getVer_code_url());
		cc.setDateLimit(editVo.getDate_limit());
		cc.setMonthLimit(editVo.getDate_limit());
		cc.setChannelType(Integer.valueOf(editVo.getChannel_type()[0]));
		cc.setLinkeName(editVo.getLinke_name());
		cc.setPhoneNo(editVo.getPhone_no());
		cc.setDetail(editVo.getDetail());
		cc.setState(editVo.getState());
		cc.setCreateTime(editVo.getCreate_time());
		cc.setCreatePeopleName(editVo.getCreate_people_name());
		//cc.setUpdateTime(new Date());
		cc.setUpdatePeopleName(editVo.getUpdate_people_name());
		cc.setDateCount(editVo.getDateCount());
		cc.setMonthCount(editVo.getMonthCount());
		cc.setSuccessFlag(editVo.getSuccess_flag());
		cc.setOrderIdCode(editVo.getOrder_id_code());
		cc.setVerCodeSuccessFlag(editVo.getVer_code_success_flag());
		cc.setCallbackcolumn(editVo.getCallbackcolumn());
		cc.setCallbacksuccess(editVo.getCallbacksuccess());
		cc.setKeyMsg(editVo.getKey_msg());
		cc.setChargePrice(editVo.getCharge_price());
		cc.setIsLimit(editVo.getIs_limit());
		int result = chargeCodeDao.editChargeCode(cc);
		ResultVO r = new ResultVO();
		if(result>0){
			r.setOk(true);
			r.setMsg("更新成功");
		}else{
			r.setMsg("更新失败");
		}
		return r;
	}
}
