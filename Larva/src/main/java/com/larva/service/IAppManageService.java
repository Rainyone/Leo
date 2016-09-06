package com.larva.service;

import java.util.Map;

import com.larva.vo.AppManageCreateVO;
import com.larva.vo.AppManageEditVO;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

/**
 * @author Rainy Wang
 * @time 2016/8/14
 */
public interface IAppManageService {

    ResultVO  saveAppManage(AppManageCreateVO createVO);
    
    ResultVO  editAppManage(AppManageEditVO createVO);
    
    ResultVO deleteAppManage(String[] appIds, String czr);

    Pager<Map<String, Object>> getAppManages(PagerReqVO pagerReqVO);
    
    ResultVO getAreaTree();
}
