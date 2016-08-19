package com.mini.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ApplicationObjectSupport;

import com.mini.core.utils.EnumClazz.Caps;
import com.mini.core.utils.StrKit;

/**
 * @author sxjun
 * @date 2014-7-9 下午3:49:07
 */
public class DatabaseRouter extends ApplicationObjectSupport implements InitializingBean{
	private final Log logger = LogFactory.getLog(DatabaseRouter.class);
	/**
	 * 初始化加载实体的包名
	 */
	private String entityPackage;
	
	/**
	 * 大小写的设置，默认为小写
	 */
	private static Caps caps = Caps.LOWER;

	/**
	 * 获取数据库操作的大小写格式
	 * @return
	 */
	public static Caps getCaps() {
		return caps;
	}

	/**
	 * 设置数据库大小写的格式
	 * @param caps
	 */
	public static void setCaps(Caps caps) {
		DatabaseRouter.caps = caps;
	}

	/**
	 * 获取实体扫描包名
	 * @return
	 */
	public String getEntityPackage() {
		return entityPackage;
	}

	/**
	 * 设置实体扫描包名
	 * @param entityPackage
	 */
	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	/**
	 * 解析配置信息
	 */
	public void afterPropertiesSet() throws Exception {
		ApplicationContextHelper.setApplicationContext(getApplicationContext());
		// 执行初始化
		if(StrKit.notBlank(entityPackage))
			EntityContainer.init(entityPackage);
	}
	
}
