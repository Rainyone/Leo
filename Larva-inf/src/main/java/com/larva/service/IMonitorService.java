package com.larva.service;

import java.util.List;
import java.util.Map;

import com.larva.model.K_Instances;
import com.larva.model.K_Versions;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * Created by sxjun on 15-9-10.
 */
public interface IMonitorService {

	List<K_Instances> selectInstances();

	void updateInstance(K_Instances instance);

	void updateVersion(String instance_id, String version_type, Long version);

	void insertInstance(K_Instances instance);

	void insertVersion(K_Versions version);

	List<K_Versions> selectVersionsByInstanceId(String instance_id);

	Pager<Map<String, Object>> getPageMonitors(PagerReqVO pagerReqVO);

	ResultVO getMonitorTree(int parseInt);

	List<K_Versions> selectVersionsByHostname(String hostname);
}
