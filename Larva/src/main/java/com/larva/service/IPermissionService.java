package com.larva.service;

import java.util.Map;

import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.PermissionCreateVO;
import com.larva.vo.PermissionEditVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * @author sxjun
 * @time 2015/8/28 9:44
 */
public interface IPermissionService {

	Pager<Map<String,Object>> getShowPermissions(PagerReqVO pagerReqVO,TreeNode tree);

    ResultVO getMenuShowPermissions(int menuId,boolean chkDisabled);

    ResultVO getRoleShowPermissions(int roleId,boolean chkDisabled);

    ResultVO cratePermission(PermissionCreateVO permissionCreateVO);

    ResultVO editPermission(PermissionEditVO permissionEditVO);

    ResultVO delPermission(int[] permIds);

	ResultVO getPermissionTree();
}
