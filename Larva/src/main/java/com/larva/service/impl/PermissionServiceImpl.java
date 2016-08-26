package com.larva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.larva.dao.IRoleDao;
import com.larva.dao.IRolePermissionDao;
import com.larva.model.Menu;
import com.larva.model.Permission;
import com.larva.model.Role;
import com.larva.service.IPermissionService;
import com.larva.utils.Constants;
import com.larva.utils.StrKit;
import com.larva.utils.UUIDUtil;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.PermissionCreateVO;
import com.larva.vo.PermissionEditVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * @author sxjun
 * @time 2015/8/28 9:45
 */
@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {
    @Resource
    private IPermissionDao permissionDao;
    @Resource
    private IRolePermissionDao rolePermissionDao;
    @Resource
    private IMenuPermissionDao menuPermissionDao;
    @Resource
    private IMenuDao menuDao;
    @Resource
    private IRoleDao roleDao;

    /**
     * 删除权限
     *
     * @param perId
     * @return
     */
    public ResultVO delPermission(String[] permIds) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();
        Subject subject = SecurityUtils.getSubject();
        for(String perId : permIds){
	        Permission permission = permissionDao.get(permissionList, perId);
	        if (permission == null) {
	            resultVO.setOk(false);
	            resultVO.setMsg("权限不存在");
	            return resultVO;
	        }
	        
	        //查看是否有权限
	        if (!subject.isPermitted(permission.getKey())) {
	            resultVO.setOk(false);
	            resultVO.setMsg("您没有操作权限");
	            return resultVO;
	        }
        
	        //判断是否是根级权限
	        //获取我拥有的权限
	        List<Permission> myPermissionList = new ArrayList<Permission>();
	        for (Permission p : permissionList) {
	            String key = p.getKey();
	            boolean permitted = subject.isPermitted(key);
	            if (permitted) {
	                myPermissionList.add(p);
	            }
	        }
	        List<Permission> rootPermissions = getRootPermissions(myPermissionList);
	        
		        for (Permission p : rootPermissions) {
		            if (p.getId().equals( perId)) {
		                resultVO.setOk(false);
		                resultVO.setMsg("根级权限不能删除");
		                return resultVO;
		            }
		        }
        }
	    for(String perId : permIds){
	        //获取子级权限id集合
	        List<String> childrenPermissionIds = getChildrenPermissionIds(perId, permissionList);
	
	        //删除权限
	        int num = permissionDao.deletePermission(perId);
	
	        for (String id : childrenPermissionIds) {
	            num = permissionDao.deletePermission(id);
	            if (num == 1) {
	                rolePermissionDao.deleteByPerId(id);
	                menuPermissionDao.deleteByPerId(id);
	
	            }
	        }
	        rolePermissionDao.deleteByPerId(perId);
	        menuPermissionDao.deleteByPerId(perId);
        }
        resultVO.setMsg("删除权限成功");
        return resultVO;
    }

    /**
     * 获取子级权限id集合
     *
     * @param parentId
     * @param permissions
     * @return
     */
    public static List<String> getChildrenPermissionIds(String parentId, List<Permission> permissions) {
        List<String> list = new ArrayList<String>();
        for (Permission permission : permissions) {
            if ((parentId == null && permission.getParentId() == null) || (parentId != null && permission.getParentId() != null && parentId.equals(permission.getParentId()))) {
                list.add(permission.getId());
                list.addAll(getChildrenPermissionIds(permission.getId(), permissions));
            }
        }
        return list;
    }

    private Map<String,Object> getPermsMap(List<Permission> perms,Permission perm){
    	Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", perm.getId());
		m.put("name", perm.getName());
		m.put("order", perm.getOrder());
		m.put("key", perm.getKey());
		m.put("parentId", perm.getParentId());
		if(perm.getParentId()!=null&&!Constants.SUPER_TREE_NODE.equals(perm.getParentId().toString()))
		{	Permission p =  permissionDao.get(perms,perm.getParentId());
			m.put("parentName",p!=null?p.getName():"");}
		return m;
    }
    
    /**
     * 获取子权限
     * @param list
     * @param depId
     * @return
     */
    public static List<Permission> getChildrenPermissions(List<Permission> list, String permId) {
        List<Permission> permIdList = new ArrayList<Permission>();
        for (Permission perm : list) {
        	String permissionId = perm.getParentId();
            if ((permissionId == null && permId == null) || (permissionId != null && permId != null && permissionId.equals(permId))) {
            	permIdList.add(perm);
            	permIdList.addAll(getChildrenPermissions(list, perm.getId()));
            }
        }
        return permIdList;
    }
    
    /**
     * 获取权限 树
     */
	@Override
	public ResultVO getPermissionTree() {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        Pager<Map<String,Object>> pager = this.getShowPermissions(null,null);
        List<Map<String,Object>> list = pager.getRows();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("所有权限");
        superTree.setOpen(true);
        trees.add(superTree);
        for(Map<String,Object> perm:list){
            TreeNode tree = new TreeNode();
            tree.setId(perm.get("id").toString());
            tree.setName(perm.get("name").toString());
            tree.setOpen(true);
            if(perm.get("parentId")!=null)
            	tree.setpId(perm.get("parentId").toString());
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
     * 获取显示权限
     *
     * @return
     */
    public Pager<Map<String,Object>> getShowPermissions(PagerReqVO pagerReqVO,TreeNode tree) {
    	 List<Map<String,Object>> resultPerms = new ArrayList<Map<String,Object>>();
         List<Permission> perms = permissionDao.selectAll();
         int size = 0;
         if(pagerReqVO==null||pagerReqVO.getLimit()==0){
         	for(Permission perm : perms){
         		resultPerms.add(getPermsMap(perms,perm));
         	}
         	size = perms.size();
         }else if(tree!=null && StrKit.notBlank(tree.getId())&&!Constants.SUPER_TREE_NODE.equals(tree.getId())){
             //获取所有部门
        	 String child = tree.getId();
             //获取当前部门
             Permission selectdepart = permissionDao.get(perms,child);
             resultPerms.add(getPermsMap(perms,selectdepart));
             //获取当前部门的子部门
             List<Permission> childrens = getChildrenPermissions(perms,child);
             
             size = childrens.size();
             if(childrens!=null&&size>0){
                 if(childrens.size()<pagerReqVO.getLimit()){
                 	for(Permission perm : childrens)
                 		resultPerms.add(getPermsMap(perms,perm));
                 }else{
                     for(int i = (pagerReqVO.getPageNo()-1)*pagerReqVO.getLimit();i<Math.min(pagerReqVO.getLimit()*pagerReqVO.getPageNo(),size);i++){
                    	 resultPerms.add(getPermsMap(perms,childrens.get(i)));
                     }
                 }
             }
         }else{
         	size = perms.size();
         	List<Permission> _perms = permissionDao.selectPage(pagerReqVO.getLimit(),pagerReqVO.getPageNo());
         	for(Permission perm : _perms){
         		resultPerms.add(getPermsMap(perms,perm));
         	}

         }
 		return new Pager(resultPerms,size);
    }
    
    /**
     * 过滤子节点
     * @param permissionList
     * @return
     */
    public List<Permission> filterChildNode(List<Permission> permissionList){
    	List<Permission> filterPermissionList = new ArrayList<Permission>();
    	for(Permission perm : permissionList){
    		boolean isNotLeaf = notLeaf(permissionList,perm.getId());
    		if(isNotLeaf)
    			filterPermissionList.add(perm);
    	}
		return filterPermissionList;
    }
    
    private boolean notLeaf(List<Permission> permissionList, String perId){
    	boolean notLeaf = false;
    	for(Permission perm : permissionList){
    		String parentId = perm.getParentId();
    		if(parentId != null && parentId.equals(perId)){
    			notLeaf=true;
    			break;
    		}
    			
    	}
    	return notLeaf;
    }

    /**
     * 获取菜单显示权限
     *
     * @param menuId
     * @return
     */
    public ResultVO getMenuShowPermissions(String menuId,boolean chkDisabled) {

        ResultVO resultVO = new ResultVO(true);
        
        List<TreeNode> mapList = new ArrayList<TreeNode>();
        
        //获取所有菜单
        List<Menu> menus = menuDao.selectAll();
        Menu menu = menuDao.get(menus, menuId);
        if (menu == null) {
            resultVO.setOk(false);
            resultVO.setMsg("菜单不存在");
            return resultVO;
        }
        
        //获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();
        
        List<Permission> filterPermissionList = filterChildNode(permissionList);
        
        //获取菜单拥有的权限id
        List<String> permissionIdSet = menuPermissionDao.selectPermissionIdSet(menuId);
        
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("权限列表");
        superTree.setOpen(true);
        superTree.setNocheck(true);
        mapList.add(superTree);
        
        for (Permission permission : filterPermissionList) {
        	TreeNode treeNode = new TreeNode();
        	treeNode.setId(permission.getId());
        	treeNode.setChkDisabled(chkDisabled);
        	treeNode.setName(permission.getName());
        	treeNode.setChecked(permissionIdSet.contains(permission.getId()));
        	treeNode.setOpen(true);
        	if(permission.getParentId()!=null)
        		treeNode.setpId(permission.getParentId());
            else{
            	treeNode.setpId(Constants.SUPER_TREE_NODE);
            }
            mapList.add(treeNode);
        }
        resultVO.setData(mapList);

        return resultVO;
    }

    /**
     * 获取角色显示权限
     *
     * @param roleId
     * @return
     */
    public ResultVO getRoleShowPermissions(String roleId,boolean chkDisabled) {
        ResultVO resultVO = new ResultVO(true);

        List<TreeNode> mapList = new ArrayList<TreeNode>();

        //获取所有角色
        List<Role> roles = roleDao.selectAll();
        Role role = roleDao.get(roles, roleId);
        if (role == null) {
            resultVO.setOk(false);
            resultVO.setMsg("角色不存在");
            return resultVO;
        }
        //获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();
        
        //获取角色拥有的权限id
        List<String> permissionIdSet = rolePermissionDao.getPermissionIdSetByRoleId(roleId);


        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("权限列表");
        superTree.setOpen(true);
        superTree.setNocheck(true);
        mapList.add(superTree);
        
        //获取该角色的所有的子id
        /*Set<Integer> allPermissionIdSet = new HashSet<Integer>();
        Iterator<Integer> it = permissionIdSet.iterator(); 
        while (it.hasNext()) {  
          int permissionId = it.next(); 
          allPermissionIdSet.add(permissionId);
          getChildSubPermissionId(permissionList,permissionId,allPermissionIdSet);
        } */
        
        for (Permission permission : permissionList) {
        	TreeNode treeNode = new TreeNode();
        	treeNode.setId(permission.getId().toString());
        	treeNode.setChkDisabled(chkDisabled);
        	treeNode.setName(permission.getName());
        	treeNode.setChecked(permissionIdSet.contains(permission.getId()));
        	treeNode.setOpen(true);
        	if(permission.getParentId()!=null)
        		treeNode.setpId(permission.getParentId().toString());
            else{
            	treeNode.setpId(Constants.SUPER_TREE_NODE);
            }
            mapList.add(treeNode);
        }
        resultVO.setData(mapList);
        return resultVO;
    }
    
    public Set<String> getChildSubPermissionId(List<Permission> permissionList,String permissionId,Set<String> allPermissionIdSet){
	    for(Permission permission : permissionList){
		    if(permission.getParentId()!=null&&permission.getParentId().equals(permissionId)){
		    	allPermissionIdSet.add(permission.getId());
		 	   getChildSubPermissionId(permissionList,permission.getId(),allPermissionIdSet);
		    }
	    }
	    return allPermissionIdSet;
    }
    
    private static boolean isKeyExist(List<Permission> list, String key) {
        for (Permission permission : list) {
            if (permission.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建权限
     *
     * @param permissionCreateVO
     * @return
     */
    public ResultVO cratePermission(PermissionCreateVO permissionCreateVO) {
        ResultVO resultVO = new ResultVO(true);
        //所有权限
        List<Permission> permissionList = permissionDao.selectAll();
        //查看父级权限是否存在
        String parentId = permissionCreateVO.getParentId();
        if (parentId != null) {
            Permission permission = permissionDao.get(permissionList, parentId);
            if (permission == null) {
                resultVO.setOk(false);
                resultVO.setMsg("父级权限不存在");
                return resultVO;
            }
        }
        //判断权限键值是否存在
        if (isKeyExist(permissionList, permissionCreateVO.getKey())) {
            resultVO.setOk(false);
            resultVO.setMsg("权限键值已存在");
            return resultVO;
        }
        
       /* //获取我拥有的权限
        List<Permission> myPermissionList = new ArrayList<Permission>();
        Subject subject = SecurityUtils.getSubject();
        for (Permission permission : permissionList) {
            String key = permission.getKey();
            boolean permitted = subject.isPermitted(key);
            if (permitted) {
                myPermissionList.add(permission);
            }
        }
        
        boolean isAlowed = false;
        for(Permission permission : myPermissionList){
        	if(parentId==permission.getId()){
        		isAlowed = true;
        		break;
        	}
        }
        
        if(!isAlowed){
        	 resultVO.setOk(false);
             resultVO.setMsg("您没有分配的权限");
             return resultVO;
        }*/
        
        Permission permission = new Permission();
        permission.setId(UUIDUtil.getUUID());
        permission.setKey(permissionCreateVO.getKey());
        permission.setName(permissionCreateVO.getName());
        permission.setParentId(permissionCreateVO.getParentId());
        permission.setOrder(permissionCreateVO.getOrder());
        permissionDao.createPermission(permission);

        resultVO.setMsg("权限创建成功");
        return resultVO;
    }

    //编辑权限
    public ResultVO editPermission(PermissionEditVO permissionEditVO) {
        ResultVO resultVO = new ResultVO(true);
        //获取所有权限
        List<Permission> permissionList = permissionDao.selectAll();

        Permission permission = permissionDao.get(permissionList, permissionEditVO.getId());
        if (permission == null) {
            resultVO.setOk(false);
            resultVO.setMsg("权限不存在");
            return resultVO;
        }

        Permission permissionParent = permissionDao.get(permissionList, permissionEditVO.getParentId());
        if (permissionParent == null) {
            resultVO.setOk(false);
            resultVO.setMsg("上级权限不存在");
            return resultVO;
        }

        if (isKeyExist(permissionList, permissionEditVO.getKey())) {
            if (!permission.getKey().equals(permissionEditVO.getKey())) {
                resultVO.setOk(false);
                resultVO.setMsg("权限键值已存在");
                return resultVO;
            }
        }

        List<String> childrenPermissionIds = getChildrenPermissionIds(permissionEditVO.getId(), permissionList);
        childrenPermissionIds.add(permissionEditVO.getId());
        if(childrenPermissionIds.contains(permissionEditVO.getParentId())){
            resultVO.setOk(false);
            resultVO.setMsg("所在权限的上级不能为自己所在权限或者下级权限");
            return resultVO;
        }

        Permission update = new Permission();
        update.setId(permissionEditVO.getId());
        update.setParentId(permissionEditVO.getParentId());
        update.setName(permissionEditVO.getName());
        update.setKey(permissionEditVO.getKey());
        update.setOrder(permissionEditVO.getOrder());
        int num = permissionDao.updatePermission(update);
        if (num == 1) {
            resultVO.setMsg("更新权限成功");
            return resultVO;
        } else {
            resultVO.setOk(false);
            resultVO.setMsg("更新权限失败");
            return resultVO;
        }
    }

    /**
     * 获取子级权限
     *
     * @param permissions
     * @param parentId
     * @return
     */
    public static List<Map<String, Object>> getChildrenPermissions(List<Permission> permissions, String parentId, Set<String> checkedPermissionIdSet,List<String> childrenIdList) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Permission permission : permissions) {
            if ((parentId == null && permission.getParentId() == null) || (parentId != null && permission.getParentId() != null && permission.getParentId().equals(parentId))) {
                if(childrenIdList==null||!childrenIdList.contains(permission.getId())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", permission.getId());
                    map.put("text", permission.getName());
                    map.put("key", permission.getKey());
                    map.put("checked", checkedPermissionIdSet == null ? false : checkedPermissionIdSet.contains(permission.getId()));
                    map.put("order", permission.getOrder());
                    map.put("children", getChildrenPermissions(permissions, permission.getId(), checkedPermissionIdSet, childrenIdList));
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }
    
    /**
     * 获取根权限
     *
     * @param permissionList
     * @return
     */
    private List<Permission> getRootPermissions(List<Permission> permissionList) {
        List<Permission> rootPermissions = new ArrayList<Permission>();
        for (Permission permission : permissionList) {
            //如果父级id是null
            if (permission.getParentId() == null) {
                rootPermissions.add(permission);
                continue;
            }
            Permission parentPermission = permissionDao.get(permissionList, permission.getParentId());
            if (parentPermission == null) {
                rootPermissions.add(permission);
            }
        }
        return rootPermissions;
    }

}
