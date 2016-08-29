package com.larva.dao;

import com.larva.model.AppManage;
import com.mini.core.PageResult;

/**
 * @author sxjun
 * @time 2015/8/27 16:22
 */
public interface IAppManageDao  {
   
    PageResult<AppManage> selectAppManages(int pageNow, int pageSize);
    
    int save(AppManage appManage);
    
    int edit(AppManage appManage);
    
    int deleteAPP(String ids, String czr);
}
