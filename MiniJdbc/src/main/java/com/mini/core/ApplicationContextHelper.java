/**   
 * ApplicationContext.java
 *
 * @author sxjun
 * @date 2014-7-25 下午11:33:15 
 * @verion 0.1.0
 */
package com.mini.core;

import org.springframework.context.ApplicationContext;

/** 
 * Context，在DatabaseRouter初始化后设置
 * @author sxjun
 * @date 2014-7-25 下午11:33:15 
 *
 */
public class ApplicationContextHelper {

	/**
	 * spring上下文
	 */
	private static ApplicationContext context;
	
	/**
	 * 设置spring上下文
	 * @param _context
	 */
	public static void setApplicationContext(ApplicationContext _context) {
		if (context == null) {
			context = _context;
		}
	}
	
	/**
	 * 获取spring上下文
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	/**
	 * 上下文中获取spring的bean
	 * @param key
	 * @return
	 */
	public static Object getBean(String key) {
		return context.getBean(key);
	}
}
