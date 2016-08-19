package com.larva.dao;

import java.util.List;

import com.larva.model.MenuPermission;

/**
 * Created by sxjun on 15-8-27.
 */
public interface IMenuPermissionDao {

	List<Integer> selectPermissionIdSet(int menuId);

    void deleteByPerId(int perId);

    void deleteByMenuId(int menuId);

    int addMenuPermission(MenuPermission menuPermission);

}
