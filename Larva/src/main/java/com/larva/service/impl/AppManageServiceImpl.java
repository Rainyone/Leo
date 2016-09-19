package com.larva.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IAppManageDao;
import com.larva.model.AppAndChargeCode;
import com.larva.model.AppAndIsp;
import com.larva.model.AppChargeCode;
import com.larva.model.AppIsp;
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.ChargeCodeAndIsp;
import com.larva.model.ChargeCodeIsp;
import com.larva.model.ChargeDisableTime;
import com.larva.model.Department;
import com.larva.service.IAppManageService;
import com.larva.utils.Constants;
import com.larva.utils.StrKit;
import com.larva.utils.UUIDUtil;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.mini.core.PageResult;
import com.mini.core.Record;

@Service("appManageService")
public class AppManageServiceImpl implements IAppManageService {
    @Resource
    private IAppManageDao appManageDao;
	@Override
	public ResultVO saveAppManage(AppManageCreateVO createVO) {
		ResultVO resultVO = new ResultVO(true);
        //保存
		AppManage appManage = new AppManage();
		appManage.setId(createVO.getApp_id());
		appManage.setAppKey(createVO.getApp_key());
		appManage.setAppName(createVO.getApp_name());
		appManage.setAppPackageName(createVO.getApp_package_name());
		appManage.setChannel(createVO.getChannel());
		appManage.setDateLimit(createVO.getDate_limit());
		appManage.setMonthLimit(createVO.getMonth_limit());
		appManage.setLinkName(createVO.getLink_name());
		appManage.setPhoneNo(createVO.getPhone_no());
		appManage.setDescription(createVO.getDescription());
		appManage.setState("1");
		appManage.setCreateTime(new Date());
		appManage.setCreateUserName(createVO.getCreate_user_name());
		appManage.setUpdateTime(new Date());
		appManage.setUpdateUserName(createVO.getUpdate_user_name());
		appManageDao.save(appManage);
		
        resultVO.setMsg("创建APP成功!");
        return resultVO;
	}
	
	@Override
	public ResultVO editAppManage(AppManageEditVO createVO) {
		ResultVO resultVO = new ResultVO(true);
        //保存
		AppManage appManage = new AppManage();	
		appManage.setId(createVO.getId());
		appManage.setAppName(createVO.getApp_name());
		appManage.setAppPackageName(createVO.getApp_package_name());
		appManage.setChannel(createVO.getChannel());
		appManage.setDateLimit(createVO.getDate_limit());
		appManage.setMonthLimit(createVO.getMonth_limit());
		appManage.setLinkName(createVO.getLink_name());
		appManage.setPhoneNo(createVO.getPhone_no());
		appManage.setDescription(createVO.getDescription());
		appManage.setUpdateTime(new Date());
		appManage.setUpdateUserName(createVO.getUpdate_user_name());
		appManageDao.edit(appManage);

        resultVO.setMsg("编辑APP成功!");
        return resultVO;
	}
	
	@Override
	public Pager<Map<String, Object>> getAppManages(PagerReqVO pagerReqVO) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<AppManage> pagers = appManageDao.selectAppManages(pagerReqVO.getPageNo(),pagerReqVO.getLimit());
        List<AppManage> list = pagers.getResults();
        for (AppManage appManage : list) {
        	results.add(getAppManageMap(appManage));
        }
        return new Pager(results, pagers.getResultCount());
	}
	
	private Map<String,Object> getAppManageMap(AppManage appManage){
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", appManage.getId());
		m.put("app_key", appManage.getAppKey());
		m.put("channel", appManage.getChannel());
		m.put("app_name", appManage.getAppName());
		m.put("app_package_name",appManage.getAppPackageName());
		m.put("date_limit", appManage.getDateLimit());
		m.put("month_limit", appManage.getMonthLimit());
		m.put("link_name", appManage.getLinkName());
		m.put("phone_no", appManage.getPhoneNo());
		m.put("description",appManage.getDescription());
		m.put("create_user_name", appManage.getCreateUserName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date createDate = appManage.getCreateTime();
		m.put("create_time", createDate!=null?format.format(createDate):"");
		m.put("update_user_name", appManage.getUpdateUserName());
		Date updateDate = appManage.getUpdateTime();
		m.put("update_time", updateDate!=null?format.format(updateDate):"");
		return m;
	}
	
	public ResultVO getAreaTree() {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        Pager<Map<String,Object>> pager = this.getAllAreas();
        List<Map<String,Object>> list = pager.getRows();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("所有区域");
        superTree.setOpen(true);
        trees.add(superTree);
        for(Map<String,Object> depart:list){
            TreeNode tree = new TreeNode();
            tree.setId(depart.get("areid").toString());
            tree.setName(depart.get("areaname").toString());
            tree.setOpen(true);
            if(depart.get("parentId")!=null)
            	tree.setpId(depart.get("parentId").toString());
            else{
            	tree.setpId(Constants.SUPER_TREE_NODE);
            }
            trees.add(tree);
        }
        ResultVO resultVO = new ResultVO(true);
        resultVO.setData(trees);
		return resultVO;
	}
	
	public Pager<Map<String,Object>> getAllAreas() {
        List<Map<String,Object>> resultAreas = new ArrayList<Map<String,Object>>();
        List<AreaManage> areas = appManageDao.selectAllAreas();
        int size = 0;
        for(AreaManage area : areas){
        	resultAreas.add(getAreaMap(areas,area));
    	}
    	size = areas.size();
    	return new Pager(resultAreas,size);
	}
	
    private Map<String,Object> getAreaMap(List<AreaManage> areas,AreaManage area){
    	Map<String,Object> m = new HashMap<String,Object>();
		m.put("areid", area.getAreaId());
		m.put("areaname", area.getAreaName());
		m.put("parentid", area.getParentId());
		m.put("arealevel", area.getAreaLevel());
		m.put("status", area.getStatus());
		if(area.getParentId()!=null&&!Constants.SUPER_TREE_NODE.equals(area.getParentId()))
			{AreaManage d = appManageDao.get(areas,area.getParentId());
			m.put("parentName",d!=null?d.getAreaName():"");}
		return m;
    }

	
    /**
     * 删除APP
     *
     * @param menuId
     * @return
     */
    public ResultVO deleteAppManage(String[] appIds, String czr) {
        ResultVO resultVO = new ResultVO(true);
        String ids = "";
        for(int i = 0; i < appIds.length; i++) {
        	ids = ids + "'" + appIds[i] +"',";
        }
        ids = ids.substring(0,ids.length()-1);
    	appManageDao.deleteAPP(ids,czr);
        resultVO.setMsg("删除APP成功");
        return resultVO;
    }

	@Override
	public ResultVO updateNewKey(String app_key, String user,String id) {
		ResultVO resultVO = new ResultVO(false);
		if(appManageDao.setNeaKey(app_key,user,id)==1){
			resultVO.setOk(true);
			resultVO.setMsg("更新成功");
		}else{
			resultVO.setMsg("更新失败");
		}
		return resultVO;
	}

	@Override
	public Pager<Map<String, Object>> getAreaIsps(PagerReqVO pagerReqVO) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<AppAndIsp> pagers = appManageDao.getAreaIsps( pagerReqVO.getLimit(), pagerReqVO.getPageNo());
        List<AppAndIsp> list = pagers.getResults();
        for (AppAndIsp appAndIsp : list) {
        	results.add(getAppAndIsp(appAndIsp));
        }
        return new Pager(results, pagers.getResultCount());
	}

	private Map<String, Object> getAppAndIsp(AppAndIsp appAndIsp) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("app_id", appAndIsp.getAppId());
		m.put("app_name", appAndIsp.getAppName());
		m.put("dx", appAndIsp.getDx());
		m.put("yd", appAndIsp.getYd());
		m.put("lt", appAndIsp.getLt());
		m.put("qt", appAndIsp.getQt());
		return m;
	}

	@Override
	public ResultVO createAppIsps(String app_id, String isp_id, int isChecked,
			String update_people_name) {
		ResultVO vo = new ResultVO(false);
		int result = 0;
		//先查询
		Record ccis = appManageDao.getAppIsp(app_id,isp_id);
		int count = ccis.getInt("count");
		//如果有记录则更新状态
		if(count>0){
			result = appManageDao.updateState(app_id,isp_id,isChecked,update_people_name);
		}else{//如果没有记录则判断是添加还是删除。如果是删除则不用处理，如果是添加则入库
			if(isChecked==1){//添加
				AppIsp isp = new AppIsp();
				isp.setId(UUIDUtil.getUUID());
				isp.setAppId(app_id);
				isp.setIspId(isp_id);
				isp.setCreateTime(new Date());
				isp.setCreatePeopleName(update_people_name);
				isp.setState(1);
				result = appManageDao.insertAppIsp(isp);
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
	public ResultVO delAppIsps(String app_id, String update_people_name) {
		int result = 0;
		ResultVO vo = new ResultVO(false);
		result += appManageDao.updateState(app_id,"1001",0,update_people_name);
		result += appManageDao.updateState(app_id,"1002",0,update_people_name);
		result += appManageDao.updateState(app_id,"1003",0,update_people_name);
		result += appManageDao.updateState(app_id,"1004",0,update_people_name);
		if(result>0){
			vo.setOk(true);
			vo.setMsg("操作成功");
		}else{
			vo.setMsg("操作失败");
		}
		return vo;
	}

	@Override
	public ResultVO getListAppArea(String app_id) {
		ResultVO vo = new ResultVO(false);
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		List<Record> list = appManageDao.getListChargeArea(app_id);
		if(list!=null&&list.size()>0){
			for(Record r : list){
				results.add(getAreaAppeMap(r));
			}
			vo.setOk(true);
			vo.setMsg("请求成功");
			vo.setData(results);
		}else{
			vo.setMsg("请求失败");
		}
		return vo;
	}

	private Map<String, Object> getAreaAppeMap(Record r) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", r.getStr("id"));
		m.put("area_id", r.getStr("area_id"));
		m.put("app_id", r.getStr("app_id"));
		m.put("rxl", r.getStr("date_limit"));
		m.put("yxl", r.getStr("month_limit"));
		m.put("app_name", r.getStr("app_name"));
		return m;
	}

	@Override
	public ResultVO createOneAppArea(String id, String area_id, String app_id,
			int checked, int rxl, int yxl) {
		ResultVO vo = new ResultVO(false);
		int result = 0;
		if(StrKit.notBlank(id)){//不为空则更新
			result+=appManageDao.updateAppAreaById(id,rxl,yxl,checked);
		}else{//为空则入库
			if(checked==1){
				id = UUIDUtil.getUUID();
				result+=appManageDao.setAppArea(id,area_id,app_id,rxl,yxl,checked);
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

	@Override
	public Pager<Map<String, Object>> getListAppCodes(PagerReqVO pagerReqVO,
			String app_id) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<AppAndChargeCode> pagers = appManageDao.getAppAndChargeCodes( pagerReqVO.getLimit(), pagerReqVO.getPageNo(), app_id);
        List<AppAndChargeCode> list = pagers.getResults();
        for (AppAndChargeCode appAndChargeCode : list) {
        	results.add(getAppAndChargeCodeMap(appAndChargeCode));
        }
        return new Pager(results, pagers.getResultCount());
	}

	private Map<String, Object> getAppAndChargeCodeMap(
			AppAndChargeCode appAndChargeCode) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("id", appAndChargeCode.getAppCodeId());
		 map.put("app_id", appAndChargeCode.getAppId());
		 map.put("app_name", appAndChargeCode.getAppName());
		 map.put("charge_code_id", appAndChargeCode.getChargeCodeId());
		 map.put("charge_code_name", appAndChargeCode.getChargeCodeName());
		return map;
	}

	@Override
	public Pager<Map<String, Object>> getListChargeCodes(PagerReqVO pagerReqVO,
			String app_id) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		PageResult<Record> pagers = appManageDao.getListChargeCodes( pagerReqVO.getLimit(), pagerReqVO.getPageNo(), app_id);
        List<Record> list = pagers.getResults();
        for (Record r : list) {
        	results.add(getListChargeCodesMap(r));
        }
        return new Pager(results, pagers.getResultCount());
	}

	private Map<String, Object> getListChargeCodesMap(Record r) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("code_id", r.getStr("id"));
		 map.put("code_name", r.getStr("code_name"));
		 map.put("is_have", r.getInt("is_have"));
		return map;
	}

	@Override
	public ResultVO createAppCodes(String charge_id, String app_id,
			int isChecked, String update_people_name) {
		ResultVO vo = new ResultVO(false);
		int result = 0;
		//先查询
		Record ccis = appManageDao.getAppCodes(charge_id,app_id);
		int count = ccis.getInt("count");
		//如果有记录则更新状态
		if(count>0){
			result = appManageDao.updatetAppCodeState(charge_id,app_id,isChecked,update_people_name);
		}else{//如果没有记录则判断是添加还是删除。如果是删除则不用处理，如果是添加则入库
			if(isChecked==1){//添加
				AppChargeCode a = new AppChargeCode();
				a.setId(UUIDUtil.getUUID());
				a.setChargeCodeId(charge_id);
				a.setAppId(app_id);
				a.setDescription("");
				a.setState(1);
				a.setCreatePeopleName(update_people_name);
				a.setCreateTime(new Date());
				result = appManageDao.insertAppCode(a);
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
	public ResultVO deleteappCodes(String[] appCodeRuleIds,
			String updateUser) {
		int result = 0;
		ResultVO rv = new ResultVO(false);
		if(appCodeRuleIds!=null&&appCodeRuleIds.length>0){
			for(String id:appCodeRuleIds){
				result += appManageDao.deleteappCode(id,updateUser);
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
	
}
