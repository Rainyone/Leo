package com.larva.mq.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class HeartBeat implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String hostname;
	private Map<String,Long> version;
	private Long noticetime;
	private String interval;
	
	private String cpuCounts;
	private String cpuCores;
	private String memTotal;
	
	private String memPercent;
	private String cpuPercent;
	
	public String getCpuCounts() {
		return cpuCounts;
	}
	public void setCpuCounts(String cpuCounts) {
		this.cpuCounts = cpuCounts;
	}
	public String getCpuCores() {
		return cpuCores;
	}
	public void setCpuCores(String cpuCores) {
		this.cpuCores = cpuCores;
	}
	public String getMemTotal() {
		return memTotal;
	}
	public void setMemTotal(String memTotal) {
		this.memTotal = memTotal;
	}
	public String getMemPercent() {
		return memPercent;
	}
	public void setMemPercent(String memPercent) {
		this.memPercent = memPercent;
	}
	public String getCpuPercent() {
		return cpuPercent;
	}
	public void setCpuPercent(String cpuPercent) {
		this.cpuPercent = cpuPercent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public Long getNoticetime() {
		return noticetime;
	}
	public void setNoticetime(Long noticetime) {
		this.noticetime = noticetime;
	}
	public Map<String, Long> getVersion() {
		return version;
	}
	public void setVersion(Map<String, Long> version) {
		this.version = version;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
}
