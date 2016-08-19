package com.mini.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.IsInstanceOf;
import org.springframework.jdbc.core.RowMapper;

import com.mini.core.annotation.Column;
import com.mini.core.utils.MiniUtil;
import com.mini.core.utils.StrKit;
import com.mini.example.bean.Demo;

/** 
 * Entity属性字段名映射类
 * 
 * @author sxjun
 * @date 2015-6-22 下午05:21:04 
 *
 */
public class BaseEntityMapper {
	private static final Map<String, RowMapper<?>> rowMapping = new HashMap<String, RowMapper<?>>();
	private static Map<String, String[]> primaryKey = new HashMap<String, String[]>();;
	private static Map<String, Map<String, String>> columnMapper = new HashMap<String, Map<String, String>>();
	private static Map<String, Map<String, String>> propertyMapper = new HashMap<String, Map<String, String>>();
	
	/**
	 * 初始化列对应关系
	 * @param className
	 */
	private static void initMapper(String className){
		Map<String, String> columnMapping = new HashMap<String, String>();
		Map<String, String> propertyMapping = new HashMap<String, String>();
		try {
			Method[] methods = Class.forName(className).getDeclaredMethods();
	        for (Method method : methods) {
	        	Annotation[] annotations= method.getAnnotations();
	        	for(Annotation an : annotations){
	        		if(an instanceof Column){
		        		String columnName = ((Column)an).value();
		        		String propertyName = StrKit.firstCharToLowerCase(method.getName().substring(3));
		        		columnMapping.put(columnName, propertyName);
		            	propertyMapping.put(propertyName, columnName);
	        		}
	        	}
	        }
	        columnMapper.put(className, columnMapping);
			propertyMapper.put(className, propertyMapping);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化强类型实体的映射关系
	 * @param entityInfo
	 */
	public static void initEntity(EntityInfo entityInfo) {
		String className = entityInfo.getModelClass().getName();
		rowMapping.put(className, entityInfo.getRowMapper());
		initMapper(className);
	}
	
	/**
	 * 获取RowMapper实例
	 * 
	 * @param clazz
	 * @return
	 */
	public static RowMapper<?> getRowMapper(Class<?> clazz) {
		RowMapper<?> rm = rowMapping.get(clazz.getName());
		if(rm == null){
			if (MiniUtil.isStrongEntity(clazz)) {
				rm = StrongEntityRowMapper.newInstance(clazz);
				rowMapping.put(clazz.getName(), rm);
				initMapper(clazz.getName());
			}else if (MiniUtil.isWeakEntity(clazz)) {
				rm = WeakEntityRowMapper.newInstance(clazz);
				rowMapping.put(clazz.getName(), rm);
				initMapper(clazz.getName());
			}else if (MiniUtil.isRecord(clazz)){
				rm = RecordRowMapper.newInstance(clazz);
			}
		}
		return rm;
	}
	
	/**
	 * 获取row mapper
	 *
	 * @param entity
	 * @return
	 */
	public static RowMapper<?> getRowMapper(WeakEntity entity) {
		return getRowMapper(entity.getClass());
	}
	
	/**
	 * 获取主键
	 * @param entity
	 * @return
	 */
	public static String[] getPrimaryKey(WeakEntity entity) {
		return primaryKey.get(entity.getClass().getName());
	}
	
	/**
	 * 获取主键
	 * @param clazz
	 * @return
	 */
	public static String[] getPrimaryKey(Class clazz) {
		return primaryKey.get(clazz.getName());
	}

	/**
	 * 设置主键
	 * @param className
	 * @param _primaryKey
	 */
	private static void setPrimaryKey(String className,String _primaryKey) {
		String[] arr = _primaryKey.split(",");
		for (int i=0; i<arr.length; i++)
			arr[i] = arr[i].trim();
		primaryKey.put(className, arr);
	}
	
	/**
	 * 根据类获取属性
	 * @param className
	 * @return
	 */
	public static Map<String, String> getProperty(String className) {
		return propertyMapper.get(className);
	}
	
	/**
	 * 根据类和数据库字段获取属性
	 * @param clazz
	 * @param columnName
	 * @return
	 */
	public static String getPropertyName(Class clazz , String columnName) {
		String p = columnMapper.get(clazz.getName()).get(columnName);
		return p == null ? columnName : p;
	}
	
	/**
	 * 根据类和数据库字段获取属性
	 * @param className
	 * @param columnName
	 * @return
	 */
	public static String getPropertyName(String className, String columnName) {
		String p = columnMapper.get(className).get(columnName);
		return p == null ? columnName : p;
	}
	
	/**
	 * 根据类和属性获取数据库字段
	 * @param className
	 * @param propertyName
	 * @return
	 */
	public static String getColumnName(String className, String propertyName) {
		String c = propertyMapper.get(className).get(propertyName);
		return c == null ? propertyName : c;
	}

	/**
	 * 是否包含数据库字段判断
	 * @param entity
	 * @param colName
	 * @return
	 */
	public static boolean hasColumnLabel(WeakEntity entity, String colName) {
		if(StrKit.notBlank(columnMapper.get(entity.getClass().getName()).get(colName)))
			return true;
		else
			return false;
	}
}
