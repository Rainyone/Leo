package com.larva.dao;

import java.util.List;

import com.larva.model.Permission;

/**
 * @author sxjun
 * @time 2015/8/27 17:30
 */
public interface IPermissionDao {

    Permission get(List<Permission> permissionList, String id);

    List<Permission> selectAll();

    int createPermission(Permission permission);

    int deletePermission(String perId);

    int updatePermission(Permission permission);

	List<Permission> selectPage(int limit, int pageNo);
}
