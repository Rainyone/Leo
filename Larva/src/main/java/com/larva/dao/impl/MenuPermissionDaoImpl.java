package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IMenuPermissionDao;
import com.larva.model.MenuPermission;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * Created by sxjun on 15-8-27.
 */
@Repository("menuPermissionDao")
public class MenuPermissionDaoImpl extends MiniDao implements IMenuPermissionDao {

    /**
     * 根据菜单id获取权限id集合
     *
     * @param menuId
     * @return
     */
    public List<String> selectPermissionIdSet(String menuId) {
        return this.findList("select permission_id from menu_permission where menu_id =?", String.class, menuId);
    }

    /**
     * 根据权限id删除menu-per
     *
     * @param perId
     */
    public void deleteByPerId(String perId) {
    	this.execute("delete from menu_permission where permission_id=?", perId);
    }

    /**
     * 根据菜单id删除menu-per
     *
     * @param menuId
     */
    public void deleteByMenuId(String menuId) {
    	this.execute("delete from menu_permission where menu_id=?", menuId);
    }

    /**
     * 添加菜单授权
     * @param menuPermission
     * @return
     */
    public int addMenuPermission(MenuPermission menuPermission) {
        return this.insert(menuPermission);
    }
}
