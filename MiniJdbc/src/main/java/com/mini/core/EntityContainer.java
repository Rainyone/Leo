package com.mini.core;

import java.util.Set;

import org.springframework.util.Assert;

/**
 * 实体容器类，包括数据库，表名/表前缀等注解信息，启动时候统一加载，方便后续提取注解信息
 * 对RowMapper进行集中实例化，后续直接提取，对于自定义的Bean，第一次查询的时候初始化
 * 
 * @author sxjun
 * @date 2014-7-9 下午4:39:15
 * 
 */
public class EntityContainer {
	private static boolean hasInit = false;
	
	/**
	 * 扫描所有代码注解
	 * 
	 * @Table
	 * @Database
	 */
	public synchronized static void init(String entityPackage) {
		if (hasInit == false) {
			hasInit = true;
			//initScanPackage();
			Set<EntityInfo> set = EntityScanner.getAllEntities(entityPackage);
			if (!set.isEmpty()) {
				for (EntityInfo ei : set) {
					EntityMapping.me().putEntity(ei);
					BaseEntityMapper.initEntity(ei);
				}
			} else {
				Assert.notEmpty(set, "没有指定的Entity！");
			}
		}
	}

}
