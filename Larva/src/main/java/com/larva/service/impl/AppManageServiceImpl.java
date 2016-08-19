package com.larva.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;

import com.larva.dao.IAccountDao;
import com.larva.dao.IAccountRoleDao;
import com.larva.dao.IAppManageDao;
import com.larva.dao.IDepartmentAccountDao;
import com.larva.dao.IDepartmentDao;
import com.larva.dao.IPermissionDao;
import com.larva.dao.IRoleDao;
import com.larva.dao.IRolePermissionDao;
import com.larva.model.Account;
import com.larva.model.AccountRole;
import com.larva.model.AppManage;
import com.larva.model.Department;
import com.larva.model.DepartmentAccount;
import com.larva.model.LoginLog;
import com.larva.model.Permission;
import com.larva.model.Role;
import com.larva.service.IAccountService;
import com.larva.service.IAppManageService;
import com.larva.utils.Constants;
import com.larva.utils.StrKit;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.larva.vo.UserCreateVO;
import com.larva.vo.UserEditVO;
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
		appManage.setAppName(createVO.getApp_name());
		appManage.setAppPackageName(createVO.getApp_package_name());
		appManage.setDescription(createVO.getDescription());
		appManage.setPhoneNo(createVO.getPhone_no());
		appManage.setLinkName(createVO.getLink_name());
		appManage.setCreateTime(new Date());
		appManageDao.save(appManage);
		
        resultVO.setMsg("注册成功");
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
		m.put("app_name", appManage.getAppName());
		m.put("app_package_name",appManage.getAppPackageName());
		m.put("link_name", appManage.getLinkName());
		m.put("phone_no", appManage.getPhoneNo());
		m.put("description",appManage.getDescription());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		m.put("create_time", format.format(appManage.getCreateTime()));
		m.put("id", appManage.getId());
		return m;
	}
}
