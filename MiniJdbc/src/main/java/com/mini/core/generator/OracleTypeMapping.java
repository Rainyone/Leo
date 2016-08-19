/**   
 * @Title: MysqlTypeMapping.java 
 * @Description: TODO
 * @author Rick
 * @date 2014-7-15 下午11:14:07 
 * @version V1.0   
 */
package com.mini.core.generator;

import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * Oracle 类型映射
 * 
 * @author Rick 
 * @date 2014-7-15 下午11:14:07 
 * 
 */
public class OracleTypeMapping implements IDialectMapping{

	/**
	 * Oracle 数据类型对应类
	 */
	public static final Map<String, Class<?>> mapping = new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = -1168963664693153914L;
		{
			put("INT", Integer.class);
			put("TINYINT", Integer.class);
			put("VARCHAR", String.class);
			put("VARCHAR2", String.class);
			put("NVARCHAR2", String.class);
			put("CHAR", String.class);
			put("NCHAR", String.class);
			put("BLOB", Blob.class);
			put("CLOB", Clob.class);
			put("NCLOB ", Clob.class);
			put("NUMBER", Long.class);
			put("INTEGER", Integer.class);
			put("BINARY_FLOAT", Float.class);
			put("TIMESTAMP", Date.class);
			put("DATE", Date.class);
			put("TIME", Date.class);
		}
	};
	
	@Override
	public Map<String, Class<?>> getMapping() {
		return mapping;
	}
}
