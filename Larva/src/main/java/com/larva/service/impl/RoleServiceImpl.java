package com.larva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IAccountRoleDao;
import com.larva.dao.IPermissionDao;
import com.larva.dao.IRoleDao;
import com.larva.dao.IRolePermissionDao;
import com.larva.model.AccountRole;
import com.larva.model.Permission;
import com.larva.model.Role;
import com.larva.model.RolePermission;
import com.larva.service.IRoleService;
import com.larva.utils.Constants;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.RoleCreateVO;
import com.larva.vo.RoleEditVO;
import com.larva.vo.TreeNode;

/**
 * @author sxjun
 * @time 2015/9/1 16:36
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
    @Resource
    private IRoleDao roleDao;
    @Resource
    private IAccountRoleDao accountRoleDao;
    @Resource
    private IRolePermissionDao rolePermissionDao;
    @Resource
    private IPermissionDao permissionDao;

    /**
     * 获取显示的角色
     *
     * @return
     */
    public Pager<Map<String,Object>> getShowRoles(PagerReqVO pagerReqVO,String userId) {
    	List<Map<String,Object>> resultRoles = new ArrayList<Map<String,Object>>();
    	int size = 0;
       /* Set<Integer> roleIdSet = accountRoleDao.selectRoleIdSet(userId);
        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        //我的角色
        List<Map<String,Object>> myRoles = new ArrayList<Map<String,Object>>();

        for (Integer id : roleIdSet) {
            Role role = roleDao.get(roles, id);
            if (role != null) {
            	Map<String,Object> m = new HashMap<String,Object>();
            	m.put("id", role.getId());
            	m.put("key", role.getKey());
            	m.put("name", role.getName());
                myRoles.add(m);
            }
        }*/
    	
    	//我的角色
        List<Map<String,Object>> myRoles = new ArrayList<Map<String,Object>>();
    	//获取所有角色
        List<Role> roles = roleDao.selectAll();
        for(Role role:roles){
        	Map<String,Object> m = new HashMap<String,Object>();
        	m.put("id", role.getId());
        	m.put("key", role.getKey());
        	m.put("name", role.getName());
            myRoles.add(m);
        }
        size = myRoles.size();
        if(pagerReqVO==null||pagerReqVO.getLimit()==0){
        	resultRoles = myRoles;
        }else{
        	for(int i = (pagerReqVO.getPageNo()-1)*pagerReqVO.getLimit();i<Math.min(pagerReqVO.getLimit()*pagerReqVO.getPageNo(),myRoles.size());i++){
        		resultRoles.add(myRoles.get(i));
            }
        }
		return new Pager(resultRoles,size);
    }
    
    /**
     * 获取 角色
     */
	@Override
	public ResultVO getRoleTree(String userId) {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        Pager<Map<String,Object>> pager = this.getShowRoles(null,userId);
        List<Map<String,Object>> list = pager.getRows();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("角色树");
        superTree.setOpen(true);
        superTree.setNocheck(true);
        trees.add(superTree);
        for(Map<String,Object> role:list){
            TreeNode tree = new TreeNode();
            tree.setId(role.get("id").toString());
            tree.setName(role.get("name").toString());
            tree.setpId(Constants.SUPER_TREE_NODE);
            trees.add(tree);
        }
        ResultVO resultVO = new ResultVO(true);
        resultVO.setData(trees);
		return resultVO;
	}

    private static boolean isKeyExist(List<Role> roles,String key){
        for(Role role:roles){
            if(role.getKey().equals(key)){
                return true;
            }
        }
        return false;
    }

    /**
     * 创建角色
     *
     * @param createVO
     * @return
     */
    public ResultVO createRole(RoleCreateVO createVO, String userId) {
        ResultVO resultVO = new ResultVO(true);
        List<Role> roleList = roleDao.selectAll();
        if (isKeyExist(roleList,createVO.getKey())) {
            resultVO.setOk(false);
            resultVO.setMsg("角色键值已存在");
            return resultVO;
        }
        Role role = new Role();
        role.setName(createVO.getName());
        role.setKey(createVO.getKey());
        roleDao.createRole(role);
        //添加账号角色关联
        AccountRole accountRole = new AccountRole();
        accountRole.setAccountId(userId);
        accountRole.setRoleId(role.getId());
        accountRoleDao.createAccountRole(accountRole);
        resultVO.setMsg("创建角色成功");
        return resultVO;
    }

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    public ResultVO deleteRole(String[] roleIds) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        for(String roleId:roleIds){
        	Role role = roleDao.get(roles, roleId);
	        if (role == null) {
	            resultVO.setOk(false);
	            resultVO.setMsg("角色不存在");
	            return resultVO;
	        }
        
	        roleDao.deleteRole(roleId);
	        rolePermissionDao.deleteByRoleId(roleId);
	        accountRoleDao.deleteByRoleId(roleId);
	        resultVO.setMsg("删除角色成功");
        }
        return resultVO;
    }

    /**
     * 编辑角色
     *
     * @param editVO
     * @return
     */
    public ResultVO editRole(RoleEditVO editVO) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        Role role = roleDao.get(roles, editVO.getId());
        if (role == null) {
            resultVO.setOk(false);
            resultVO.setMsg("角色不存在");
            return resultVO;
        }
        if (!role.getKey().equals(editVO.getKey()) && isKeyExist(roles,editVO.getKey())) {
            resultVO.setOk(false);
            resultVO.setMsg("角色键值已存在");
            return resultVO;
        }
        Role update = new Role();
        update.setId(editVO.getId());
        update.setKey(editVO.getKey());
        update.setName(editVO.getName());
        int num = roleDao.updateRole(update);
        resultVO.setMsg("编辑角色成功");
        return resultVO;
    }

    /**
     * 角色授权
     *
     * @param roleId
     * @param perIdArray
     * @return
     */
    public ResultVO grantPermissions(String roleId, String[] perIdArray) {

        ResultVO resultVO = new ResultVO(true);
        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        //获取角色
        Role role = roleDao.get(roles, roleId);
        if (role == null) {
            resultVO.setOk(false);
            resultVO.setMsg("角色不存在");
            return resultVO;
        }

        //获取所有权限
        List<Permission> permissions = permissionDao.selectAll();

        Set<String> perIdSet = new HashSet<String>();

        for (String id : perIdArray) {
            perIdSet.add(id);
   /*         List<Integer> childrenPermissionIds = PermissionServiceImpl.getChildrenPermissionIds(id, permissions);
            perIdSet.addAll(childrenPermissionIds);*/
        }

        //删除角色权限
        rolePermissionDao.deleteByRoleId(roleId);
        //授权
        if (!perIdSet.isEmpty()) {
            for (String perId : perIdSet) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(perId);
                rolePermission.setRoleId(roleId);
                rolePermissionDao.addRolePermission(rolePermission);

            }
        }
        resultVO.setMsg("授权成功");
        return resultVO;
    }
}
