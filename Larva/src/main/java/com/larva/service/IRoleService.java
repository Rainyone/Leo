package com.larva.service;

import java.util.Map;

import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.RoleCreateVO;
import com.larva.vo.RoleEditVO;

/**
 * @author sxjun
 * @time 2015/9/1 16:36
 */
public interface IRoleService {

    Pager<Map<String,Object>> getShowRoles(PagerReqVO pagerReqVO,int userId);

    ResultVO createRole(RoleCreateVO createVO, int userId);

    ResultVO deleteRole(int[] roleIds);

    ResultVO editRole(RoleEditVO editVO);

    ResultVO grantPermissions(int roleId, Integer[] perIdArray);
    
    ResultVO getRoleTree(int userId);
}
