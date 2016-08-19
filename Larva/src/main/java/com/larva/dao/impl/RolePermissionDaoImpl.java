package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IRolePermissionDao;
import com.larva.model.RolePermission;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 17:26
 */
@Repository("rolePermissionDao")
public class RolePermissionDaoImpl extends MiniDao implements IRolePermissionDao {

    /**
     * 根据角色id获取权限id集合
     * @param roleId
     * @return
     */
    public List<Integer> getPermissionIdSetByRoleId(int roleId) {
        return this.findList("select  permission_id from role_permission where role_id=?", int.class, roleId);
    }

    /**
     * 根据权限删除role-per
     * @param perId
     */
    public void deleteByPerId(int perId) {
    	this.execute("delete from role_permission where 1=1 and permission_id=?",perId);
    }

    /**
     * 根据角色删除role-per
     * @param roleId
     */
    public void deleteByRoleId(int roleId) {
    	this.execute("delete from role_permission where 1=1 and role_id=?",roleId);
    }

    /**
     * 添加角色权限
     * @param rolePermission
     * @return
     */
    public int addRolePermission(RolePermission rolePermission) {
        return this.insert(rolePermission);
    }
}
