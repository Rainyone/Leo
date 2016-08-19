package com.larva.service;

import java.util.Map;

import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;

/**
 * Created by sxjun on 15-9-12.
 */
public interface ILogService {

	Pager<Map<String,Object>> queryLoginLog(PagerReqVO pagerReqVO);

    ResultVO getAllUserLocations();


}
