package com.larva.service;

import java.util.Map;

import com.larva.model.LoginLog;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

public interface ILogService {

	Pager<Map<String,Object>> queryLoginLog(PagerReqVO pagerReqVO);

    ResultVO getAllUserLocations();
    
    int insertLoginLog(LoginLog loginLog);

}
