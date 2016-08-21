package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IMenuDao;
import com.larva.model.Menu;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * Created by sxjun on 15-8-27.
 */
@Repository("menuDao")
public class MenuDaoImpl extends MiniDao implements IMenuDao {
    
    /**
     * 获取所有菜单
     *
     * @return
     */
    public List<Menu>  selectPage(int limit,int pageNo) {
    	return this.paginate("select * from menu order by `order` asc", pageNo, limit, Menu.class);
    }

    /**
     * 获取所有菜单
     * @return
     */
    public List<Menu> selectAll() {
        return this.findList("select * from menu order by `order` asc", Menu.class);
    }

    /**
     * 获取菜单
     * @param menus
     * @param id
     * @return
     */
    public Menu get(List<Menu> menus, String id) {
        for(Menu menu:menus){
            if(id.equals(menu.getId())){
                return menu;
            }
        }
        return null;
    }

    /**
     * 添加菜单
     * @param menu
     * @return
     */
    public int createMenu(Menu menu) {
        return this.insert(menu);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    public int deleteMenu(String id) {
        return this.deleteById(Menu.class,id);
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    public int updateMenu(Menu menu) {
        return this.update(menu);
    }
}
