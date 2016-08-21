package com.larva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.larva.dao.IMenuDao;
import com.larva.dao.IMenuPermissionDao;
import com.larva.dao.IPermissionDao;
import com.larva.model.Menu;
import com.larva.model.MenuPermission;
import com.larva.model.Permission;
import com.larva.service.IMenuService;
import com.larva.utils.Constants;
import com.larva.utils.StrKit;
import com.larva.vo.MenuCreateVO;
import com.larva.vo.MenuEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * Created by sxjun on 15-8-27.
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService {
    @Resource
    private IMenuDao menuDao;
    @Resource
    private IMenuPermissionDao menuPermissionDao;
    @Resource
    private IPermissionDao permissionDao;
    
    private Map<String,Object> getMenuMap(List<Menu> menus,Menu menu){
    	Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", menu.getId());
		m.put("name", menu.getName());
		m.put("order", menu.getOrder());
		m.put("icon", menu.getIcon());
		m.put("parentId", menu.getParentId());
		m.put("url", menu.getUrl());
		if(menu.getParentId()!=null&&!Constants.SUPER_TREE_NODE.equals(menu.getParentId().toString()))
		if(menuDao.get(menus,menu.getParentId())==null){
			System.out.println();
		}
		if(menu.getParentId()!=null&&!Constants.SUPER_TREE_NODE.equals(menu.getParentId()))
		{
			Menu me = menuDao.get(menus,menu.getParentId());
			m.put("parentName", me!=null?me.getName():"");
		}
		return m;
    }
    
    /**
     * 获取子部门
     * @param list
     * @param depId
     * @return
     */
    public static List<Menu> getChildrenMenus(List<Menu> list, String _menuId) {
        List<Menu> menuIdList = new ArrayList<Menu>();
        for (Menu menu : list) {
        	String menuId = menu.getParentId();
            if ((menuId == null && _menuId == null) || (menuId != null && _menuId != null && menuId.equals( _menuId))) {
            	menuIdList.add(menu);
            	menuIdList.addAll(getChildrenMenus(list, menu.getId()));
            }
        }
        return menuIdList;
    }
    
    /**
     * 获取菜单列表
     */
    public Pager<Map<String, Object>> getPageMenus(PagerReqVO pagerReqVO, TreeNode tree){
    	List<Map<String,Object>> resultMenus = new ArrayList<Map<String,Object>>();
    	List<Menu> menus = menuDao.selectAll();
    	int size = 0;
        if(pagerReqVO==null||pagerReqVO.getLimit()==0){
        	for(Menu menu : menus){
        		resultMenus.add(getMenuMap(menus,menu));
        	}
        	size = menus.size();
        }else if(tree!=null && StrKit.notBlank(tree.getId())&&!Constants.SUPER_TREE_NODE.equals(tree.getId())){
            //获取所有菜单
        	String child = tree.getId();
            //获取当前菜单
            Menu selectdepart = menuDao.get(menus,child);
            resultMenus.add(getMenuMap(menus,selectdepart));
            //获取当前部门的子菜单
            List<Menu> childrens = getChildrenMenus(menus,child);

            size = childrens.size();
            if(childrens!=null&&size>0){
                if(childrens.size()<pagerReqVO.getLimit()){
                	for(Menu menu : childrens)
                		resultMenus.add(getMenuMap(menus,menu));
                }else{
                    for(int i = (pagerReqVO.getPageNo()-1)*pagerReqVO.getLimit();i<Math.min(pagerReqVO.getLimit()*pagerReqVO.getPageNo(),size);i++){
                    	resultMenus.add(getMenuMap(menus,childrens.get(i)));
                    }
                }
            }
        }else{
        	size = menus.size();
        	List<Menu> _menus = menuDao.selectPage(pagerReqVO.getLimit(),pagerReqVO.getPageNo());
        	for(Menu menu : _menus){
        		resultMenus.add(getMenuMap(menus,menu));
        	}

        }
		return new Pager(resultMenus,size);
    }
    
    /**
     * 获取菜单树
     */
    public ResultVO getMenuTree(){
        List<TreeNode> trees = new ArrayList<TreeNode>();
        Pager<Map<String,Object>> pager = this.getPageMenus(null,null);
        List<Map<String,Object>> list = pager.getRows();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("所有菜单");
        superTree.setOpen(true);
        trees.add(superTree);
        for(Map<String,Object> menu:list){
            TreeNode tree = new TreeNode();
            tree.setId(menu.get("id").toString());
            tree.setName(menu.get("name").toString());
            tree.setOpen(true);
            if(menu.get("parentId")!=null)
            	tree.setpId(menu.get("parentId").toString());
            else{
            	tree.setpId(Constants.SUPER_TREE_NODE);
            }
            trees.add(tree);
        }
        ResultVO resultVO = new ResultVO(true);
        resultVO.setData(trees);
		return resultVO;
    }

    /**
     * 获取显示菜单
     *
     * @return
     */
    public List<Map<String, Object>> selectShowMenus(String rootId) {

        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //授权的菜单集合
        List<Menu> menuPermittedList = new ArrayList<Menu>();
        //获取所有菜单
        List<Menu> menuList = menuDao.selectAll();
        //获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();

        Subject subject = SecurityUtils.getSubject();
        for (Menu menu : menuList) {
        	List<String> permissionIdSet = menuPermissionDao.selectPermissionIdSet(menu.getId());
            //权限key集合
            List<String> perKeyList = new ArrayList<String>();
            if (permissionIdSet != null && !permissionIdSet.isEmpty()) {
                for (String perId : permissionIdSet) {
                    Permission permission = permissionDao.get(permissionList, perId);
                    perKeyList.add(permission.getKey());
                }
            }
            //判断权限
            String[] permissions = perKeyList.toArray(new String[]{});
            boolean permittedAll = subject.isPermittedAll(permissions);
            if (permittedAll) {
                menuPermittedList.add(menu);
            }
        }
        List<String> childrenIdList = null;
        if (rootId != null) {
            childrenIdList = getChildrenMenuIds(menuList, rootId);
            childrenIdList.add(rootId);
        }

        List<Menu> rootMenus = getRootMenus(menuPermittedList);
        for (Menu menu : rootMenus) {
            if (childrenIdList == null || !childrenIdList.contains(menu.getId())) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", menu.getId());
                map.put("text", menu.getName());
                map.put("order", menu.getOrder());
                //菜单额外属性
                Map<String, Object> attrMap = new HashMap<String, Object>();
                attrMap.put("url", menu.getUrl());
                map.put("attributes", attrMap);
                map.put("url", menu.getUrl());
                map.put("icon", menu.getIcon());
                List<Map<String, Object>> childrenMenus = getChildrenMenus(menuPermittedList, menu.getId(), childrenIdList);
                map.put("children", childrenMenus);
                maps.add(map);
            }
        }
        return maps;
    }

    /**
     * 创建菜单
     *
     * @param createVO
     * @return
     */
    public ResultVO createMenu(MenuCreateVO createVO) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有菜单
        List<Menu> menuList = menuDao.selectAll();
        String parentId = createVO.getParentId();
        //父级菜单
        /*if (createVO.getParentId() != null) {
            Menu parentMenu = menuDao.get(menuList, createVO.getParentId());
            if (parentMenu == null) {
                resultVO.setOk(false);
                resultVO.setMsg("父级菜单不存在");
                return resultVO;
            }
        }*/
        if(Constants.SUPER_TREE_NODE.equals(parentId))
        	parentId=null;
        //菜单名字不能重复
        for(Menu menu:menuList){
            if(menu.getName().equals(createVO.getName())){
                resultVO.setOk(false);
                resultVO.setMsg("菜单名字已存在");
                return resultVO;
            }
        }

        //添加菜单
        Menu menu = new Menu();
        menu.setOrder(createVO.getOrder());
        menu.setName(createVO.getName());
        menu.setParentId(parentId);
        menu.setUrl(createVO.getUrl());
        menu.setIcon(createVO.getIcon());
        menuDao.createMenu(menu);
        resultVO.setMsg("创建菜单成功");
        return resultVO;
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    public ResultVO deleteMenu(String[] menuIds) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有菜单
        List<Menu> menus = menuDao.selectAll();
        for(String menuId : menuIds){
	        //获取菜单
	        Menu menu = menuDao.get(menus, menuId);
	        if (menu == null) {
	            resultVO.setOk(false);
	            resultVO.setMsg("菜单不存在");
	            return resultVO;
	        }
        }
        
        for(String menuId : menuIds){
	        //获取子级菜单
	        List<String> childrenMenuIds = getChildrenMenuIds(menus, menuId);
	        //删除菜单
	        menuDao.deleteMenu(menuId);
	        menuPermissionDao.deleteByMenuId(menuId);
	        for (String id : childrenMenuIds) {
	            menuDao.deleteMenu(id);
	            menuPermissionDao.deleteByMenuId(id);
	        }
        }
        resultVO.setMsg("删除菜单成功");
        return resultVO;
    }

    /**
     * 编辑菜单
     *
     * @param editVO
     * @return
     */
    public ResultVO editMenu(MenuEditVO editVO) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有菜单
        List<Menu> menus = menuDao.selectAll();
        //获取菜单
        Menu menu = menuDao.get(menus, editVO.getId());
        if (menu == null) {
            resultVO.setOk(false);
            resultVO.setMsg("菜单不存在");
            return resultVO;
        }
        //父级菜单
        if (editVO.getParentId() != null) {
            Menu parentMenu = menuDao.get(menus, editVO.getParentId());
           /* if (parentMenu == null) {
                resultVO.setOk(false);
                resultVO.setMsg("父级菜单不存在");
                return resultVO;
            }*/
            List<String> childrenMenuIds = getChildrenMenuIds(menus, editVO.getId());
            childrenMenuIds.add(editVO.getId());
            if (childrenMenuIds.contains(editVO.getParentId())) {
                resultVO.setOk(false);
                resultVO.setMsg("所在菜单的上级不能为自己所在菜单或者下级菜单");
                return resultVO;
            }
        }
        //菜单名字不能重复
        for(Menu m:menus){
            if(m.getName().equals(editVO.getName())&&!menu.getId().equals(editVO.getId())){
                resultVO.setOk(false);
                resultVO.setMsg("菜单名字已存在");
                return resultVO;
            }
        }
        //更新菜单
        Menu update = new Menu();
        update.setUrl(editVO.getUrl());
        update.setParentId(editVO.getParentId());
        update.setName(editVO.getName());
        update.setOrder(editVO.getOrder());
        update.setId(editVO.getId());
        update.setIcon(editVO.getIcon());
        menuDao.updateMenu(update);
        resultVO.setMsg("编辑菜单成功");
        return resultVO;
    }
    
    
    public String checkOwnPermissions(List<Permission> permissions,String[] perIdArray){
    	//获取我拥有的权限
        List<Permission> myPermissionList = new ArrayList<Permission>();
        Subject subject = SecurityUtils.getSubject();
        for (Permission permission : permissions) {
            String key = permission.getKey();
            boolean permitted = subject.isPermitted(key);
            if (permitted) {
                myPermissionList.add(permission);
            }
        }
        
    	boolean isAlowed = true;
        String perInfo = "";
        for(String perId : perIdArray){
        	boolean subAlowed = false;
        	for(Permission permission : myPermissionList){
        		if(permission.getId().equals(perId)){
        			subAlowed = true;
        			break;
        		}
        	}
        	if(!subAlowed){
        		Permission perm = permissionDao.get(permissions, perId);
        		perInfo+=perm.getName()+",";
        		isAlowed = false;
        	}
        }
        
        if(!isAlowed&&StrKit.notBlank(perInfo)){
             return perInfo.substring(0,perInfo.length()-1);
        }
        return null;
    }

    /**
     * 菜单授权
     *
     * @param menuId
     * @param perIdArray
     * @return
     */
    public ResultVO grantPermissions(String menuId, String[] perIdArray) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有菜单
        List<Menu> menus = menuDao.selectAll();
        //获取菜单
        Menu menu = menuDao.get(menus, menuId);
        if (menu == null) {
            resultVO.setOk(false);
            resultVO.setMsg("菜单不存在");
            return resultVO;
        }
        
        if(perIdArray==null||perIdArray.length==0){
        	 resultVO.setOk(false);
             resultVO.setMsg("未选择授权页面");
             return resultVO;
        }

        //获取所有权限
        List<Permission> permissions = permissionDao.selectAll();
        
        String perInfo = checkOwnPermissions(permissions,perIdArray);
        if(StrKit.notBlank(perInfo)){
        	 resultVO.setOk(false);
             resultVO.setMsg("您没有分配["+perInfo.substring(0,perInfo.length()-1)+"]的权限");
             return resultVO;
        }

        Set<String> perIdSet = new HashSet<String>();

        for (String id : perIdArray) {
            perIdSet.add(id);
        /*    List<Integer> childrenPermissionIds = PermissionServiceImpl.getChildrenPermissionIds(id, permissions);
            perIdSet.addAll(childrenPermissionIds);*/
        }

        //删除菜单权限
        menuPermissionDao.deleteByMenuId(menuId);
        //授权
        if (!perIdSet.isEmpty()) {
            for (String perId : perIdSet) {
                MenuPermission menuPermission = new MenuPermission();
                menuPermission.setMenuId(menuId);
                menuPermission.setPermissionId(perId);
                menuPermissionDao.addMenuPermission(menuPermission);

            }
        }
        resultVO.setMsg("授权成功");
        return resultVO;
    }

    /**
     * 获取子级菜单
     *
     * @param menus
     * @param parentId
     * @return
     */
    private List<Map<String, Object>> getChildrenMenus(List<Menu> menus, String parentId, List<String> excludeChildrenIdList) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Menu menu : menus) {
            if ((parentId == null && menu.getParentId() == null) || (parentId != null && menu.getParentId() != null && menu.getParentId().equals(parentId))) {
                if (excludeChildrenIdList == null || !excludeChildrenIdList.contains(menu.getId())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", menu.getId());
                    map.put("text", menu.getName());
                    //菜单额外属性
                    Map<String, Object> attrMap = new HashMap<String, Object>();
                    attrMap.put("url", menu.getUrl());
                    map.put("attributes", attrMap);
                    map.put("url", menu.getUrl());
                    map.put("order", menu.getOrder());
                    map.put("icon", menu.getIcon());
                    List<Map<String, Object>> childrenMenus = getChildrenMenus(menus, menu.getId(), excludeChildrenIdList);
                    map.put("children", childrenMenus);
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }

    /**
     * 获取根菜单
     *
     * @param menus
     * @return
     */
    private List<Menu> getRootMenus(List<Menu> menus) {
        List<Menu> rootMenus = new ArrayList<Menu>();
        for (Menu menu : menus) {
            if (menu.getParentId() == null) {
                rootMenus.add(menu);
                continue;
            }
            Menu parentMenu = menuDao.get(menus, menu.getParentId());
            if (parentMenu == null) {
                rootMenus.add(menu);
            }
        }
        return rootMenus;
    }

    /**
     * 获取所有子级菜单id集合
     *
     * @param menuList
     * @param parentId
     * @return
     */
    private List<String> getChildrenMenuIds(List<Menu> menuList, String parentId) {
        List<String> list = new ArrayList<String>();
        for (Menu menu : menuList) {
            if ((parentId == null && menu.getParentId() == null) || (parentId != null && menu.getParentId() != null && parentId.equals( menu.getParentId()))) {
                list.add(menu.getId());
                list.addAll(getChildrenMenuIds(menuList, menu.getId()));
            }
        }
        return list;
    }
}
