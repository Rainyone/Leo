package com.mini.core;

import org.springframework.jdbc.core.RowMapper;

import com.mini.core.utils.EnumClazz.StrategyType;
import com.mini.core.utils.StrKit;

/**
 * @author sxjun
 * 2016-2-4
 * Table save the table meta info like column name and column type.
 */
public class EntityInfo {
	
	/**
	 * 数据库表名
	 */
	private String name;
	
	/**
	 * 主键的生成策略
	 */
	private StrategyType strategy;
	
	/**
	 * 忽略字段
	 */
	private String[] ignore_field;
	
	/**
	 * 插入时忽略字段
	 */
	private String[] ignore_insert_field;
	
	/**
	 * 更新时忽略字段
	 */
	private String[] ignore_update_field;
	
	/**
	 * 不许为空字段
	 */
	private String[] not_null_field;
	
	/**
	 * 主键字段
	 */
	private String[] primaryKey = null;
	
	/**
	 * 实体类
	 */
	private Class<? extends WeakEntity> modelClass;
	
	/**
	 * 字段映射
	 */
	private RowMapper<?> rowMapper;
	
	/**
	 * 初始化EntityInfo
	 * @param name
	 * @param modelClass
	 */
	public EntityInfo(String name, Class<? extends WeakEntity> modelClass) {
		if (StrKit.isBlank(name))
			throw new IllegalArgumentException("Table name can not be blank.");
		if (modelClass == null)
			throw new IllegalArgumentException("Model class can not be null.");
		
		this.name = name.trim();
		this.modelClass = modelClass;
	}
	
	/**
	 * 初始化EntityInfo
	 * @param name
	 * @param primaryKey
	 * @param modelClass
	 */
	public EntityInfo(String name, String[] primaryKey, Class<? extends WeakEntity> modelClass) {
		if (StrKit.isBlank(name))
			throw new IllegalArgumentException("Table name can not be blank.");
		if (primaryKey==null||primaryKey.length==0)
			throw new IllegalArgumentException("Primary key can not be blank.");
		if (modelClass == null)
			throw new IllegalArgumentException("Model class can not be null.");
		
		this.name = name.trim();
		this.primaryKey = primaryKey;
		this.modelClass = modelClass;
	}
	
	/**
	 * 初始化EntityInfo
	 * @param name
	 * @param primaryKey
	 * @param strategy
	 * @param ignore_field
	 * @param ignore_insert_field
	 * @param ignore_update_field
	 * @param not_null_field
	 * @param modelClass
	 */
	public EntityInfo(String name,
			String[] primaryKey,
			StrategyType strategy,
			String[] ignore_field,
			String[] ignore_insert_field,
			String[] ignore_update_field,
			String[] not_null_field,
			Class<? extends WeakEntity> modelClass) {
		if (StrKit.isBlank(name))
			throw new IllegalArgumentException("Table name can not be blank.");
		if (primaryKey==null||primaryKey.length==0)
			throw new IllegalArgumentException("Primary key can not be blank.");
		if (modelClass == null)
			throw new IllegalArgumentException("Model class can not be null.");
		
		this.name = name.trim();
		this.primaryKey = primaryKey;
		this.modelClass = modelClass;
		
		this.strategy = strategy;
		this.ignore_field = ignore_field;
		this.ignore_insert_field = ignore_insert_field;
		this.ignore_update_field = ignore_update_field;
		this.not_null_field = not_null_field;
	}
	
	/**
	 * 获取数据库表名
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 获取主键
	 */
	public String[] getPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * 获取主键生成策略
	 * @return
	 */
	public StrategyType getStrategy() {
		return strategy;
	}

	/**
	 * 设置主键生成策略
	 * @param strategy
	 */
	public void setStrategy(StrategyType strategy) {
		this.strategy = strategy;
	}

	/**
	 * 获取模型类
	 * @return
	 */
	public Class<? extends WeakEntity> getModelClass() {
		return modelClass;
	}

	/**
	 * 获取忽略字段
	 * @return
	 */
	public String[] getIgnore_field() {
		return ignore_field;
	}

	/**
	 * 设置忽略字段
	 * @param ignore_field
	 */
	public void setIgnore_field(String[] ignore_field) {
		this.ignore_field = ignore_field;
	}

	/**
	 * 获取插入时的忽略字段
	 * @return
	 */
	public String[] getIgnore_insert_field() {
		return ignore_insert_field;
	}

	/**
	 * 设置插入时的忽略字段
	 * @param ignore_insert_field
	 */
	public void setIgnore_insert_field(String[] ignore_insert_field) {
		this.ignore_insert_field = ignore_insert_field;
	}

	/**
	 * 获取更新时的忽略字段
	 * @return
	 */
	public String[] getIgnore_update_field() {
		return ignore_update_field;
	}

	/**
	 * 设置更新时的忽略字段
	 * @param ignore_update_field
	 */
	public void setIgnore_update_field(String[] ignore_update_field) {
		this.ignore_update_field = ignore_update_field;
	}

	/**
	 * 获取不能为空的字段
	 * @return
	 */
	public String[] getNot_null_field() {
		return not_null_field;
	}

	/**
	 * 设置不能为空的字段
	 * @param not_null_field
	 */
	public void setNot_null_field(String[] not_null_field) {
		this.not_null_field = not_null_field;
	}
	
	/**
	 * 获取行映射关系
	 * @return
	 */
	public RowMapper<?> getRowMapper() {
		return rowMapper;
	}
	
	/**
	 * 设置行映射关系
	 * @param rowMapper
	 */
	public void setRowMapper(RowMapper<?> rowMapper) {
		this.rowMapper = rowMapper;
	}
	
}





