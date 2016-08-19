package com.mini.core.dialect;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class DialectFactory {
	private final static Log logger = LogFactory.getLog(DialectFactory.class);
	public static Dialect getDialect(DATABASETYPE databaseType) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
	    if(DATABASETYPE.ORACLE.equals(databaseType))
			return OracleDialect.class.newInstance();
		else if(DATABASETYPE.SQLSERVER.equals(databaseType))
			return SqlServerDialect.class.newInstance();
		else if(DATABASETYPE.MYSQL.equals(databaseType))
			return MysqlDialect.class.newInstance();
		else 
			return null;
	}     
	public enum DATABASETYPE{  
		MYSQL("MySQL"),
		ORACLE("Oracle"),
		SQLSERVER("Microsoft SQL Server");
		private final String value;

		private DATABASETYPE(String value) {
            this.value = value;
        }  
        public String getValue(){  
            return this.value;  
        }  
	} 
	
	public static DATABASETYPE getDatabaseType(Connection connection) {
		try {
			String databaseType = connection.getMetaData().getDatabaseProductName();
			if(DATABASETYPE.ORACLE.getValue().equals(databaseType))
				return DATABASETYPE.ORACLE;
			else if(DATABASETYPE.SQLSERVER.getValue().equals(databaseType))
				return DATABASETYPE.SQLSERVER;
			else if(DATABASETYPE.MYSQL.getValue().equals(databaseType))
				return DATABASETYPE.MYSQL;
		} catch (SQLException e) {
			logger.error("暂不支持该数据库类型");
			e.printStackTrace();
		}
		return null;
	}
	
	public static Dialect getDialect(Connection connection) {
		try {
			return getDialect(getDatabaseType(connection));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Dialect getDialect(JdbcTemplate jdbcTemplate) {
		try {
			return getDialect(getDatabaseType(jdbcTemplate.getDataSource().getConnection()));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
