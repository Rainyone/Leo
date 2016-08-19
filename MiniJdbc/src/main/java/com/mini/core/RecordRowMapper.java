package com.mini.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 完全拷贝RecordRowMapper，修改mapRow方法
 * 有个前提，所有的Bean必须继承BaseEntity
 * 
 * @author sxjun
 * @date 2015-12-29 下午10:00:39 
 *
 */
public class RecordRowMapper implements RowMapper<Record> {

	/**
	 * Record 的映射处理
	 */
	public Record mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
		
		int columns = meta.getColumnCount();
		
		Record record = new Record();
		for(int i=1;i<=columns;i++)
		{
		    record.set(meta.getColumnName(i), rs.getObject(meta.getColumnName(i)));
		}
        return record;
	}

	/**
	 * 初始化Record的映射
	 * @param clazz
	 * @return
	 */
	public static RowMapper<?> newInstance(Class<?> clazz) {
		RecordRowMapper newInstance = new RecordRowMapper();
		return newInstance;
	}

}
