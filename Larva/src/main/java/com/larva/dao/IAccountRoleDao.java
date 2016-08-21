package com.larva.dao;

import java.util.List;

import com.larva.model.AccountRole;

/**
 * @author sxjun
 * @time 2015/8/27 17:05
 */
public interface IAccountRoleDao {

	List<String> selectRoleIdSet(String accountId);

    int createAccountRole(AccountRole accountRole);

    int deleteByRoleId(String roleId);

    int deleteByAccountId(String accountId);

}
