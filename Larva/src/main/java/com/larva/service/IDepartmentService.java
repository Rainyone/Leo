package com.larva.service;

import com.larva.vo.*;
import com.larva.model.Department;
import com.larva.vo.DepartmentCreateVO;
import com.larva.vo.DepartmentEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

import java.util.List;
import java.util.Map;

/**
 * @author sxjun
 * @time 2015/9/2 10:14
 */
public interface IDepartmentService {

	Pager<Map<String,Object>> getPageDepartments(PagerReqVO pagerReqVO,TreeNode tree);
    
    /*ResultVO getShowDepartments(int userId, Integer rootId,Integer checkedUserId);*/

    ResultVO createDepartment(DepartmentCreateVO createVO);

    ResultVO editDepartment(DepartmentEditVO createVO, String accountId);

    ResultVO deleteDep(String[] depIds, String accountId);

    ResultVO getDeptTree(String userId);
}
