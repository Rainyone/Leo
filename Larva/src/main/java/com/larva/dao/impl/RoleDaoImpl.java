package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IRoleDao;
import com.larva.model.Role;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * @author sxjun
 * @time 2015/8/27 17:21
 */
@Repository("roleDao")
public class RoleDaoImpl extends MiniDao implements IRoleDao {
    /**
     * 根据角色id获取角色
     * @param id
     * @return
     */
    public Role get(List<Role> roles, int id) {
        for(Role role:roles){
            if(role.getId().intValue()==id){
                return role;
            }
        }
        return null;
    }

    /**
     * 获取所有角色
     * @return
     */
    public List<Role> selectAll() {
        return this.findList("select * from role", Role.class);
    }

    /**
     * 创建角色
     * @param role
     * @return
     */
    public int createRole(Role role) {
        return this.insert(role);
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    public int deleteRole(int roleId) {
        return this.deleteById(Role.class,roleId);
    }

    /**
     * 编辑角色
     * @param role
     * @return
     */
    public int updateRole(Role role) {
        return this.update(role);
    }
}
