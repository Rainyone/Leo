/**   
 * @Title: MysqlTypeMapping.java 
 * @Description: TODO
 * @author Rick
 * @date 2014-7-15 下午11:14:07 
 * @version V1.0   
 */
package com.mini.core.generator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * Mysql 类型映射
 * 
 * @author Rick 
 * @date 2014-7-15 下午11:14:07 
 * 
 */
public class MysqlTypeMapping implements IDialectMapping{

	/**
	 * mysql 数据类型对应类
	 */
	public static final Map<String, Class<?>> mapping = new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = -1168963664693153914L;
		{
			put("INT", Integer.class);
			put("TINYINT", Integer.class);
			put("VARCHAR", String.class);
			put("CHAR", String.class);
			put("BLOB", String.class);
			put("TEXT", String.class);
			put("INTEGER", Long.class);
			put("TINYINT", Integer.class);
			put("SMALLINT", Integer.class);
			put("MEDIUMINT", Integer.class);
			put("BIT", Boolean.class);
			put("BIGINT", BigInteger.class);
			put("FLOAT", Float.class);
			put("DOUBLE", Double.class);
			put("DECIMAL", BigDecimal.class);
			put("BOOLEAN", Integer.class);
			put("ID", Long.class);
			put("DATE", Date.class);
			put("Time", Time.class);
			put("DATETIME", Date.class);
			put("TIMESTAMP", Date.class);
			put("YEAR", Date.class);
			put("LONGTEXT", String.class);
		}
	};
	
	@Override
	public Map<String, Class<?>> getMapping() {
		return mapping;
	}
}
