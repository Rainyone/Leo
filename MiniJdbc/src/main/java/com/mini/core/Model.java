package com.mini.core;

/**
 * BaseEntity接口
 * @author sxjun
 * 2016-2-1
 */
public interface Model {
	/**
	 * 获取表名
	 * @return
	 */
	String getTableName();
	
	/**
	 * 获取主键名
	 * @return
	 */
	String[] getPrimaryKeys();
}
