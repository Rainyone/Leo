package com.larva.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.larva.dao.IMonitorDao;
import com.larva.model.K_Instances;
import com.larva.model.K_Versions;
import com.mini.core.dao.IMiniDao;
import com.mini.core.dao.MiniDao;

/**
 * Created by sxjun on 15-9-10.
 */
@Repository("monitorDao")
public class MonitorDaoImpl extends MiniDao implements IMonitorDao {

	@Override
	public List<K_Instances> selectInstances() {
		return this.findList("select * from K_Instances",K_Instances.class);
	}

	@Override
	public void updateInstance(K_Instances instance) {
		this.update(instance);
	}

	@Override
	public void updateVersion(String instance_id, String version_type, Long version) {
		this.execute("update K_VERSIONS set version = ? where instance_id = ? and version_type = ?",version,instance_id,version_type);
	}

	@Override
	public void insertInstance(K_Instances instance) {
		this.insert(instance);
	}

	@Override
	public void insertVersion(K_Versions version) {
		this.insert(version);
	}

	@Override
	public List<K_Versions> selectVersionsByInstanceId(String instance_id) {
		return this.findList("from  K_VERSIONS t where INSTANCE_ID=",K_Versions.class,instance_id);
	}

	@Override
	public List<K_Instances> selectPage(int limit, int pageNo) {
		return this.paginate("select * from  K_INSTANCES", pageNo, limit, K_Instances.class);
	}

	@Override
	public List<K_Versions> selectVersionsByHostname(String hostname) {
		return this.findList("select  * from  K_VERSIONS t where INSTANCE_ID=(select ID from K_INSTANCES where HOSTNAME=?",K_Versions.class,hostname);
	}
}
