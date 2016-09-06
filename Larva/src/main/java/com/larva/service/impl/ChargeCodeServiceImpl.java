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
		ChargeCode chargeCode = new ChargeCode();
		chargeCode.setId(UUIDUtil.getUUID());
		chargeCode.setCodeName(createVO.getCode_name());
		chargeCode.setUrl(createVO.getUrl());
		chargeCode.setChargeCode(createVO.getCharge_code());
		chargeCode.setSendType(createVO.getSend_type());
		chargeCode.setInfType(createVO.getInf_type());
		chargeCode.setBackMsgType(createVO.getBack_msg_type());
		chargeCode.setOrderBack(createVO.getOrder_back());
		chargeCode.setBackForm(createVO.getBack_form());
		chargeCode.setReturnForm(createVO.getReturn_form());
		chargeCode.setVerCodeUrl(createVO.getVer_code_url());
		chargeCode.setDateLimit(createVO.getDate_limit());
		chargeCode.setMonthLimit(createVO.getMonth_limit());
		chargeCode.setChannelType(createVO.getChannel_type());
		chargeCode.setLinkeName(createVO.getLinke_name());
		chargeCode.setPhoneNo(createVO.getPhone_no());
		chargeCode.setDetail(createVO.getDetail());
		chargeCode.setState(1);
		chargeCode.setCreateTime(new Date());
		chargeCode.setCreatePeopleName("");
		chargeCode.setUpdateTime(new Date());
		chargeCode.setUpdatePeopleName("");
		chargeCode.setDateCount(0);
		chargeCode.setMonthCount(0);
		chargeCode.setSuccessFlag(createVO.getSuccess_flag());
		chargeCode.setOrderIdCode(createVO.getOrder_id_code());
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
		m.put("update_time", updateTime!=null?format.format(chargeCode.getUpdateTime()):"");
		m.put("update_people_name", chargeCode.getUpdatePeopleName());
		m.put("date_count", chargeCode.getDateCount());
		m.put("month_count", chargeCode.getMonthCount());
		m.put("success_flag", chargeCode.getSuccessFlag());
		m.put("order_id_code", chargeCode.getOrderIdCode());
		return m;
	}
}
