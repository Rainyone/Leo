package com.larva.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAccountDao;
import com.larva.dao.IAppManageDao;
import com.larva.model.Account;
import com.larva.model.AppManage;
import com.mini.core.PageResult;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 16:23
 */

@Repository("appManageDao")
public class AppManageDaoImpl extends MiniDao implements IAppManageDao {
	
    @Override
    public int save(AppManage appManage) {
        return this.insert(appManage);
    }

	@Override
	public PageResult<AppManage> selectAppManages(int pageNow, int pageSize) {
		return this.paginateResult("select * from t_app_manage where state = 1 ", pageNow, pageSize, AppManage.class);
	}

}
