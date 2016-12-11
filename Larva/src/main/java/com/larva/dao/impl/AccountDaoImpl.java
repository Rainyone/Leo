package com.larva.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IAccountDao;
import com.larva.model.Account;
import com.mini.core.PageResult;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 16:23
 */

@Repository("accountDao")
public class AccountDaoImpl extends MiniDao implements IAccountDao {
	
    /**
     * 获取所有用户
     *
     * @return
     */
    public List<Account>  selectPage(int limit,int pageNo) {
    	return this.paginate("select * from account",pageNo, limit, Account.class);
    }

    @Override
    public Account get(String id) {
        return this.findById(Account.class, id);
    }
    
    /**
     * 获取用户
     *
     * @param list
     * @param id
     * @return
     */
    public Account get(List<Account> list, String id) {
        for (Account account : list) {
            if (id.equals(account.getId())) {
                return account;
            }
        }
        return null;
    }
    
    /**
     * 获取所有用户
     *
     * @return
     */
    public List<Account> selectAll() {
        return this.findList("select * from account order by id asc", Account.class);
    }

    /**
     * @param account
     * @return
     */
    public Account getByAccount(String account) {
    	Account ac = this.find("select * from account where account = ?", Account.class, account);
        return ac;
    }

    @Override
    public int save(Account account) {
        return this.insert(account);
    }

    /**
     * 删除账号
     * @param accountId
     * @return
     */
    @Override
    public int delete(String accountId) {
        return this.deleteById(Account.class, accountId);
    }

    /**
     * 账号管理页面获取账号id
     * @return
     */
    @Override
    public PageResult<Account> selectAccountIdManage(String querySql,int pageNow,int pageSize) {
    	String sql = "select * from account_manage where 1=1";
    	PageResult<Account> pageResult =  this.paginateResult(sql, pageNow, pageSize, Account.class);
        return pageResult;
    }

    /**
     * 用户管理
     * @param depIdSet  要查询的部门id集合
     * @param excludeAccountIdSet   排除的不查询的账号id集合
     * @param pageNow
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<Account> selectAccountManage(Set<String> depIdSet, Set<String> excludeAccountIdSet, int pageNow, int pageSize) {
    	 String sql = "select * from account_manage where 1=1";
    	 if(depIdSet!=null&&depIdSet.size()>0){
    		 sql += " and dep_id in(";
    		 Iterator<String> it = depIdSet.iterator();  
    		 while (it.hasNext()) {  
    			 sql += it.next()+",";  
    		 } 
    		 sql = sql.substring(0,sql.length()-1);
    		 sql += ")";
    	 }
    	 
    	 if(excludeAccountIdSet!=null&&excludeAccountIdSet.size()>0){
    		 sql += " and id not in(";
    		 Iterator<String> it = excludeAccountIdSet.iterator();  
    		 while (it.hasNext()) {  
    			 sql += it.next()+",";  
    		 } 
    		 sql = sql.substring(0,sql.length()-1);
    		 sql += ")";
    	 }
    	 PageResult<Account> pageResult =  this.paginateResult(sql, pageNow, pageSize, Account.class);
        return pageResult;
    }

	@Override
	public int updateAcount(Account account) {
		return this.update(account);
	}
}
