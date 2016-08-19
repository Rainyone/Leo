package com.larva.job;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.larva.mq.model.HeartBeat;

public class AnalyseJob {
	private static final Logger log = LoggerFactory.getLogger(AnalyseJob.class);
	
	@Value("${larva.quartz.analyse}")
    private Integer larva_quartz_analyse;
	
	//heartbeat
	public static Map<String,LinkedBlockingQueue<HeartBeat>>  heartbeats = new HashMap<String,LinkedBlockingQueue<HeartBeat>>();
	
	public void doJob() {
	    for(Map.Entry<String, LinkedBlockingQueue<HeartBeat>> entry : heartbeats.entrySet()) {
	    	HeartBeat pre = heartbeats.get(entry.getKey()).peek();
	    	if(pre==null){
	    		log.error("{}网络失败！",entry.getKey());
	    		return;
	    	}
	    	//从队列中抓取的数量
	    	int fetch = larva_quartz_analyse/Integer.parseInt(pre.getInterval());
	    	if(larva_quartz_analyse%Integer.parseInt(pre.getInterval())>0){
	    		fetch++;
	    	}
	    	
	    	int error=0;
	    	for(int i = 0;i<fetch;i++){
	    		HeartBeat hb = heartbeats.get(entry.getKey()).poll();
	    		if(hb!=null){
	    			
	    		}else{
	    			error++;
	    		}
	    	}
	    	if(error/fetch==1){
	    		log.error("{}网络失败！",entry.getKey());
	    	}else if(error/fetch>0.3)
	    		log.error("{}网络很差，请检查，丢包率：{}%",entry.getKey(),(100*error/fetch));
	    }
	}
}
