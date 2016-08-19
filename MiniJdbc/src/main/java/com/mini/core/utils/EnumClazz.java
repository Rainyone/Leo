package com.mini.core.utils;
public class EnumClazz{
	/**
	 * 主键的生成策略
	 * StrategyType.NULL 默认需要手动设置主键值
	 * StrategyType.AUTO 主键值自动增长
	 * StrategyType.UUID 自动生成system.uuid作为主键
	 * @return
	 * @author sxjun
	 * 2016-1-26
	 */
	public enum StrategyType {
		NULL ,AUTO, UUID
	}
	
	/**
	 * 字段的大小写
	 * SENSITIVE 大小写敏感
	 * UPPER 大写
	 * LOWER 小写
	 * @return
	 * @author sxjun
	 * 2016-1-26
	 */
	public enum Caps {
		SENSITIVE, UPPER ,LOWER
	}
}
