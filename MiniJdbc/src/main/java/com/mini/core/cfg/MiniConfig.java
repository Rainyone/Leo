package com.mini.core.cfg;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mini.core.DbHelper;
import com.mini.core.MiniDaoException;
import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.DialectFactory;
import com.mini.core.dialect.H2Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;
import com.mini.core.dynamic.DynamicDataSource;
import com.mini.core.dynamic.DynamicDialect;
import com.mini.core.ehcache.EhCacheHelper;

import net.sf.ehcache.CacheManager;

/**
 * 基础配置文件
 * @author sxjun1904
 *
 */
public class MiniConfig {
	/**
	 * 数据库帮助类，非必须配置
	 */
	private DbHelper dbHelper;
	
	/**
	 * 动态数据源，dynamicDialect和dynamicDataSource两者选一配置
	 */
	private DynamicDataSource dynamicDataSource;
	
	/**
	 * 缓存帮助类，非必须配置
	 */
	private CacheManager cacheManager;
	
	/**
	 * jdbcTemplate类，非必须配置
	 */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 动态方言，dynamicDialect和dynamicDataSource两者选一配置
	 */
	private DynamicDialect dynamicDialect;
	
	/**
	 * 是否需要自动切换数据库,默认不启用
	 */
	private boolean exchange = false;
	
	public MiniConfig(){
	//	initCache();
	}

	public void initCache() {
		EhCacheHelper ehCacheHelper = null;
		if(cacheManager!=null)
			ehCacheHelper = new EhCacheHelper(cacheManager);
		else
			ehCacheHelper = new EhCacheHelper();
		ehCacheHelper.start();
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public DynamicDataSource getDynamicDataSource() {
		return dynamicDataSource;
	}

	public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
		this.dynamicDataSource = dynamicDataSource;
	}

	public DbHelper getDbHelper() {
		if(dbHelper==null){
			dbHelper = new DbHelper();
			dbHelper.setJdbcTemplate(getJdbcTemplate());
			dbHelper.setDialect(getDynamicDialect());
			dbHelper.setExchange(isExchange());
		}
		return dbHelper;
	}

	public void setDbHelper(DbHelper dbHelper) {
		this.dbHelper = dbHelper;
	}

	public boolean isExchange() {
		return exchange;
	}

	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}

	public JdbcTemplate getJdbcTemplate() {
		if(jdbcTemplate==null){
			if(getDynamicDataSource()!=null){
				jdbcTemplate = new JdbcTemplate();
				jdbcTemplate.setDataSource(getDynamicDataSource());
			}else{
				throw new MiniDaoException("You have not config dynamicDataSource");
			}
		}
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public DynamicDialect getDynamicDialect() {
		if(dynamicDialect==null)
			if(getDynamicDataSource()!=null){
				dynamicDialect = new DynamicDialect();
				dynamicDialect.setTargetDialect(loadDialect(dynamicDataSource.getTargetDataSources()));
				dynamicDialect.setDefaultDialect(loadDialect((DataSource)dynamicDataSource.getDefaultTargetDataSource()));
			}else{
				throw new MiniDaoException("You have not config dynamicDataSource or dynamicDialect...");
			}
		return dynamicDialect;
	}

	public void setDynamicDialect(DynamicDialect dynamicDialect) {
		this.dynamicDialect = dynamicDialect;
	}
	
	/**
	 * 获取方言
	 * @param datasources
	 * @return
	 */
	public Map<Object,Dialect> loadDialect(Map<Object, Object> datasources){
		Map<Object,Dialect> dialects = new HashMap<Object,Dialect>();
		for(Entry<Object, Object> entry : datasources.entrySet()){
			dialects.put(entry.getKey(), loadDialect(loadDialect((DataSource)entry.getValue())));
		}
		return dialects;
	}
    
    /**
	 * 获取方言
	 * @param datasource
	 * @return
	 */
	private Dialect loadDialect(DataSource datasource){
		Dialect dialect = null;
		try {
			dialect = DialectFactory.getDialect(datasource.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loadDialect(dialect);
	}
	
	/**
	 * 获取方言
	 * @param dialect
	 * @return
	 */
	private Dialect loadDialect(Dialect dialect){
		if(dialect instanceof OracleDialect){
			return new OracleDialect();
		}else if(dialect instanceof SqlServerDialect){
			return new SqlServerDialect();
		}else if(dialect instanceof  MysqlDialect){
			return new MysqlDialect();
		}else if(dialect instanceof  H2Dialect){
			return new H2Dialect();
		}
		return null;
	}
	
}
