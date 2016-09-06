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
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.Department;
import com.larva.service.IAppManageService;
import com.larva.utils.Constants;
import com.larva.utils.UUIDUtil;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.mini.core.PageResult;

/**
 * @author sxjun
 * @time 2015/8/27 17:10
 */
@Service("appManageService")
public class AppManageServiceImpl implements IAppManageService {
    @Resource
    private IAppManageDao appManageDao;
	@Override
	public ResultVO saveAppManage(AppManageCreateVO createVO) {
		ResultVO resultVO = new ResultVO(true);
        //保存
		AppManage appManage = new AppManage();
		appManage.setId(UUIDUtil.getUUID());
		appManage.setAppKey(UUIDUtil.getUUID());
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
		m.put("create_time", format.format(appManage.getCreateTime()));
		m.put("update_user_name", appManage.getUpdateUserName());
		m.put("update_time", format.format(appManage.getUpdateTime()));
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
}
