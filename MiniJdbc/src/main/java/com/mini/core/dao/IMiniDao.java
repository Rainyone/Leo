package com.mini.core.dao;

import java.util.List;

import com.mini.core.BaseEntity;
import com.mini.core.Model;
import com.mini.core.PageResult;

/**
 * 通用Dao对象接口
 * 
 * @author sxjun 2016-1-28
 */
public interface IMiniDao
{
    /**
     * 插入数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int insert(T record);

    /**
     * 删除数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int delete(T record);
    
    /**
     * 删除数据
     * @param clazz BaseEntity或Record对象 <必须继承Record>
     * @param primaryKey 主键
     * @return int
     */
    public int deleteById(Class<? extends BaseEntity> clazz, Object primaryKey);
    
    /**
     * 更新数据
     * @param record BaseEntity或Record对象 <必须继承Record>
     * @return int
     */
    public <T extends Model> int update(T record);
    
    /**
     * 执行sql语句
     * @param sql 执行语句
     * @param paras 参数
     * @return int
     */
    public int execute(String sql, Object... paras);
    
    /**
     * 根据ID查找单个实体
     * @param clazz  类<必须继承BaseEntity>
     * @param primaryKey 主键
     * @return T extends BaseEntity
     */
    public <T> T findById(Class<? extends BaseEntity> clazz, Object primaryKey);
    
    /**
     * 查找单条记录
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型);返回一个数组;String.class;Integer.class;Long.class]
     * @param args 参数值数组
     * @return T {String、Integer、Long、Record、BaseEntity等}
     */
    public <T> T find(String sql, Class clazz, Object... args);
    
    
    /**
     * 查找单条记录并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型);返回一个数组;String.class;Integer.class;Long.class]
     * @param args 参数值数组
     * @return T {String、Integer、Long、Record、BaseEntity等}
     */
    public <T> T cacheFind(String cacheName, Object key, String sql, Class clazz, Object... args);

    /**
     * 查找一个list
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> findList(String sql, Class clazz, Object... args);
    
    /**
     * 查找一个list并缓存
     * @param cacheName 缓存名称
     * @param key 缓存键
     * @param sql 查询语句
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> cacheFindList(String cacheName, Object key, String sql, Class clazz, Object... args);
    
    /**
     * 分页查找一个list
     * @param sql 查询语句
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param clazz 可以是[Record.class(弱类型);BaseEntity.class(强类型)]
     * @param args 参数值数组
     * @return T extends BaseEntity
     */
    public <T> List<T> paginate(String sql,int pageNumber, int pageSize, Class clazz, Object... args);
    
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
    public <T> List<T> cachePaginate(String cacheName, Object key, String sql,int pageNumber, int pageSize, Class clazz, Object... args);

    /**
     * 获取分页Record数据
     * @param pageNumber 记录行的偏移量
     * @param pageSize 记录行的最大数目
     * @param sql 执行语句
     * @param args 参数
     * @return PageResult<Record>
     */
    public <T> PageResult<T> paginateResult(String sql, int pageNumber, int pageSize, Class clazz, Object... args);
    
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
    public <T> PageResult<T> cachePaginateResult(String cacheName, Object key, String sql, int pageNumber, int pageSize, Class clazz, Object... args);
}
