package com.mini.core.dialect;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mini.core.EntityInfo;
import com.mini.core.PageResult;
import com.mini.core.Record;

/**
 * Dialect.
 */
public abstract class Dialect {
	public abstract String forTableBuilderDoBuild(String tableName);
	public abstract void forModelSave(EntityInfo table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras);
	public abstract String forModelDeleteById(EntityInfo table);
	public abstract void forModelUpdate(EntityInfo table, Map<String, Object> attrs, Set<String> modifyFlag, StringBuilder sql, List<Object> paras);
	public abstract String forModelFindById(EntityInfo table, String columns);
	public abstract String forDbFindById(String tableName, String[] pKeys);
	public abstract String forDbDeleteById(String tableName, String[] pKeys);
	public abstract void forDbSave(StringBuilder sql, List<Object> paras, String tableName, String[] pKeys, Record record);
	public abstract void forDbUpdate(String tableName, String[] pKeys, Object[] ids, Record record, StringBuilder sql, List<Object> paras);
	public abstract void forPaginate(StringBuilder sql, int pageNumber, int pageSize, String select);
	
	public boolean isOracle() {
		return false;
	}
	
	public boolean isTakeOverDbPaginate() {
		return false;
	}
	
	public <T> PageResult<T> takeOverDbPaginate(int pageNumber, int pageSize, String sql, Object... paras) throws SQLException {
		throw new RuntimeException("You should implements this method in " + getClass().getName());
	}
	
	public boolean isTakeOverModelPaginate() {
		return false;
	}
	
	/*@SuppressWarnings("rawtypes")
	public Page takeOverModelPaginate(Connection conn, Class<? extends Model> modelClass, int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) throws Exception {
		throw new RuntimeException("You should implements this method in " + getClass().getName());
	}*/
	
	/*public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
		for (int i=0, size=paras.size(); i<size; i++) {
			pst.setObject(i + 1, paras.get(i));
		}
	}
	
	public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
		for (int i=0; i<paras.length; i++) {
			pst.setObject(i + 1, paras[i]);
		}
	}*/
	
	public String getDefaultPrimaryKey() {
		return "id";
	}
	
	protected boolean isPrimaryKey(String colName, String[] pKeys) {
		for (String pKey : pKeys)
			if (colName.equalsIgnoreCase(pKey))
				return true;
		return false;
	}
	
	/**
	 * 一、forDbXxx 系列方法中若有如下两种情况之一，则需要调用此方法对 pKeys 数组进行 trim():
	 * 1：方法中调用了 isPrimaryKey(...)：为了防止在主键相同情况下，由于前后空串造成 isPrimaryKey 返回 false
	 * 2：为了防止 tableName、colName 与数据库保留字冲突的，添加了包裹字符的：为了防止串包裹区内存在空串
	 *   如 mysql 使用的 "`" 字符以及 PostgreSql 使用的 "\"" 字符
	 * 不满足以上两个条件之一的 forDbXxx 系列方法也可以使用 trimPrimaryKeys(...) 方法让 sql 更加美观，但不是必须
	 * 
	 * 二、forModelXxx 由于在映射时已经trim()，故不再需要调用此方法
	 */
	protected void trimPrimaryKeys(String[] pKeys) {
		for (int i=0; i<pKeys.length; i++)
			pKeys[i] = pKeys[i].trim();
	}
}






