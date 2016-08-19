package com.larva.dao;

import java.util.List;

import com.larva.model.K_Instances;
import com.larva.model.K_Versions;

/**
 * Created by sxjun on 15-9-10.
 */
public interface IMonitorDao {

    List<K_Instances> selectInstances();

	void updateInstance(K_Instances instance);

	void updateVersion(String instance_id, String version_type, Long version);

	void insertInstance(K_Instances instance);

	void insertVersion(K_Versions version);

	List<K_Versions> selectVersionsByInstanceId(String instance_id);

	List<K_Instances> selectPage(int limit, int pageNo);

	List<K_Versions> selectVersionsByHostname(String hostname);
}
