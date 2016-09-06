package com.larva.dao;

import java.util.List;

import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.Permission;
import com.mini.core.PageResult;

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
}
