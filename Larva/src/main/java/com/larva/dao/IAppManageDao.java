package com.larva.dao;

import java.util.List;

import com.larva.model.AppAndChargeCode;
import com.larva.model.AppAndIsp;
import com.larva.model.AppChargeCode;
import com.larva.model.AppIsp;
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.Permission;
import com.mini.core.PageResult;
import com.mini.core.Record;

/**
 * @author sxjun
 * @time 2015/8/27 16:22
 */
public interface IAppManageDao  {
   
    PageResult<AppManage> selectAppManages(int pageNow, int pageSize);
    
    List<AreaManage> selectAllAreas();
    
    AreaManage get(List<AreaManage> areaManageList, String id);
    
    int save(AppManage appManage);
    
    int edit(AppManage appManage);
    
    int deleteAPP(String ids, String czr);

	int setNeaKey(String app_key, String user,String id);

	PageResult<AppAndIsp> getAreaIsps(int limit, int pageNo);

	Record getAppIsp(String app_id, String isp_id);

	int updateState(String app_id, String isp_id, int isChecked,
			String update_people_name);

	int insertAppIsp(AppIsp isp);

	List<Record> getListChargeArea(String app_id);

	int updateAppAreaById(String id, int rxl, int yxl, int checked);

	int setAppArea(String id, String area_id, String app_id, int rxl, int yxl,
			int checked);

	PageResult<AppAndChargeCode> getAppAndChargeCodes(int limit, int pageNo,
			String app_id);

	PageResult<Record> getListChargeCodes(int limit, int pageNo, String app_id);

	Record getAppCodes(String charge_id, String app_id);

	int updatetAppCodeState(String charge_id, String app_id, int isChecked,
			String update_people_name);

	int insertAppCode(AppChargeCode a);

	int deleteappCode(String id, String updateUser);
}
