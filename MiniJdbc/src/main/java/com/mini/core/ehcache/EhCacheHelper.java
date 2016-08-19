
package com.mini.core.ehcache;

import java.io.InputStream;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.Configuration;

/**
 * 缓存插件
 * @author sxjun
 * EhCachePlugin.
 */
public class EhCacheHelper{
	private CacheManager cacheManager;
	private String configurationFileName;
	private URL configurationFileURL;
	private InputStream inputStream;
	private Configuration configuration;
	
	public EhCacheHelper() {
		
	}
	
	public EhCacheHelper(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public EhCacheHelper(String configurationFileName) {
		this.configurationFileName = configurationFileName; 
	}
	
	public EhCacheHelper(URL configurationFileURL) {
		this.configurationFileURL = configurationFileURL;
	}
	
	public EhCacheHelper(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public EhCacheHelper(Configuration configuration) {
		this.configuration = configuration;
	}
	
	/**
	 * 启动插件
	 * @return
	 */
	public boolean start() {
		createCacheManager();
		CacheKit.init(cacheManager);
		return true;
	}
	
	/**
	 * 创建缓存管理对象
	 */
	private void createCacheManager() {
		if (cacheManager != null)
			return ;
		
		if (configurationFileName != null) {
			cacheManager = CacheManager.create(configurationFileName);
			return ;
		}
		
		if (configurationFileURL != null) {
			cacheManager = CacheManager.create(configurationFileURL);
			return ;
		}
		
		if (inputStream != null) {
			cacheManager = CacheManager.create(inputStream);
			return ;
		}
		
		if (configuration != null) {
			cacheManager = CacheManager.create(configuration);
			return ;
		}
		
		cacheManager = CacheManager.create();
	}
	
	/**
	 * 停用插件
	 * @return
	 */
	public boolean stop() {
		cacheManager.shutdown();
		return true;
	}
	
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}




