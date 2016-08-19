package com.mini.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mini.core.utils.EnumClazz.StrategyType;

/**
 * 表注解
 * 
 * @author sxjun
 * @date 2014-11-14 下午09:46:37 
 *
 */
@Documented 
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.TYPE) 
public @interface Entity {

	/**
	 * 设置表名
	 * @return
	 */
	String table();
	
	/**
	 * 设置主键字段,联合主键用逗号分隔
	 * @return
	 */
	String[] id();
	
	/**
	 * 主键的生成策略
	 * StrategyType.NULL 默认需要手动设置主键值
	 * StrategyType.AUTO 主键值自动增长
	 * StrategyType.UUID 自动生成system.uuid作为主键
	 * @return
	 */
	StrategyType strategy() default StrategyType.NULL;
	
	/**
	 * 插入、修改都要排除的字段
	 * @return
	 */
	String[] ignore_field() default {};
	
	/**
	 * 插入时要排除的字段
	 * @return
	 */
	String[] ignore_insert_field() default {};
	
	/**
	 * 更新时要排除的字段
	 * @return
	 */
	String[] ignore_update_field() default {};
	
	/**
	 * 不许为Null的字段
	 * @return
	 */
	String[] not_null_field() default {};
}
