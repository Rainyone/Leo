package com.larva.mq.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.TextMessage;

import com.larva.job.AnalyseJob;
import com.larva.model.K_Instances;
import com.larva.model.K_Versions;
import com.larva.mq.model.HeartBeat;
import com.larva.service.IMonitorService;
import com.larva.utils.SerializeUtil;
import com.larva.utils.Constants.VERSIONTYPE;
import com.larva.webSocket.TextMessageHandler;

public class HeartBeatListener implements  MessageListener{
	private static final Logger log = LoggerFactory.getLogger(HeartBeatListener.class);
	
	//hostname和mq版本信息
	private static Map<String,Map<String,Long>> versions = new HashMap<String,Map<String,Long>>();;
	
	//socket
	public static Map<String,Boolean> isON = new HashMap<String,Boolean>();
	
	@Resource
	private IMonitorService monitorService;
	
	@Bean
    public TextMessageHandler textMessageHandler() {
        return new TextMessageHandler();
    }
	
	@Override
	public void onMessage(Message message) {
		try {
			if(versions.isEmpty()){//1.将hostname和version_type为0的version载入内存
				List<K_Instances> instances = monitorService.selectInstances();
				for(K_Instances instance :instances)
					this.put(instance);
			}
			HeartBeat heartBeat = (HeartBeat) SerializeUtil.unserialize(message.getBody());
			if(!versions.containsKey(heartBeat.getHostname())){//2.如果不存在实例，则插入mysql数据库,并加入version内存
				K_Instances instance = new K_Instances();
				instance.setId(heartBeat.getId());
				instance.setHostname(heartBeat.getHostname());
				instance.setCpuCores(heartBeat.getCpuCores());
				instance.setCpuCounts(heartBeat.getCpuCounts());
				instance.setMemTotal(heartBeat.getMemTotal());
				instance.setStatus("0");
				monitorService.insertInstance(instance);
				K_Versions version = new K_Versions();
				version.setId(UUID.randomUUID().toString());
				version.setInstance_id(instance.getId());
				version.setVersion_type(VERSIONTYPE.MQ_MAIN.getValue());
				version.setVersion(heartBeat.getVersion().get(VERSIONTYPE.MQ_MAIN.getValue()));
				version.setVersion_name(heartBeat.getHostname()+"-客户端版本");
				monitorService.insertVersion(version);
				this.put(instance);
			}
			
			if(!versions.get(heartBeat.getHostname()).equals(heartBeat.getVersion())){//3.版本不相同则更新数据库版本和version内存
				monitorService.updateVersion(heartBeat.getId(), VERSIONTYPE.MQ_MAIN.getValue(), heartBeat.getVersion().get(VERSIONTYPE.MQ_MAIN.getValue()));
				K_Instances instance = new K_Instances();
				instance.setId(heartBeat.getId());
				instance.setHostname(heartBeat.getHostname());
				this.put(instance);
			}
			
			AnalyseJob.heartbeats.get(heartBeat.getHostname()).put(heartBeat);
			
			if(!isON.isEmpty()&&isON.get(heartBeat.getHostname())){
				TextMessage msg = new TextMessage("{'ok':true,'cpu':"+heartBeat.getCpuPercent()+",'memory':"+heartBeat.getMemPercent()+"}");
		        textMessageHandler().sendMessageToUsers(msg);
			}
			
			log.info("CPU has use "+heartBeat.getCpuPercent()+"%;MEMORY has use "+ heartBeat.getMemPercent()+"%");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void put(K_Instances instance){
		List<K_Versions> vers = monitorService.selectVersionsByHostname(instance.getHostname());
		Map<String,Long> m = new HashMap<String,Long>();
		for(K_Versions v : vers){
			m.put(v.getVersion_type(), v.getVersion());
		}
		versions.put(instance.getHostname(), m);
		if(!isON.containsKey(instance.getHostname()))
			isON.put(instance.getHostname(), false);
		AnalyseJob.heartbeats.put(instance.getHostname(), new LinkedBlockingQueue<HeartBeat>());
	}
	
}
