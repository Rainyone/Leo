package com.larva.dao;

import java.util.List;

import com.larva.model.MenuPermission;

/**
 * Created by sxjun on 15-8-27.
 */
public interface IMenuPermissionDao {

	List<String> selectPermissionIdSet(String menuId);

    void deleteByPerId(String perId);

    void deleteByMenuId(String menuId);

    int addMenuPermission(MenuPermission menuPermission);

}
