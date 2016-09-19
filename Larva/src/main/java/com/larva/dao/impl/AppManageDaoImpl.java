package com.larva.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAppManageDao;
import com.larva.model.AppAndChargeCode;
import com.larva.model.AppAndIsp;
import com.larva.model.AppChargeCode;
import com.larva.model.AppIsp;
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.ChargeCodeAndIsp;
import com.larva.model.ChargeDisableTime;
import com.larva.model.Permission;
import com.mini.core.PageResult;
import com.mini.core.Record;
import com.mini.core.dao.MiniDao;


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
		return this.paginateResult("select * from t_app_manage where state = 1 order by create_time desc ", pageNow, pageSize, AppManage.class);
	}

    public List<AreaManage> selectAllAreas() {
        return this.findList("select * from t_areas where status = '1' order by create_time desc ", AreaManage.class);
    }
    
    public AreaManage get(List<AreaManage> areaManageList,String id) {
        for(AreaManage areaManage:areaManageList){
            if(id.equals(areaManage.getAreaId())){
                return areaManage;
            }
        }
        return null;
    }

	@Override
	public int setNeaKey(String app_key, String user,String id) {
		String str = "update t_app_manage set app_key=?,update_user_name=?,create_time=now() where id=? and state = 1 ";
		return this.execute(str, app_key,user,id);
	}

	@Override
	public PageResult<AppAndIsp> getAreaIsps(int limit, int pageNo) {
		return this.paginateResult("select * from area_and_isp ", 
				pageNo, limit, AppAndIsp.class);
	}

	@Override
	public Record getAppIsp(String app_id, String isp_id) {
		String select = "select count(1) as count from t_app_isp where app_id = ? and isp_id = ? ";
		return this.find(select, Record.class, app_id,isp_id);
	}

	@Override
	public int updateState(String app_id, String isp_id, int isChecked,
			String update_people_name) {
		String updateState = "update t_app_isp set state = ?,update_time=now(),update_people_name=? where app_id = ? and isp_id = ? ";
		return this.execute(updateState, isChecked,update_people_name,app_id,isp_id);
	}

	@Override
	public int insertAppIsp(AppIsp isp) {
		return this.insert(isp);
	}

	@Override
	public List<Record> getListChargeArea(String app_id) {
		String str = "select t.id,t.area_id,t.app_id,t.date_limit,t.month_limit,c.app_name from t_app_area t,t_app_manage c where t.app_id = c.id and t.state = 1 and c.state = 1 and c.id = ?";
		return this.findList(str, Record.class, app_id);
	}

	@Override
	public int updateAppAreaById(String id, int rxl, int yxl, int checked) {
		String str = "update t_app_area set state=?,date_limit=?,month_limit=? where id = ? ";
		return this.execute(str, checked,rxl,yxl,id);
	}

	@Override
	public int setAppArea(String id, String area_id, String app_id, int rxl,
			int yxl, int checked) {
		String str = "insert t_app_area (id,app_id,area_id,date_limit,month_limit,state,create_time)"
				+ " values(?,?,?,?,?,1,now())";
		return this.execute(str, id,app_id,area_id,rxl,yxl);
	}

	@Override
	public PageResult<AppAndChargeCode> getAppAndChargeCodes(int limit,
			int pageNo, String app_id) {
		return this.paginateResult("select * from app_and_code where app_id = ?", 
				pageNo, limit, AppAndChargeCode.class,app_id);
	}

	@Override
	public PageResult<Record> getListChargeCodes(int limit, int pageNo,
			String app_id) {
		return this.paginateResult("select c.id,c.code_name,CASE WHEN a.id is null then 0 else 1 end as is_have from t_charge_code c left join t_app_charge_code a on a.state = 1 and c.id = a.charge_code_id and a.app_id = ? where c.state = 1 ", 
				pageNo, limit, Record.class,app_id);
	}

	@Override
	public Record getAppCodes(String charge_id, String app_id) {
		String str = "select count(1) as count from t_app_charge_code where charge_code_id =? and app_id = ? ";
		return this.find(str, Record.class, charge_id,app_id);
	}

	@Override
	public int updatetAppCodeState(String charge_id, String app_id,
			int isChecked, String update_people_name) {
		String update = "update t_app_charge_code set state = ?,update_time =now(),update_people_name=? where charge_code_id = ? and app_id=? ";
		return this.execute(update, isChecked,update_people_name,charge_id,app_id);
	}

	@Override
	public int insertAppCode(AppChargeCode a) {
		return this.insert(a);
	}

	@Override
	public int deleteappCode(String id, String updateUser) {
		String update = "update t_app_charge_code set state=0,update_time=now(),update_people_name=? where id = ? and state = 1";
		return this.execute(update, updateUser,id);
	}
}
