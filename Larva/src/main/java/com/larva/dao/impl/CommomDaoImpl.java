package com.larva.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAppManageDao;
import com.larva.dao.ICommomDao;
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.IspManage;
import com.larva.model.Permission;
import com.mini.core.PageResult;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 16:23
 */

@Repository("commomDao")
public class CommomDaoImpl extends MiniDao implements ICommomDao {
    
    public List<AreaManage> selectProvAreas() {
        return this.findList("select * from t_area where status = '1' and areaLevel='1' ", AreaManage.class);
    }

	@Override
	public List<IspManage> selectIsps() {
		return  this.findList("select * from t_static_isp ", IspManage.class);
	}
}
