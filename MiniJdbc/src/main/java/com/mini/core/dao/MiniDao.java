package com.mini.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.mini.core.BaseEntity;
import com.mini.core.MiniDaoException;
import com.mini.core.Model;
import com.mini.core.PageResult;
import com.mini.core.Record;
import com.mini.core.cfg.MiniConfig;
import com.mini.core.utils.MiniUtil;

/**
 * 通用Dao对象
 * @author sxjun
 * 2016-1-15
 */
public class MiniDao implements IMiniDao
{
	private Logger logger = Logger.getLogger(MiniDao.class);
	@Autowired
	private MiniConfig miniConfig;
	/*private miniConfig.getDbHelper() miniConfig.getDbHelper();
    
    private DynamicDataSource dynamicDataSource;
    
    public void setDynamicDataSource(DynamicDataSource dynamicDataSource) {
		this.dynamicDataSource = dynamicDataSource;
	}

	public void init(){
		new EhCacheHelper().start();
    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dynamicDataSource);
        
        DynamicDialect dynamicDialect = new DynamicDialect();
		dynamicDialect.setTargetDialect(loadDialect(dynamicDataSource.getTargetDataSources()));
		dynamicDialect.setDefaultDialect(loadDialect((DataSource)dynamicDataSource.getDefaultTargetDataSource()));
        
    	miniConfig.getDbHelper() = new miniConfig.getDbHelper()();
    	miniConfig.getDbHelper().setJdbcTemplate(jdbcTemplate);
		miniConfig.getDbHelper().setDialect(dynamicDialect);
    }
	
	public void init(JdbcTemplate jdbcTemplate,DynamicDialect dynamicDialect){
		new EhCacheHelper().start();
    	miniConfig.getDbHelper() = new miniConfig.getDbHelper()();
    	miniConfig.getDbHelper().setJdbcTemplate(jdbcTemplate);
		miniConfig.getDbHelper().setDialect(dynamicDialect);
    }
    
    public void setminiConfig.getDbHelper()(miniConfig.getDbHelper() miniConfig.getDbHelper()) {
        this.miniConfig.getDbHelper() = miniConfig.getDbHelper();
    }*/
    
    public void setMiniConfig(MiniConfig miniConfig) {
		this.miniConfig = miniConfig;
	}

	/**
     * 插入数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int insert(T record){
    	logger.info("MiniDao--insert:" + record);
    	return miniConfig.getDbHelper().save(record);
    }

    /**
     * 删除数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int delete(T record){
    	logger.info("MiniDao--delete:" + record);
    	return miniConfig.getDbHelper().delete(record);
    }
    
    /**
     * 删除数据
     * @param clazz BaseEntity或Record对象 <必须继承Record>
     * @param primaryKey 主键
     * @return int
     */
    public int deleteById(Class<? extends BaseEntity> clazz, Object primaryKey){
    	logger.info("MiniDao--deleteById:" + clazz.getName() +";primaryKey:" + primaryKey);
    	return miniConfig.getDbHelper().deleteById(clazz, primaryKey);
    }
    
    /**
     * 更新数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int update(T record){
    	logger.info("MiniDao--update:" + record );
    	return miniConfig.getDbHelper().update(record);
    }
    
    /**
     * 执行sql语句
     * @param sql 执行语句
     * @param paras 参数
     * @return int
     */
    public int execute(String sql, Object... paras){
    	logger.info("MiniDao--execute:" + sql +";paras:" + paras);
    	return miniConfig.getDbHelper().update(sql, paras);
    }
    
    /**
     * 根据ID查找单个实体
     * @param clazz  类<必须继承BaseEntity>
     * @param primaryKey 主键
     * @return T extends BaseEntity
     */
    @SuppressWarnings("unchecked")
	public <T> T findById(Class<? extends BaseEntity> clazz, Object primaryKey){
    	logger.info("MiniDao--findById:" + clazz.getName() +";primaryKey:" + primaryKey);
    	return (T) miniConfig.getDbHelper().findById(clazz, primaryKey);
    }
    
    /**
     * 查找单条记录
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)，或基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数值数组
     * @return T {String、Integer、Long、Record、BaseEntity等}
     */
    @SuppressWarnings("unchecked")
	public <T> T find(String sql, Class clazz, Object... args){
    	logger.info("MiniDao--find:" + sql +";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz))
    		return (T) miniConfig.getDbHelper().queryForObject(sql,clazz,args);
    	else if(MiniUtil.isBaseEntity(clazz))
    		return (T) miniConfig.getDbHelper().findFirst(sql,clazz,args);
    	else if(MiniUtil.isRecord(clazz))
    		return (T) miniConfig.getDbHelper().findFirst(sql,args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    }
    
    /**
     * 查找单条记录并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型);返回一个数组;String.class;Integer.class;Long.class]
     * @param args 参数值数组
     * @return T {String、Integer、Long、Record、BaseEntity等}
     */
    public <T> T cacheFind(String cacheName, Object key, String sql, Class clazz, Object... args){
    	logger.info("MiniDao--cacheFind:" + cacheName +";key:" + key + ";sql:" + sql + ";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz))
    		return (T) miniConfig.getDbHelper().queryForObjectByCache(cacheName, key, sql,clazz,args);
    	else if(MiniUtil.isBaseEntity(clazz))
    		return (T) miniConfig.getDbHelper().findFirstByCache(cacheName, key, sql,clazz,args);
    	else if(MiniUtil.isRecord(clazz))
    		return (T) miniConfig.getDbHelper().findFirstByCache(cacheName, key, sql,args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    }

    /**
     * 查找一个list
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)] 或 基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> findList(String sql, Class clazz, Object... args){
    	logger.info("MiniDao--findList:" + sql + ";clazz:" + clazz.getName() + ";args:"+ args);
    	if(MiniUtil.isPrimitiveClass(clazz))
    		return miniConfig.getDbHelper().queryForList(sql, clazz, args);
    	else if(MiniUtil.isBaseEntity(clazz))
    		return miniConfig.getDbHelper().findEntity(sql,clazz,args);
    	else  if(MiniUtil.isRecord(clazz))
    		return (List<T>) miniConfig.getDbHelper().find(sql, args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    }
    
    /**
     * 查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> cacheFindList(String cacheName, Object key, String sql, Class clazz, Object... args){
    	logger.info("MiniDao--cacheFindList:" + cacheName +";key:" + key + ";sql:" + sql + ";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz))
    		return miniConfig.getDbHelper().queryForListByCache(cacheName, key,sql, clazz, args);
    	else if(MiniUtil.isBaseEntity(clazz))
    		return miniConfig.getDbHelper().findEntityByCache(cacheName, key,sql,clazz,args);
    	else  if(MiniUtil.isRecord(clazz))
    		return (List<T>) miniConfig.getDbHelper().findByCache(cacheName, key,sql, args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    }
    
    /**
     * 分页查找一个list
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]，或基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> paginate(String sql,int pageNumber, int pageSize, Class clazz, Object... args){
    	logger.info("MiniDao--paginate:" + sql +";pageNumber:" + pageNumber + ";pageSize:" + pageSize + ";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz)){
    		List<T> list = new ArrayList<T>();
    		List<Record> records = miniConfig.getDbHelper().paginateRecord(pageNumber, pageSize, sql, args);
    		for(Record record : records){
    			Object[] o = record.getColumnValues();
    			list.add((T)o[0]);
    			break;
    		}
    		return list;
    	}else if(MiniUtil.isBaseEntity(clazz))
    		return miniConfig.getDbHelper().paginateEntity(pageNumber, pageSize, clazz, sql, args);
    	else  if(MiniUtil.isRecord(clazz))
    		return (List<T>) miniConfig.getDbHelper().paginateRecord(pageNumber, pageSize, sql, args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    }
    
    /**
     * 分页查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> cachePaginate(String cacheName, Object key, String sql,int pageNumber, int pageSize, Class clazz, Object... args){
    	logger.info("MiniDao--cachePaginate:" + cacheName +";key:" + key + ";sql:" + sql + ";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz)){
    		List<T> list = new ArrayList<T>();
    		List<Record> records = miniConfig.getDbHelper().paginateRecordByCache(cacheName, key, pageNumber, pageSize, sql, args);
    		for(Record record : records){
    			Object[] o = record.getColumnValues();
    			list.add((T)o[0]);
    			break;
    		}
    		return list;
    	}else if(MiniUtil.isBaseEntity(clazz))
    		return miniConfig.getDbHelper().paginateEntityByCache(cacheName, key, pageNumber, pageSize, clazz, sql, args);
    	else  if(MiniUtil.isRecord(clazz))
    		return (List<T>) miniConfig.getDbHelper().paginateRecordByCache(cacheName, key, pageNumber, pageSize, sql, args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    
    }
    
    /**
     * 获取分页Record数据
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]，或基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]
     * @param args 参数
     * @return PageResult<Record>
     */
    @SuppressWarnings("unchecked")
	public <T> PageResult<T> paginateResult(String sql, int pageNumber, int pageSize, Class clazz, Object... args){
    	logger.info("MiniDao--paginateResult:" + sql +";pageNumber:" + pageNumber + ";pageSize:" + pageSize + ";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz)){
    		PageResult<Record> records = miniConfig.getDbHelper().paginate(pageNumber, pageSize, sql, args);
    		List<T> list = new ArrayList<T>();
    		for(Record record : records.getResults()){
    			Object[] o = record.getColumnValues();
    			list.add((T)o[0]);
    			break;
    		}
    		PageResult<T> reds = new PageResult<T>(list, pageNumber, pageSize, Integer.parseInt(String.valueOf(records.getResultCount())));
    		return reds;
    	}else if(MiniUtil.isBaseEntity(clazz))
    		return (PageResult<T>) miniConfig.getDbHelper().paginate(pageNumber, pageSize, sql, clazz, args);
    	else  if(MiniUtil.isRecord(clazz))
    		return (PageResult<T>) miniConfig.getDbHelper().paginate(pageNumber, pageSize, sql, args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    }
    
    /**
     * 获取分页Record数据并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param args 参数
     * @return PageResult<Record>
     */
    public <T> PageResult<T> cachePaginateResult(String cacheName, Object key, String sql, int pageNumber, int pageSize, Class clazz, Object... args){
    	logger.info("MiniDao--cachePaginateResult:" + cacheName +";key:" + key + ";sql:" + sql + ";clazz:" + clazz.getName() + ";args:" + args);
    	if(MiniUtil.isPrimitiveClass(clazz)){
    		PageResult<Record> records = miniConfig.getDbHelper().paginateByCache(cacheName, key, pageNumber, pageSize, sql, args);
    		List<T> list = new ArrayList<T>();
    		for(Record record : records.getResults()){
    			Object[] o = record.getColumnValues();
    			list.add((T)o[0]);
    			break;
    		}
    		PageResult<T> reds = new PageResult<T>(list, pageNumber, pageSize, Integer.parseInt(String.valueOf(records.getResultCount())));
    		return reds;
    	}else if(MiniUtil.isBaseEntity(clazz))
    		return (PageResult<T>) miniConfig.getDbHelper().paginateByCache(cacheName, key, pageNumber, pageSize, sql, clazz, args);
    	else  if(MiniUtil.isRecord(clazz))
    		return (PageResult<T>) miniConfig.getDbHelper().paginateByCache(cacheName, key, pageNumber, pageSize, sql, args);
    	else
    		throw new MiniDaoException("只支持基本类型[String、Integer(int)、Long(long)、Char(char)、Double(double)、Float(float)、Short(short)、Boolean(boolean)、Byte(byte)]和以[Record.class(弱类型);BaseEntity.class(强类型)]为基类的对象转换;不支持("+clazz.getSimpleName()+")类型的转换。");
    
    }
}
