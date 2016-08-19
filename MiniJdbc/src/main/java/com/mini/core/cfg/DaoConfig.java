package com.mini.core.cfg;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mini.core.DbHelper;
import com.mini.core.dao.MiniDao;
import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.DialectFactory;
import com.mini.core.dialect.H2Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;
import com.mini.core.dynamic.DynamicDataSource;
import com.mini.core.dynamic.DynamicDialect;

@Configuration  
@EnableAspectJAutoProxy(proxyTargetClass=true) 
//启用注解事务管理，使用CGLib代理  
@EnableTransactionManagement
public class DaoConfig {
	/*@Autowired
	private DynamicDataSource dynamicDataSource;
	
	*//**
	 * 加载Mysql方言
	 * @return MysqlDialect
	 *//*
	@Bean
    public Dialect mysqlDialect() {
        return new MysqlDialect();
    }
	
	*//**
	 * 加载Oracle方言
	 * @return OracleDialect
	 *//*
	@Bean
    public Dialect oracleDialect() {
        return new OracleDialect();
    }
	
	*//**
	 * 加载SqlServer方言
	 * @return SqlServerDialect
	 *//*
	@Bean
    public Dialect sqlServerDialect() {
        return new SqlServerDialect();
    }
	
	*//**
	 * 加载H2Dialect方言
	 * @return H2Dialect
	 *//*
	@Bean
    public Dialect h2Dialect() {
        return new H2Dialect();
    }
	
	*//**
	 * 加载JdbcTemplate
	 * @return JdbcTemplate
	 *//*
	@Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dynamicDataSource);
        return jdbcTemplate;
    }
	
	*//**
	 * 加载动态方言
	 * @return DynamicDialect
	 *//*
	@Bean
    public DynamicDialect dynamicDialect(){
		DynamicDialect dynamicDialect = new DynamicDialect();
		dynamicDialect.setTargetDialect(loadDialect(dynamicDataSource.getTargetDataSources()));
		dynamicDialect.setDefaultDialect(loadDialect((DataSource)dynamicDataSource.getDefaultTargetDataSource()));
		return dynamicDialect;
	}
	
	*//**
	 * 加载数据库支持类
	 * @return DbHelper
	 *//*
	@Bean
	public DbHelper dbHelper(){
		DbHelper dbHelper = new DbHelper();
		dbHelper.setJdbcTemplate(jdbcTemplate());
		dbHelper.setDialect(dynamicDialect());
		return dbHelper;
	}
	
	*//**
	 * 加载MiniDao
	 * @return MiniDao
	 *//*
	@Bean
	public MiniDao miniDao(){
		MiniDao miniDao = new MiniDao();
		miniDao.setDbHelper(dbHelper());
		return miniDao;
	}
	
	*//**
	 * 加载事务管理
	 * @return DataSourceTransactionManager
	 *//*
	@Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {  
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();  
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;  
    }  
	
	*//**
	 * 获取方言
	 * @param datasources
	 * @return
	 *//*
	public Map<Object,Dialect> loadDialect(Map<Object, Object> datasources){
		Map<Object,Dialect> dialects = new HashMap<Object,Dialect>();
		for(Entry<Object, Object> entry : datasources.entrySet()){
			dialects.put(entry.getKey(), loadDialect(loadDialect((DataSource)entry.getValue())));
		}
		return dialects;
	}
	
	*//**
	 * 获取方言
	 * @param datasource
	 * @return
	 *//*
	public Dialect loadDialect(DataSource datasource){
		Dialect dialect = null;
		try {
			dialect = DialectFactory.getDialect(datasource.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loadDialect(dialect);
	}
	
	*//**
	 * 获取方言
	 * @param dialect
	 * @return
	 *//*
	public Dialect loadDialect(Dialect dialect){
		if(dialect instanceof OracleDialect){
			return oracleDialect();
		}else if(dialect instanceof SqlServerDialect){
			return sqlServerDialect();
		}else if(dialect instanceof  MysqlDialect){
			return mysqlDialect();
		}else if(dialect instanceof  H2Dialect){
			return h2Dialect();
		}
		return null;
	}*/
}
