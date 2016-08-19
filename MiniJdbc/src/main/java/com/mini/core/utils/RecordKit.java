package com.mini.core.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mini.core.BaseEntity;
import com.mini.core.BaseEntityMapper;
import com.mini.core.EntityInfo;
import com.mini.core.EntityMapping;
import com.mini.core.MiniDaoException;
import com.mini.core.Record;
import com.mini.core.StrongEntity;

/**
 * RecordKit
 */
@SuppressWarnings("rawtypes")
public final class RecordKit {
	/** 
     * 循环向上转型, 获取对象的 DeclaredField 
     * @param object : 子类对象 
     * @param fieldName : 父类中的属性名 
     * @return 父类中的属性对象 
     */  
    static Field getDeclaredField(Object object, String fieldName){  
        Field field = null ;  
          
        Class<?> clazz = object.getClass() ;  
          
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {  
            try {  
                field = clazz.getDeclaredField(fieldName) ;  
                return field ;  
            } catch (Exception e) {  
            }   
        }  
        return null;  
    } 
    
	public static <T> Record convert2Record(T o){
		if(MiniUtil.isStrongEntity(o.getClass())){
			Record record = null;
			try {
				BaseEntity be = MiniUtil.getStrongClazz(o.getClass());
				EntityInfo entityInfo = EntityMapping.me().getEntity(be.getClass());
				record = new Record();
				record.setPrimaryKeys(entityInfo.getPrimaryKey());
				record.setTableName(entityInfo.getName());
				Map<String,String> propertyMapper = BaseEntityMapper.getProperty(be.getClass().getName());


				/*Map<String,Object> map = new HashMap<String,Object>();
				Set<String> set = new HashSet<String>();
				EntityInfo entityInfo = EntityMapping.me().getEntity(MiniUtil.getStrongClazz(record.getClass()).getClass());
				ingoreSet(set,entityInfo.getIgnore_field());
				ingoreSet(set,entityInfo.getIgnore_update_field());
				// TODO 后续增加该方法
				//notNullRecord(entityInfo.getNot_null_field(),(Record)record);*/
				
				EntityInfo ei = EntityMapping.me().getEntity(MiniUtil.getStrongClazz(o.getClass()).getClass());
				
				Set<String> modifyFlag = ((StrongEntity)o).getModifyFlag();
				boolean all = false;
				if(((StrongEntity)o).getModifyFlag().isEmpty())
					all = true;
	        	for(Map.Entry<String, String> entry : propertyMapper.entrySet()) {
	        		String propertyName = entry.getKey();
	        		String columnName = entry.getValue();
	        		String[] keys = ei.getPrimaryKey();
	        		boolean findit = false;
	        		for(String key : keys)
	        			if(key.equals(propertyName)){
	        				findit = true;
	        				break;
	        			}
	        		if(modifyFlag.contains(propertyName) || findit || all){
	        			Field field = getDeclaredField(o, propertyName);
		        		if(field!=null){
		        			field.setAccessible(true) ; 
					        Object val = field.get(o); 
					        record.set(columnName, val);
		        		}
	        		}
	            }
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
	        return record;
		}else
			return (Record) o;
	}
	
	public static <T> Map<String,Object> convert2Map(T o){
		Map<String,Object> record = new HashMap<String,Object>();
		Map<String,String> propertyMapper = BaseEntityMapper.getProperty(o.getClass().getName());
        try {
        	for(Map.Entry<String, String> entry : propertyMapper.entrySet()) {
        		String propertyName = entry.getKey();
        		String columnName = entry.getValue();
        		
        		Field field = getDeclaredField(o, propertyName);
        		if(field!=null){
        			field.setAccessible(true) ; 
			        Object val = field.get(o); 
			        record.put(columnName, val);
        		}
            }
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	    return record;
	}
	
}




