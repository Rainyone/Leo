package com.larva.dao;

import java.util.List;

import com.larva.model.Account;
import com.larva.model.DepartmentAccount;

/**
 * @author sxjun
 * @time 2015/9/2 10:20
 */
public interface IDepartmentAccountDao {
	
	List<DepartmentAccount> selectAll();
	
	DepartmentAccount getByAccountId(List<DepartmentAccount> list,int accountId);
	
	DepartmentAccount getByAccountId(int accountId);
	
    Integer getDepIdByAccountId(int accountId);

    int deleteByDepId(int depId);

    int deleteByAccountId(int accountId);

    int save(DepartmentAccount departmentAccount);

	int update(DepartmentAccount departmentAccount);
}
