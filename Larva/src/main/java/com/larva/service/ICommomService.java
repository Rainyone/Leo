package com.larva.service;

import com.larva.vo.ResultVO;

public interface ICommomService {
	/**
	 * 获取省级区域
	 * @param pagerReqVO
	 * @return
	 */
	public ResultVO getProvAreaTree();
	/**
	 * 获取isp信息
	 * @return
	 */
	public ResultVO getIsps();
}
