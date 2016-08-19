package com.mini.core;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.ReflectionUtils;

import com.mini.core.utils.EnumClazz.StrategyType;
import com.mini.core.utils.MiniUtil;
import com.mini.core.utils.StrKit;

public class ModelDeal {
	/**
	 * 主键设置UUID
	 * @param record
	 */
	public static <T extends Model> void uuid(T record){
		if(MiniUtil.isWeakEntity(record.getClass())){
			for(String key : record.getPrimaryKeys()){
				((Record)record).set(key, UUID.randomUUID().toString());
			}
		}else if(MiniUtil.isStrongEntity(record.getClass())){
			String[] keys = record.getPrimaryKeys();
			for(String key : keys){
				try {
					ReflectionUtils.findMethod(record.getClass(), "set"+StrKit.firstCharToUpperCase(key),String.class).invoke(record,UUID.randomUUID().toString());
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将排除的数组放到set中
	 * @param set
	 * @param ingores
	 */
	public static void ingoreSet(Set<String> set,String[] ingores){
		if(ingores!=null&&ingores.length>0){
			for(String ingore : ingores)
				set.add(ingore);
		}
	}
	
	/**
	 * 删除不需要的字段以及需要更新的字段
	 * @param set
	 * @param record
	 */
	public static <T extends Record> void ignoreRecord(Set<String> set,T record,Map<String,Object> map){
		for(String field : set){
			if(record.containsKey(MiniUtil.caps(field))){
				map.put(field, record.get(field));
				record.remove(field);
				if(record.getModifyFlag().contains(field))
					record.getModifyFlag().remove(field);
			}
		}
	}
	
	/**
	 * 不能为空的字段
	 * @param set
	 * @param record
	 */
	public static <T extends Record> void notNullRecord(String[] not_null_field,T record){
		for(String field : not_null_field)
			if(record.get(field)==null)
				throw new MiniDaoException("数据库表("+record.getTableName()+")的字段("+field+")不能设置为NULL，请检查对应数据或实体约束");
	}
	
	/**
	 * 处理插入操作
	 * @param record
	 * return 返回被忽略的字段和值
	 */
	public static <T extends Model> Map<String,Object> dealInsert(T record){
		if(MiniUtil.isWeakEntity(record.getClass())){
			Map<String,Object> map = new HashMap<String,Object>();
			Set<String> set = new HashSet<String>();
			EntityInfo entityInfo = EntityMapping.me().getEntity(((BaseEntity)record).getClass());
			ingoreSet(set,entityInfo.getIgnore_field());
			ingoreSet(set,entityInfo.getIgnore_insert_field());
			
			if(entityInfo.getStrategy() == StrategyType.NULL){
				String[] keys = entityInfo.getPrimaryKey();
				for(String key : keys){
					if(((Record)record).get(key)==null)
						throw new MiniDaoException("数据库表("+record.getTableName()+")的主键("+key+")不能设置为NULL");
				}
			}else if(entityInfo.getStrategy() == StrategyType.UUID){
				uuid((Record)record);
			}else if(entityInfo.getStrategy() == StrategyType.AUTO){
				String[] keys = entityInfo.getPrimaryKey();
				ingoreSet(set,keys);
			}
			ignoreRecord(set,(Record)record,map);
			notNullRecord(entityInfo.getNot_null_field(),(Record)record);
			return map;
		}else if(MiniUtil.isStrongEntity(record.getClass())){
			EntityInfo entityInfo = EntityMapping.me().getEntity(((BaseEntity)record).getClass());
			if(entityInfo.getStrategy() == StrategyType.NULL){
				String[] keys = entityInfo.getPrimaryKey();
				for(String key : keys){
					try {
						Object _key = ReflectionUtils.findMethod(record.getClass(), "get"+StrKit.firstCharToUpperCase(key)).invoke(null);
						if(_key==null)
							throw new MiniDaoException("数据库表("+record.getTableName()+")的主键("+key+")不能设置为NULL");
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}else if(entityInfo.getStrategy() == StrategyType.UUID){
				uuid(record);
			}
			return null;
		}else
			return null;
	}
	
	/**
	 * 处理更新操作
	 * @param record
	 * return 返回被忽略的字段和值
	 */
	public static <T extends Model> Map<String,Object> dealUpdate(T record){
		if(MiniUtil.isWeakEntity(record.getClass())){
			Map<String,Object> map = new HashMap<String,Object>();
			Set<String> set = new HashSet<String>();
			EntityInfo entityInfo = EntityMapping.me().getEntity(((BaseEntity)record).getClass());
			ingoreSet(set,entityInfo.getIgnore_field());
			ingoreSet(set,entityInfo.getIgnore_update_field());
			ignoreRecord(set,(Record)record,map);
			notNullRecord(entityInfo.getNot_null_field(),(Record)record);
			Set<String> modifyFlag = ((Record)record).getModifyFlag();
			if(modifyFlag.isEmpty()){
				for(Map.Entry<String, Object> entry :((Record)record).entrySet())
					modifyFlag.add(entry.getKey());
			}
			return map;
		}else 
			return null;
	}
	
	/**
	 * 将忽略的值恢复到record对象上
	 * @param map
	 * @param record
	 */
	public static <T extends Model> void completeRecord(Map<String,Object> map,T record){
		if(map!=null&&!map.isEmpty()){
			if(MiniUtil.isWeakEntity(record.getClass())){
				for(Entry<String, Object> entry : map.entrySet()){
					((Record)record).set(entry.getKey(), entry.getValue());
				}
			}
		}
	}
}
