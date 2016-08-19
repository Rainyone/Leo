package com.mini.core;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.mini.core.utils.AnnotationUtil;

/**
 * @author sxjun
 * TableMapping save the mapping between model class and table.
 */
public class EntityMapping {
	
	private final Map<Class<? extends WeakEntity>, EntityInfo> modelToTableMap = new HashMap<Class<? extends WeakEntity>, EntityInfo>();
	
	private static EntityMapping me = new EntityMapping(); 
	
	private EntityMapping() {}
	
	public static EntityMapping me() {
		return me;
	}
	
	/**
	 * 设置实体类
	 * @param table
	 */
	public void putEntity(EntityInfo table) {
		modelToTableMap.put(table.getModelClass(), table);
	}
	
	/**
	 * 获取社体类信息
	 * @param modelClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public EntityInfo getEntity(Class<?> modelClass) {
		EntityInfo entityInfo = modelToTableMap.get(modelClass);
		if (entityInfo == null){
			Annotation[] annotations = modelClass.getAnnotations();
			if (annotations != null && annotations.length >= 1) {
				entityInfo = AnnotationUtil.isEntity(annotations,modelClass);
				if(AnnotationUtil.hasColumns(annotations,modelClass)){
					if(StrongEntity.class.isInstance(modelClass))
						entityInfo.setRowMapper(StrongEntityRowMapper.newInstance(modelClass));
					else if(WeakEntity.class.isAssignableFrom(modelClass))
						entityInfo.setRowMapper(WeakEntityRowMapper.newInstance(modelClass));
				}
				putEntity(entityInfo);
				//if(MiniUtil.isStrongEntity(modelClass))
				BaseEntityMapper.initEntity(entityInfo);
			}
		}else if (entityInfo == null)
			throw new RuntimeException("The Table mapping of model: " + modelClass.getName() + " not exists. Please add annotation Entity to you class.");
		return entityInfo;
	}
}


