package com.larva.dao;

import com.github.pagehelper.PageInfo;
import com.larva.model.Account;
import com.larva.model.Department;
import com.mini.core.PageResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author sxjun
 * @time 2015/8/27 16:22
 */
public interface IAccountDao  {

    Account get(int id);
    
    Account get(List<Account> list, int id);

    Account getByAccount(String account);
    
    List<Account> selectAll();
    
    List<Account> selectPage(int limit,int ffset);

    int save(Account account);

    int delete(int accountId);

    PageResult<Account> selectAccountIdManage(String querySql, int pageNow, int pageSize);

    PageResult<Account> selectAccountManage(Set<Integer> depIdSet, Set<Integer> excludeAccountIdSet, int pageNow, int pageSize);

	int update(Account account);
}
