package com.larva.dao;

import java.util.List;

import com.larva.model.RolePermission;

/**
 * @author sxjun
 * @time 2015/8/27 17:25
 */
public interface IRolePermissionDao {

	List<Integer> getPermissionIdSetByRoleId(int roleId);

    void deleteByPerId(int perId);

    void deleteByRoleId(int roleId);

    int addRolePermission(RolePermission rolePermission);
}
