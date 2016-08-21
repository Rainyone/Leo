package com.larva.dao;

import java.util.List;

import com.larva.model.Menu;

/**
 * Created by sxjun on 15-8-27.
 */
public interface IMenuDao {
	
	List<Menu> selectPage(int limit,int ffset);

    List<Menu> selectAll();

    Menu get(List<Menu> menus, String id);

    int createMenu(Menu menu);

    int deleteMenu(String id);

    int updateMenu(Menu menu);

}
