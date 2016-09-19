package com.larva.service;

import java.util.Map;

import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

/**
 * @author Rainy Wang
 * @time 2016/8/14
 */
public interface IAppManageService {

    ResultVO  saveAppManage(AppManageCreateVO createVO);
    
    ResultVO  editAppManage(AppManageEditVO createVO);
    
    ResultVO deleteAppManage(String[] appIds, String czr);

    Pager<Map<String, Object>> getAppManages(PagerReqVO pagerReqVO);
    
    ResultVO getAreaTree();

	ResultVO updateNewKey(String app_key, String user,String id);

	Pager<Map<String, Object>> getAreaIsps(PagerReqVO pagerReqVO);

	ResultVO createAppIsps(String app_id, String isp_id, int isChecked,
			String update_people_name);

	ResultVO delAppIsps(String app_id, String update_people_name);

	ResultVO getListAppArea(String app_id);

	ResultVO createOneAppArea(String id, String area_id, String app_id,
			int checked, int rxl, int yxl);

	Pager<Map<String, Object>> getListAppCodes(PagerReqVO pagerReqVO,
			String app_id);

	Pager<Map<String, Object>> getListChargeCodes(PagerReqVO pagerReqVO,
			String app_id);

	ResultVO createAppCodes(String charge_id, String app_id, int isChecked,
			String update_people_name);

	ResultVO deleteappCodes(String[] appCodeRuleIds,
			String updateUser);
}
