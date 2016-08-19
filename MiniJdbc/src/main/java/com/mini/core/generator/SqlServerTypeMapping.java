/**   
 * @Title: MysqlTypeMapping.java 
 * @Description: TODO
 * @author Rick
 * @date 2014-7-15 下午11:14:07 
 * @version V1.0   
 */
package com.mini.core.generator;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 
 * SqlServer 类型映射
 * 
 * @author Rick 
 * @date 2014-7-15 下午11:14:07 
 * 
 */
public class SqlServerTypeMapping implements IDialectMapping{

	/**
	 * Oracle 数据类型对应类
	 */
	public static final Map<String, Class<?>> mapping = new HashMap<String, Class<?>>() {
		private static final long serialVersionUID = -1168963664693153914L;
		{
			put("INT", Integer.class);
			put("TINYINT", Integer.class);
			put("SMALLINT", Integer.class);
			put("VARCHAR", String.class);
			put("NVARCHAR", String.class);
			put("CHAR", String.class);
			put("NCHAR", String.class);
			put("UNIQUEIDENTIFIER", String.class);
			put("BIT", Boolean.class);
			put("FLOAT", Double.class);
			put("DECIMAL", BigDecimal.class);
			put("MONEY", BigDecimal.class);
			put("SMALLMONEY", BigDecimal.class);
			put("NUMERIC", BigDecimal.class);
			put("REAL", Float.class);
			put("BINARY", byte[].class);
			put("VARBINARY", byte[].class);
			put("TEXT", Clob.class);
			put("NTEXT", Clob.class);
			put("IMAGE", Blob.class);
			put("SQL_VARIANT", byte[].class);
			put("SMALLDATETIME", Date.class);
			put("TIMESTAMP", Date.class);
			put("DATETIME", Date.class);
			put("DATETIME2", Date.class);
			put("DATE", Date.class);
			put("TIME", Date.class);
		}
	};
	
	@Override
	public Map<String, Class<?>> getMapping() {
		return mapping;
	}
}
