package com.larva.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IAppManageDao;
import com.larva.dao.ICommomDao;
import com.larva.model.AppManage;
import com.larva.model.AreaManage;
import com.larva.model.Department;
import com.larva.model.IspManage;
import com.larva.service.IAppManageService;
import com.larva.service.ICommomService;
import com.larva.utils.Constants;
import com.larva.utils.UUIDUtil;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.mini.core.PageResult;

@Service("commomService")
public class CommomServiceImpl implements ICommomService {
    @Resource
    private ICommomDao commomDao;
	@Override
	public ResultVO getProvAreaTree() {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        List<AreaManage> list = commomDao.selectProvAreas();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("所有区域");
        superTree.setOpen(true);
        trees.add(superTree);
        for(AreaManage area:list){
            TreeNode tree = new TreeNode();
            tree.setId(area.getAreaId());
            tree.setName(area.getAreaName());
            tree.setOpen(true);
            if(area.getParentId()!=null)
            	tree.setpId(area.getParentId());
            else{
            	tree.setpId(Constants.SUPER_TREE_NODE);
            }
            trees.add(tree);
        }
        ResultVO resultVO = new ResultVO(true);
        resultVO.setData(trees);
		return resultVO;
	}
	@Override
	public ResultVO getIsps() {
		List<TreeNode> trees = new ArrayList<TreeNode>();
        List<IspManage> list = commomDao.selectIsps();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("所有运营商");
        superTree.setOpen(true);
        trees.add(superTree);
        for(IspManage isp:list){
            TreeNode tree = new TreeNode();
            tree.setId(isp.getIspCode());
            tree.setName(isp.getIspName());
            tree.setOpen(true);
//            if(isp.getId()!=null)
//            	tree.setpId(isp.getId());
//            else{
            	tree.setpId(Constants.SUPER_TREE_NODE);
//            }
            trees.add(tree);
        }
        ResultVO resultVO = new ResultVO(true);
        resultVO.setData(trees);
		return resultVO;
	}
}
