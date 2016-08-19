package com.mini.core.dialect;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mini.core.EntityInfo;
import com.mini.core.Record;
import com.mini.core.EntityInfo;

/**
 * MysqlDialect.
 */
public class H2Dialect extends Dialect {
	
	public String forTableBuilderDoBuild(String tableName) {
		return "select * from `" + tableName + "` where 1 = 2";
	}
	
	public void forModelSave(EntityInfo table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
		sql.append("insert into `").append(table.getName()).append("`(");
		StringBuilder temp = new StringBuilder(") values(");
		for (Entry<String, Object> e: attrs.entrySet()) {
			String colName = e.getKey();
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("`").append(colName).append("`");
			temp.append("?");
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(")");
	}
	
	public String forModelDeleteById(EntityInfo table) {
		String[] pKeys = table.getPrimaryKey();
		StringBuilder sql = new StringBuilder(45);
		sql.append("delete from `");
		sql.append(table.getName());
		sql.append("` where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}
	
	public void forModelUpdate(EntityInfo table, Map<String, Object> attrs, Set<String> modifyFlag, StringBuilder sql, List<Object> paras) {
		sql.append("update `").append(table.getName()).append("` set ");
		String[] pKeys = table.getPrimaryKey();
		for (Entry<String, Object> e : attrs.entrySet()) {
			String colName = e.getKey();
			if (modifyFlag.contains(colName)) {
				boolean isKey = false;
				for (String pKey : pKeys)	// skip primaryKeys
					if (pKey.equalsIgnoreCase(colName)) {
						isKey = true ;
						break ;
					}
				
				if (isKey)
					continue;
				
				if (paras.size() > 0)
					sql.append(", ");
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append("`").append(pKeys[i]).append("` = ?");
			paras.add(attrs.get(pKeys[i]));
		}
	}
	
	public String forModelFindById(EntityInfo table, String columns) {
		StringBuilder sql = new StringBuilder("select ");
		columns = columns.trim();
		if ("*".equals(columns)) {
			sql.append("*");
		}
		else {
			String[] arr = columns.split(",");
			for (int i=0; i<arr.length; i++) {
				if (i > 0)
					sql.append(",");
				sql.append("`").append(arr[i].trim()).append("`");
			}
		}
		
		sql.append(" from `");
		sql.append(table.getName());
		sql.append("` where ");
		String[] pKeys = table.getPrimaryKey();
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}
	
	public String forDbFindById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		StringBuilder sql = new StringBuilder("select * from `").append(tableName).append("` where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}
	
	public String forDbDeleteById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		StringBuilder sql = new StringBuilder("delete from `").append(tableName).append("` where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}
	
	public void forDbSave(StringBuilder sql, List<Object> paras, String tableName, String[] pKeys, Record record) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		sql.append("insert into `");
		sql.append(tableName).append("`(");
		StringBuilder temp = new StringBuilder();
		temp.append(") values(");
		for (Entry<String, Object> e: record.entrySet()) {
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("`").append(e.getKey()).append("`");
			temp.append("?");
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(")");
	}
	
	public void forDbUpdate(String tableName, String[] pKeys, Object[] ids, Record record, StringBuilder sql, List<Object> paras) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		sql.append("update `").append(tableName).append("` set ");
		for (Entry<String, Object> e: record.entrySet()) {
			String colName = e.getKey();
			if (!isPrimaryKey(colName, pKeys)) {
				if(record.getModifyFlag().contains(colName)){
					if (paras.size() > 0) {
						sql.append(", ");
					}
					sql.append("`").append(colName).append("` = ? ");
					paras.add(e.getValue());
				}
			}
		}
		sql.append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append("`").append(pKeys[i]).append("` = ?");
			paras.add(ids[i]);
		}
	}
	
	public void forPaginate(StringBuilder sql, int pageNumber, int pageSize, String select) {
		int offset = pageSize * (pageNumber - 1);
		sql.append(select).append(" ");
		sql.append(" limit ").append(offset).append(", ").append(pageSize);	// limit can use one or two '?' to pass paras
	}
}
