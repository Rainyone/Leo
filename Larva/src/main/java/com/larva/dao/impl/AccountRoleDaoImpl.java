package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAccountRoleDao;
import com.larva.model.AccountRole;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 17:05
 */
@Repository("accountRoleDao")
public class AccountRoleDaoImpl extends MiniDao implements IAccountRoleDao {

    /**
     * 根据账号id获取角色id
     * @param accountId
     * @return
     */
    public List<String> selectRoleIdSet(String accountId) {
    	List<String> records =  this.findList("select role_id from account_role where account_id = ?",String.class,accountId);
        return records;
    }

    /**
     * 创建账号角色
     * @param accountRole
     * @return
     */
    public int createAccountRole(AccountRole accountRole) {
        return this.insert(accountRole);
    }

    /**
     * 根据角色删除account-role
     * @param roleId
     * @return
     */
    public int deleteByRoleId(String roleId) {
        return this.execute("delete from account_role where role_id = ?", roleId);
    }

    /**
     *   根据账号删除account-role
     * @param accountId
     * @return
     */
    public int deleteByAccountId(String accountId) {
    	return this.execute("delete from account_role where account_id = ?", accountId);
    }
}
