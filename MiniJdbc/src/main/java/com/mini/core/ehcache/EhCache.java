package com.mini.core.ehcache;

/**
 * @author sxjun
 * EhCache.
 */
public class EhCache{
	
	@SuppressWarnings("unchecked")
	public <T>T get(String cacheName, Object key) {
		return (T)CacheKit.get(cacheName, key);
	}
	
	public void put(String cacheName, Object key, Object value) {
		CacheKit.put(cacheName, key, value);
	}
	
	public void remove(String cacheName, Object key) {
		CacheKit.remove(cacheName, key);
	}
	
	public void removeAll(String cacheName) {
		CacheKit.removeAll(cacheName);
	}
}


