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

    Pager<Map<String,Object>> getShowRoles(PagerReqVO pagerReqVO,String userId);

    ResultVO createRole(RoleCreateVO createVO, String userId);

    ResultVO deleteRole(String[] roleIds);

    ResultVO editRole(RoleEditVO editVO);

    ResultVO grantPermissions(String roleId, String[] perIdArray);
    
    ResultVO getRoleTree(String userId);
}
