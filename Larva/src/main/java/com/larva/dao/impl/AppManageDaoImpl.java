package com.larva.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAppManageDao;
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.Permission;
import com.mini.core.PageResult;
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
    public int edit(AppManage appManage) {
        return this.update(appManage);
    }

    public int deleteAPP(String ids, String czr) {
    	String sql = "update t_app_manage set state = '0',update_user_name = '"+czr+"',update_time = CURRENT_TIMESTAMP() where id in("+ids+") " ;
        return this.execute(sql);
    }
    
	@Override
	public PageResult<AppManage> selectAppManages(int pageNow, int pageSize) {
		return this.paginateResult("select * from t_app_manage where state = 1 ", pageNow, pageSize, AppManage.class);
	}

    public List<AreaManage> selectAllAreas() {
        return this.findList("select * from t_areas where status = '1'", AreaManage.class);
    }
    
    public AreaManage get(List<AreaManage> areaManageList,String id) {
        for(AreaManage areaManage:areaManageList){
            if(id.equals(areaManage.getAreaId())){
                return areaManage;
            }
        }
        return null;
    }
}
