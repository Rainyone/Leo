/**   
 * EntityScanner.java
 *
 * @author sxjun
 * @date 2014-7-25 下午11:50:14 
 * @verion 0.1.0
 */
package com.mini.core;

import java.util.HashSet;
import java.util.Set;

import com.mini.core.utils.EntityParser;

/** 
 * 实体类扫描器，仅扫描符合特征的Entity类，并返回实例化EntityInfo类集合
 * EntityInfo包含 数据库名、表名、类名、RowMapper信息，都会在这个类中实例化
 * 
 * @author sxjun
 * @date 2014-7-25 下午11:50:14 
 *
 */
public class EntityScanner {
	/**
	 * 扫描实体类
	 * @param packageName
	 * @return
	 */
	public static Set<EntityInfo> getAllEntities(String packageName) {
		Set<EntityInfo> results = new HashSet<EntityInfo>();
		String[] packages = packageName.split(",");
		for(String pkg : packages)
			EntityParser.getClassName(pkg, true, results);
		return results;
    }
}