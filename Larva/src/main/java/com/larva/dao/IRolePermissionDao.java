package com.larva.dao;

import java.util.List;

import com.larva.model.RolePermission;

/**
 * @author sxjun
 * @time 2015/8/27 17:25
 */
public interface IRolePermissionDao {

	List<String> getPermissionIdSetByRoleId(String roleId);

    void deleteByPerId(String perId);

    void deleteByRoleId(String roleId);

    int addRolePermission(RolePermission rolePermission);
}
