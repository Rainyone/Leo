package com.mini.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * 字段注解
 * 
 * @author sxjun
 * @date 2015-6-22 下午05:59:20 
 *
 */
@Documented 
@Retention(RetentionPolicy.RUNTIME) 
@Target(value = {ElementType.METHOD})
public @interface Column {
	
	String value();
}
