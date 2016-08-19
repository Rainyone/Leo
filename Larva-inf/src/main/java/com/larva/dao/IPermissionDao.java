package com.larva.dao;

import java.util.List;

import com.larva.model.Permission;

/**
 * @author sxjun
 * @time 2015/8/27 17:30
 */
public interface IPermissionDao {

    Permission get(List<Permission> permissionList, int id);

    List<Permission> selectAll();

    int createPermission(Permission permission);

    int deletePermission(int perId);

    int updatePermission(Permission permission);

	List<Permission> selectPage(int limit, int pageNo);
}
