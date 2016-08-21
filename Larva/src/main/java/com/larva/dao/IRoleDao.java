package com.larva.dao;

import java.util.List;

import com.larva.model.Role;

/**
 * @author sxjun
 * @time 2015/8/27 17:21
 */
public interface IRoleDao {

    Role get(List<Role> roles, String id);

    List<Role> selectAll();

    int createRole(Role role);

    int deleteRole(String roleId);

    int updateRole(Role role);
}
