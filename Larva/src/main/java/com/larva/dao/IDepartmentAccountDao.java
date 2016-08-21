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
	
	DepartmentAccount getByAccountId(List<DepartmentAccount> list,String accountId);
	
	DepartmentAccount getByAccountId(String accountId);
	
	String getDepIdByAccountId(String accountId);

    int deleteByDepId(String depId);

    int deleteByAccountId(String accountId);

    int save(DepartmentAccount departmentAccount);

	int update(DepartmentAccount departmentAccount);
}
