package com.larva.dao;

import com.github.pagehelper.PageInfo;
import com.larva.model.Account;
import com.larva.model.AppManage;
import com.larva.model.Department;
import com.mini.core.PageResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sxjun
 * @time 2015/8/27 16:22
 */
public interface IAppManageDao  {
    int save(AppManage appManage);
    PageResult<AppManage> selectAppManages(int pageNow, int pageSize);
    
}
