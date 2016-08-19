package com.larva.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Service;

import com.larva.dao.IAccountDao;
import com.larva.dao.IAccountRoleDao;
import com.larva.dao.IDepartmentAccountDao;
import com.larva.dao.IDepartmentDao;
import com.larva.dao.IPermissionDao;
import com.larva.dao.IRoleDao;
import com.larva.dao.IRolePermissionDao;
import com.larva.model.Account;
import com.larva.model.AccountRole;
import com.larva.model.Department;
import com.larva.model.DepartmentAccount;
import com.larva.model.Permission;
import com.larva.model.Role;
import com.larva.service.IAccountService;
import com.larva.utils.Constants;
import com.larva.utils.StrKit;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.larva.vo.UserCreateVO;
import com.larva.vo.UserEditVO;
import com.mini.core.PageResult;

/**
 * @author sxjun
 * @time 2015/8/27 17:10
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Resource
    private IAccountDao accountDao;
    @Resource
    private IAccountRoleDao accountRoleDao;
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IRolePermissionDao rolePermissionDao;
    @Resource
    private IPermissionDao permissionDao;
    @Resource
    private IDepartmentAccountDao departmentAccountDao;
    @Resource
    private IDepartmentDao departmentDao;

    /**
     * 根据账号获取账号对象
     *
     * @param account
     * @return
     */
    public Account getAccountByAccount(String account) {
    	Account re =  accountDao.getByAccount(account);
        return re;
    }
    
    public Pager<Map<String,Object>> getPageUsers(PagerReqVO pagerReqVO,TreeNode tree){
    	 List<Map<String,Object>> resultAccounts = new ArrayList<Map<String,Object>>();
    	 //获取所有部门
         List<Department> departments  = departmentDao.selectAll();
         //获取所有用户
    	 List<Account> accounts = accountDao.selectAll();
    	 //获取所有角色
         List<Role> roles = roleDao.selectAll();
         //获取所有部门用户关系
         List<DepartmentAccount> departAccounts = departmentAccountDao.selectAll();
         
         int size = 0;
         if(tree!=null && StrKit.notBlank(tree.getId())&&!Constants.SUPER_TREE_NODE.equals(tree.getId())){
             int child = Integer.parseInt(tree.getId());
             //获取当前部门
             Department selectdepart = departmentDao.get(departments,child);
             
             //获取当前部门的子部门
             List<Department> depts = DepartmentServiceImpl.getChildrenDepartments(departments,child);
             depts.add(selectdepart);
             
             //获取当前部门的子部门
             List<DepartmentAccount> childrens = getChildrenDepartAccounts(departAccounts,depts);
             size = childrens.size();
             if(childrens!=null&&size>0){
                 if(childrens.size()<pagerReqVO.getLimit()){
                	 for(DepartmentAccount departmentAccount : childrens){
                		 Account account = accountDao.get(accounts, departmentAccount.getAccountId());
                		 resultAccounts.add(getAccountDepartRoles(accounts,departments,roles,departmentAccount,account));
                	 }
                 }else{
                     for(int i = (pagerReqVO.getPageNo()-1)*pagerReqVO.getLimit();i<Math.min(pagerReqVO.getLimit()*pagerReqVO.getPageNo(),size);i++){
                    	 Account account = accountDao.get(accounts, childrens.get(i).getAccountId());
                         resultAccounts.add(getAccountDepartRoles(accounts,departments,roles,childrens.get(i),account));
                     }
                 }
             }
         }else{
        	 size = accounts.size();
        	 List<Account> _accounts = accountDao.selectPage(pagerReqVO.getLimit(),pagerReqVO.getPageNo());
        	 for(Account account:_accounts){
        		 DepartmentAccount departmentAccount = departmentAccountDao.getByAccountId(departAccounts, account.getId());
        		 resultAccounts.add(getAccountDepartRoles(accounts,departments,roles,departmentAccount,account));
        	 }
         }
 		return new Pager(resultAccounts,size);
    }
    
    public Map<String,Object> getAccountDepartRoles(List<Account> accounts,List<Department> departments,List<Role> roles,DepartmentAccount departmentAccount,Account account){
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", account.getId());
		map.put("account", account.getAccount());
		map.put("password", account.getPassword());
		map.put("dep_name", departmentDao.get(departments,departmentAccount.getDepId()).getName());
		map.put("dep_id",departmentAccount.getDepId());
		//用户角色id集合
        List<Integer> roleIdSet = accountRoleDao.selectRoleIdSet(departmentAccount.getAccountId());
        String roleNames = "";
        String roleIds = "";
        for (Integer roleId : roleIdSet) {
            Role role = roleDao.get(roles, roleId);
            roleNames += "," + role.getName();
            roleIds += "," + role.getId();
        }
        if (StringUtils.isNotBlank(roleNames)) {
            roleNames = roleNames.substring(1);
            roleIds = roleIds.substring(1);
        }
        map.put("roleNames", roleNames);
        map.put("roleIds", roleIds);
        return map;
    }
    
    /**
     * 获取子部门用户
     * @param list
     * @param depId
     * @return
     */
    public static List<DepartmentAccount> getChildrenDepartAccounts(List<DepartmentAccount> list, List<Department> depts) {
        List<DepartmentAccount> depIdList = new ArrayList<DepartmentAccount>();
        for(Department dept : depts){
        	for (DepartmentAccount departAccount : list) {
                Integer departmentId = departAccount.getDepId();
                if(dept.getId()==departmentId){
                	depIdList.add(departAccount);
                }
            }
        }
        return depIdList;
    }

    /**
     * 账号管理
     *
     * @return
     */
    public ResultVO selectAccount(int userId, int pageNow, int pageSize) {
        ResultVO resultVO = new ResultVO(true);
        Map<String, Object> map = new HashMap<String, Object>();
        //获取用户部门id
        Integer depIdByAccountId = departmentAccountDao.getDepIdByAccountId(userId);
        if (depIdByAccountId == null) {
            resultVO.setOk(false);
            resultVO.setMsg("用户部门不存在");
            return resultVO;
        }
        //获取所有部门
        List<Department> departments = departmentDao.selectAll();
        //获取所有子级部门id集合
        Set<Integer> childrenDepIds = DepartmentServiceImpl.getChildrenDepIds(departments, depIdByAccountId);
        childrenDepIds.add(depIdByAccountId);
        //排除不查询的账号id
        Set<Integer> excludeAccountIdSet = new HashSet<>();
        excludeAccountIdSet.add(userId);


        PageResult<Account> pageInfo = accountDao.selectAccountManage(childrenDepIds, excludeAccountIdSet, pageNow, pageSize);
        List<Account> mapList = pageInfo.getResults();

        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        for (Account m : mapList) {
            Object idObj = m.get("id");
            //用户角色id集合
            List<Integer> roleIdSet = accountRoleDao.selectRoleIdSet(Integer.parseInt(idObj.toString()));
            String roleNames = "";
            for (Integer roleId : roleIdSet) {
                Role role = roleDao.get(roles, roleId);
                roleNames += "," + role.getName();
            }
            if (StringUtils.isNotBlank(roleNames)) {
                roleNames = roleNames.substring(1);
            }
            m.set("roleNames", roleNames);
        }
        map.put("total", pageInfo.getResultCount());
        map.put("rows", mapList);
        resultVO.setData(map);
        return resultVO;
    }

    /**
     * 根据账号id获取授权
     *
     * @param accountId
     * @return
     */
    public SimpleAuthorizationInfo getAccountRolePermission(int accountId) {
        SimpleAuthorizationInfo token = new SimpleAuthorizationInfo();
        //获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();
        //用户角色名字
        Set<String> roleNameSet = new HashSet<String>();
        //权限名字
        Set<String> perNameSet = new HashSet<String>();
        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        //获取用户角色id
        List<Integer> roleIdSet = accountRoleDao.selectRoleIdSet(accountId);
        if (roleIdSet != null && !roleIdSet.isEmpty()) {
            for (Integer roleId : roleIdSet) {
                Role role = roleDao.get(roles, roleId);
                if (role != null) {
                    roleNameSet.add(role.getKey());
                }
                //获取权限id集合
                List<Integer> permissionIdSet = rolePermissionDao.getPermissionIdSetByRoleId(roleId);
                if (permissionIdSet != null && !permissionIdSet.isEmpty()) {
                    for (Integer permissionId : permissionIdSet) {
                        //获取权限
                        Permission permission = permissionDao.get(permissionList, permissionId);
                        String key = permission.getKey();
                        if (StringUtils.isNotBlank(key)) {
                            perNameSet.add(key);
                        }
                    }
                }
            }
        }
        //设置角色权限
        token.setRoles(roleNameSet);
        token.setStringPermissions(perNameSet);
        return token;
    }

    /**
     * 添加账号
     *
     * @param createVO
     * @return
     */
    @Override
    public ResultVO saveUser(UserCreateVO createVO) {
        ResultVO resultVO = new ResultVO(true);
        Account byAccount = accountDao.getByAccount(createVO.getAccount());
        if (byAccount != null) {
            resultVO.setOk(false);
            resultVO.setMsg("账号已存在");
            return resultVO;
        }
        //保存
        Account account = new Account();
        account.setAccount(createVO.getAccount());
        account.setPassword(createVO.getPassword());
        account.setRegisterTime(new Date());
        accountDao.save(account);

        DepartmentAccount departmentAccount = new DepartmentAccount();
        departmentAccount.setAccountId(account.getId());
        departmentAccount.setDepId(createVO.getDep());
        departmentAccountDao.save(departmentAccount);
        resultVO.setMsg("注册成功");
        return resultVO;
    }
    
    /**
     * 修改账号
     *
     * @param createVO
     * @return
     */
    @Override
    public ResultVO editUser(UserEditVO editVO) {
        ResultVO resultVO = new ResultVO(true);
        Account account = accountDao.getByAccount(editVO.getAccount());
        //修改
        account.setPassword(editVO.getPassword());
        account.setRegisterTime(new Date());
        accountDao.update(account);

        DepartmentAccount departmentAccount = departmentAccountDao.getByAccountId(account.getId());
        departmentAccount.setDepId(editVO.getDep());
        departmentAccountDao.update(departmentAccount);
        resultVO.setMsg("修改成功");
        return resultVO;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @Override
    public ResultVO deleteUser(int[] userIds) {
        ResultVO resultVO = new ResultVO(true);
        for(int userId : userIds){
        	//删除账号
            accountDao.delete(userId);
            //删除账号与角色关联
            accountRoleDao.deleteByAccountId(userId);
            //删除账号与部门关联
            departmentAccountDao.deleteByAccountId(userId);
        }
        resultVO.setMsg("删除账号成功");
        return resultVO;
    }

    /**
     * 修改用户部门
     * @param userId
     * @param depId
     * @return
     */
    @Override
    public ResultVO updateUserDep(int userId, int depId) {
        ResultVO resultVO = new ResultVO(true);

        Integer depIdByAccountId = departmentAccountDao.getDepIdByAccountId(userId);
        if(depIdByAccountId!=null){
            departmentAccountDao.deleteByAccountId(userId);
        }
        DepartmentAccount departmentAccount = new DepartmentAccount();
        departmentAccount.setDepId(depId);
        departmentAccount.setAccountId(userId);
        departmentAccountDao.save(departmentAccount);
        resultVO.setMsg("修改用户部门成功");
        return resultVO;
    }

	@Override
	public ResultVO grantRoles(int userId, Integer[] roleArray) {

        ResultVO resultVO = new ResultVO(true);
        //获取所有角色
        List<Account> accounts = accountDao.selectAll();
        //获取角色
        Account account = accountDao.get(accounts, userId);
        if (account == null) {
            resultVO.setOk(false);
            resultVO.setMsg("用户不存在");
            return resultVO;
        }

        Set<Integer> roleIdSet = new HashSet<Integer>();
        for (Integer id : roleArray) {
        	roleIdSet.add(id);
        }

        //删除用户角色
        accountRoleDao.deleteByAccountId(userId);
        //授权
        if (!roleIdSet.isEmpty()) {
            for (Integer roleId : roleIdSet) {
                AccountRole accountRole = new AccountRole();
                accountRole.setAccountId(userId);
                accountRole.setRoleId(roleId);
                accountRoleDao.createAccountRole(accountRole);
            }
        }
        resultVO.setMsg("授权成功");
        return resultVO;
	}
}
