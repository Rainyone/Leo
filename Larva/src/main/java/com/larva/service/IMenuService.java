package com.larva.service;

import java.util.List;
import java.util.Map;

import com.larva.vo.MenuCreateVO;
import com.larva.vo.MenuEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * Created by sxjun on 15-8-27.
 */
public interface IMenuService {

    List<Map<String,Object>> selectShowMenus(Integer rootId);

    ResultVO createMenu(MenuCreateVO createVO);

    ResultVO deleteMenu(int[] menuIds);

    ResultVO editMenu(MenuEditVO editVO);

    ResultVO grantPermissions(int menuId, Integer[] perIdArray);

    Pager<Map<String, Object>> getPageMenus(PagerReqVO pagerReqVO, TreeNode tree);

	ResultVO getMenuTree();
}
