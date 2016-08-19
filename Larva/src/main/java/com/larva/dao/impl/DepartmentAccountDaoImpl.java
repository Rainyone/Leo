package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IDepartmentAccountDao;
import com.larva.model.DepartmentAccount;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/9/2 10:20
 */
@Repository("departmentAccountDao")
public class DepartmentAccountDaoImpl extends MiniDao  implements IDepartmentAccountDao {
    
    public List<DepartmentAccount> selectAll(){
    	return this.findList("select * from department_account order by id asc", DepartmentAccount.class);
    }
    
    public DepartmentAccount getByAccountId(List<DepartmentAccount> list,int accountId){
    	for (DepartmentAccount departmentAccount : list) {
            if (departmentAccount.getAccountId() == accountId) {
            	return departmentAccount;
            }
        }
        return null;
    }

    /**
     * 根据账号id获取部门id
     * @param accountId
     * @return
     */
    public Integer getDepIdByAccountId(int accountId) {
        return this.find("select dep_id from department_account where account_id=?",int.class,accountId);
    }

    /**
     * 根据部门id删除dep-account
     * @param depId
     * @return
     */
    public int deleteByDepId(int depId) {
        return this.execute("delete from department_account where 1=1 and dep_id=?",depId);
    }
    /**
     * 根据账号id删除dep-account
     * @param accountId
     * @return
     */
    public int deleteByAccountId(int accountId) {
    	return this.execute("delete from department_account where 1=1 and account_id=?",accountId);
    }
    //保存部门-账号
    @Override
    public int save(DepartmentAccount departmentAccount) {
        return this.insert(departmentAccount);
    }

	@Override
	public int update(DepartmentAccount departmentAccount) {
		return this.update(departmentAccount);
	}

	@Override
	public DepartmentAccount getByAccountId(int accountId) {
		return this.find("select * from department_account where account_id = ?",DepartmentAccount.class,accountId);
	}
}
