package com.mini.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.mini.core.EntityInfo;
import com.mini.core.StrongEntity;
import com.mini.core.WeakEntity;
import com.mini.core.annotation.Column;
import com.mini.core.annotation.Entity;

/**
 * 注解相关工具类
 * 
 * @author sxjun
 * @date 2014-7-10 下午5:50:16
 * 
 */
public class AnnotationUtil {
	
	public static String getColumnFieldName(Class clazz,Method method){
		try {
			Method[] methods = clazz.getMethods();
			for(Method m : methods){
				if(m.getName().equals(method.getName())){
					Annotation[] annos = m.getAnnotations();
					boolean findit = false;
					if(annos!=null && annos.length>0)
						for(Annotation anno :annos){
				    		if(anno instanceof Column){
				    			findit = true;
				    			break;
				    		}
				    	}
					if(!findit || annos==null || annos.length==0)
						annos = clazz.getMethod("get"+method.getName().substring(3)).getAnnotations();
					
			    	for(Annotation anno :annos){
			    		if(anno instanceof Column){
			    			return ((Column)anno).value();
			    		}
			    	}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有注解信息
	 * 
	 * @param className
	 * @return
	 */
	public static Annotation[] getAllAnnotaions(String className) {
		try {
			Annotation annos[] = Class.forName(className).getAnnotations();
			return annos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取所有注解信息
	 * 
	 * @param clazz
	 * @return
	 */
	public static Annotation[] getAllAnnotaions(Class<?> clazz) {
		try {
			Annotation annos[] = clazz.getAnnotations();
			return annos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断是否是实体类，必须都包含Table，Database注解，Entity专用注解，其他类尽量不要使用
	 * 如果是实体类，会设置table和database属性，并返回改EntityInfo实例
	 * 
	 * @param annotaions
	 * @return
	 */
	public static EntityInfo isEntity(Annotation[] annotations,Class<?> clazz) {
		boolean hasTableAnnotation = false;
		EntityInfo i = null;
		if (annotations != null && annotations.length > 0) {
			for (Annotation a : annotations) {
				if (a instanceof Entity) {
					hasTableAnnotation = true;
					i = new EntityInfo(((Entity) a).table(),
							((Entity) a).id(), ((Entity) a).strategy(),
							((Entity) a).ignore_field(),
							((Entity) a).ignore_insert_field(),
							((Entity) a).ignore_update_field(),
							((Entity) a).not_null_field(),
							(Class<? extends WeakEntity>) clazz);
				}
			}
		}
		if (hasTableAnnotation) {
			return i;
		} else {
			return null;
		}
	}

	/**
	 * 判断是否有Column字段
	 * @param annotations
	 * @param clazz
	 * @return
	 */
	public static boolean hasColumns(Annotation[] annotations, Class<?> clazz) {
		boolean hasColumnAnnotation = false;
		if (annotations != null && annotations.length > 0) {
			for (Annotation a : annotations) {
				if (a instanceof Entity) {
					hasColumnAnnotation = true;
					break;
				}
			}
		}
		return hasColumnAnnotation;
	}
}
