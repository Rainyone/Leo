package com.larva.service;

import java.util.Map;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import com.larva.model.Account;
import com.larva.model.AppManage;
import com.larva.vo.AppManageCreateVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;
import com.larva.vo.UserCreateVO;
import com.larva.vo.UserEditVO;
import com.mini.core.PageResult;

/**
 * @author Rainy Wang
 * @time 2016/8/14
 */
public interface IChargeCodeService {

    ResultVO  saveAppManage(AppManageCreateVO createVO);

    Pager<Map<String, Object>> getAppManages(PagerReqVO pagerReqVO);
}
