package com.mini.core.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * DbKit
 */
@SuppressWarnings("rawtypes")
public final class DbKit {
	
	
	static final Object[] NULL_PARA_ARRAY = new Object[0];
	public static final String MAIN_CONFIG_NAME = "main";
	
	private DbKit() {}
	
	public static String replaceFormatSqlOrderBy(String sql) {
		sql = sql.replaceAll("(\\s)+", " ");
		int index = sql.toLowerCase().lastIndexOf("order by");
		if (index > sql.toLowerCase().lastIndexOf(")")) {
			String sql1 = sql.substring(0, index);
			String sql2 = sql.substring(index);
			sql2 = sql2.replaceAll("[oO][rR][dD][eE][rR] [bB][yY] [\u4e00-\u9fa5a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[\u4e00-\u9fa5a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*", "");
			return sql1 + sql2;
		}
		return sql;
	}
	
	public static String replaceFormatSqlFrom(String sql) {
		int index = sql.toLowerCase().indexOf("from");
		int _index = sql.toLowerCase().lastIndexOf("(");
		if(_index<0||_index>index){
			return sql.substring(index);
		}
		return sql;
	}
	
}




