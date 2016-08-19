package com.larva.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.IMonitorDao;
import com.larva.model.K_Instances;
import com.larva.model.K_Versions;
import com.larva.service.IMonitorService;
import com.larva.utils.Constants;
import com.larva.utils.StrKit;
import com.larva.vo.Pager;
import com.larva.vo.PagerReqVO;
import com.larva.vo.ResultVO;
import com.larva.vo.TreeNode;

/**
 * Created by sxjun on 15-9-10.
 */
@Service("monitorService")
public class MonitorServiceImpl implements IMonitorService {
    @Resource
    private IMonitorDao monitorDao;


	@Override
	public List<K_Instances> selectInstances() {
	    return monitorDao.selectInstances();
	}

	@Override
	public void updateInstance(K_Instances instance) {
		monitorDao.updateInstance(instance);
	}

	@Override
	public void updateVersion(String instance_id, String version_type, Long version) {
		monitorDao.updateVersion(instance_id, version_type, version);
	}

	@Override
	public void insertInstance(K_Instances instance) {
		monitorDao.insertInstance(instance);
	}

	@Override
	public void insertVersion(K_Versions version) {
		monitorDao.insertVersion(version);
	}

	@Override
	public List<K_Versions> selectVersionsByInstanceId(String instance_id) {
		return monitorDao.selectVersionsByInstanceId(instance_id);
	}
	
	
	
	private Map<String,Object> geteMonitorMap(List<K_Instances> mons,K_Instances mon){
    	Map<String,Object> m = new HashMap<String,Object>();
		m.put("id", mon.getId());
		m.put("hostname", mon.getHostname());
		m.put("cpuCores", mon.getCpuCores());
		m.put("cpuCounts", mon.getCpuCounts());
		m.put("memTotal", mon.getMemTotal());
		m.put("status", mon.getStatus());
		return m;
	}

	@Override
	public Pager<Map<String, Object>> getPageMonitors(PagerReqVO pagerReqVO) {
		 List<Map<String,Object>> resultMonitors = new ArrayList<Map<String,Object>>();
	        List<K_Instances> mons = monitorDao.selectInstances();
	        int size = 0;
	        if(pagerReqVO==null||pagerReqVO.getLimit()==0){
	        	for(K_Instances mon : mons){
	        		resultMonitors.add(geteMonitorMap(mons,mon));
	        	}
	        	size = mons.size();
	        }else{
	        	size = mons.size();
	        	List<K_Instances> _mons = monitorDao.selectPage(pagerReqVO.getLimit(),pagerReqVO.getPageNo());
	        	for(K_Instances mon : _mons){
	        		resultMonitors.add(geteMonitorMap(mons,mon));
	        	}

	        }
			return new Pager(resultMonitors,size);
	}

	@Override
	public ResultVO getMonitorTree(int parseInt) {
		Pager<Map<String,Object>> pager = this.getPageMonitors(null);
		List<TreeNode> trees = new ArrayList<TreeNode>();
		List<Map<String,Object>> list = pager.getRows();
        TreeNode superTree = new TreeNode();
        superTree.setId(Constants.SUPER_TREE_NODE);
        superTree.setName("所有实例");
        superTree.setOpen(true);
        trees.add(superTree);
        for(Map<String,Object> monitor:list){
            TreeNode tree = new TreeNode();
            tree.setId(monitor.get("id").toString());
            tree.setName(monitor.get("hostname").toString());
            tree.setpId(Constants.SUPER_TREE_NODE);
            tree.setOpen(true);
            trees.add(tree);
        }
        ResultVO resultVO = new ResultVO(true);
        resultVO.setData(trees);
		return resultVO;
	}

	@Override
	public List<K_Versions> selectVersionsByHostname(String hostname) {
		return monitorDao.selectVersionsByHostname(hostname);
	}
    
}
