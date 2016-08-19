package com.mini.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mini.core.utils.MiniUtil;
import com.mini.core.utils.StrKit;
import com.mini.core.utils.TypeUtil;

/**
 * Record
 */
public class Record extends HashMap<String,Object> implements Serializable,Model {
	
	private static final long serialVersionUID = 905784513600884082L;
	
	public Record(){}
	
	public Record(String tableName){
		this.tableName = MiniUtil.caps(tableName);
	}
	
	public Record(String tableName,String primaryKey){
		this.tableName = MiniUtil.caps(tableName);
		this.primaryKeys = new String[]{primaryKey};
	}
	
	public Record(String tableName,String[] primaryKeys){
		this.tableName = MiniUtil.caps(tableName);
		this.primaryKeys = primaryKeys;
	}
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 主键
	 */
	private String[] primaryKeys;
	
	/**
	 * 修改记录标志
	 */
	private Set<String> modifyFlag =  new HashSet<String>();
	
	/**
	 * 清空修改记录
	 */
	public void clearModifyFlag() {
		modifyFlag.clear();
	}
	
	/**
	 * 获取修改记录标志
	 * @return
	 */
	public Set<String> getModifyFlag() {
		return modifyFlag;
	}
	
	/**
	 * 设置主键
	 * @param primaryKeys
	 */
	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	
	/**
	 * 设置主键
	 * @param primaryKey
	 */
	public void setPrimaryKeys(String primaryKey) {
		this.primaryKeys = new String[]{primaryKey};
	}

	/**
	 * 设置表名
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = MiniUtil.caps(tableName);
	}
	
	/**
	 * 获取主键
	 */
	public String[] getPrimaryKeys() {
		if(primaryKeys!=null&&primaryKeys.length>0)
			return primaryKeys;
		else{
			Class clazz = this.getClass();
			if(MiniUtil.isBaseEntity(clazz)){
				String[] pks = EntityMapping.me().getEntity(clazz).getPrimaryKey();
				if(pks!=null&&pks.length>0)
					return pks;
				else
					throw new MiniDaoException("主键不存在");
			}else
				throw new MiniDaoException("主键不存在");
		}
	}

	/**
	 * 获取表名
	 */
	public String getTableName(){
		if(StrKit.notBlank(tableName))
			return tableName;
		else{
			boolean findit = false;
			Class clazz = this.getClass();
			if(MiniUtil.isBaseEntity(clazz)){
				String tabName = EntityMapping.me().getEntity(clazz).getName();
				if(StrKit.notBlank(tabName))
					return MiniUtil.caps(tabName);
				else
					throw new MiniDaoException("表名不存在");
			}else
				throw new MiniDaoException("表名不存在");
		}
	}
	
	/**
	 * return contains key
	 * @param column
	 * @return
	 */
	public boolean containsKey(String column){
		return super.containsKey(MiniUtil.caps(column));
	}
	
	/**
	 * Set columns value with map.
	 * @param columns the columns map
	 */
	public Record setColumns(Map<String, Object> columns) {
		Map<String, Object> cols = new HashMap<String, Object>();
		for(Entry<String, Object> entry : columns.entrySet()){
			cols.put(MiniUtil.caps(entry.getKey()), entry.getValue());
		}
		super.putAll(cols);
		return this;
	}
	
	/**
	 * Set columns value with Record.
	 * @param record the Record object
	 */
	public Record setColumns(Record record) {
		super.putAll(record);
		return this;
	}
	
	/**
	 * Remove attribute of this record.
	 * @param column the column name of the record
	 */
	public Record remove(String column) {
		super.remove(MiniUtil.caps(column));
		return this;
	}
	
	/**
	 * Remove columns of this record.
	 * @param columns the column names of the record
	 */
	public Record remove(String... columns) {
		if (columns != null)
			for (String c : columns)
				super.remove(MiniUtil.caps(c));
		return this;
	}
	
	/**
	 * Remove columns if it is null.
	 */
	public Record removeNullValueColumns() {
		for (java.util.Iterator<Entry<String, Object>> it = super.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() == null) {
				it.remove();
			}
		}
		return this;
	}
	
	/**
	 * Keep columns of this record and remove other columns.
	 * @param columns the column names of the record
	 */
	public Record keep(String... columns) {
		if (columns != null && columns.length > 0) {
			Map<String, Object> newColumns = new HashMap<String, Object>(columns.length);	// getConfig().containerFactory.getColumnsMap();
			for (String c : columns)
				if (super.containsKey(MiniUtil.caps(c)))	// prevent put null value to the newColumns
					newColumns.put(MiniUtil.caps(c), super.get(MiniUtil.caps(c)));
			
			super.clear();
			super.putAll(newColumns);
		}
		else
			super.clear();
		return this;
	}
	
	/**
	 * Keep column of this record and remove other columns.
	 * @param column the column names of the record
	 */
	public Record keep(String column) {
		column = MiniUtil.caps(column);
		if (super.containsKey(column)) {	// prevent put null value to the newColumns
			Object keepIt = super.get(column);
			super.clear();
			super.put(column, keepIt);
		}
		else
			super.clear();
		return this;
	}
	
	/**
	 * Remove all columns of this record.
	 */
	public void clear() {
		super.clear();
	}
	
	/**
	 * Set column to record.
	 * @param column the column name
	 * @param value the value of the column
	 */
	public Record set(String column, Object value) {
		column = MiniUtil.caps(column);
		modifyFlag.add(column);
		super.put(column, value);
		return this;
	}
	
	/**
	 * Get column of any mysql type
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String column) {
		return (T)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of any mysql type.
	 */
	public Object get(Object column) {
		return super.get(MiniUtil.caps(column.toString()));
	}
	
	/**
	 * Get column of any mysql type. Returns defaultValue if null.
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String column, Object defaultValue) {
		Object result = super.get(column);
		return (T)(result != null ? result : defaultValue);
	}
	
	/**
	 * Get column of mysql type: varchar, char, enum, set, text, tinytext, mediumtext, longtext
	 */
	public String getStr(String column) {
		return TypeUtil.object2String(super.get(MiniUtil.caps(column)));
	}
	
	/**
	 * Get column of mysql type: int, integer, tinyint(n) n > 1, smallint, mediumint
	 */
	public Integer getInt(String column) {
		return TypeUtil.object2Int(super.get(MiniUtil.caps(column)));
	}
	
	/**
	 * Get column of mysql type: bigint
	 */
	public Long getLong(String column) {
		return TypeUtil.object2Long(super.get(MiniUtil.caps(column)));
	}
	
	/**
	 * Get column of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String column) {
		return (java.math.BigInteger)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of mysql type: date, year
	 */
	public java.util.Date getDate(String column) {
		return (java.util.Date)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of mysql type: time
	 */
	public java.sql.Time getTime(String column) {
		return (java.sql.Time)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of mysql type: timestamp, datetime
	 */
	public java.sql.Timestamp getTimestamp(String column) {
		return (java.sql.Timestamp)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of mysql type: real, double
	 */
	public Double getDouble(String column) {
		return TypeUtil.object2Double(super.get(MiniUtil.caps(column)));
	}
	
	/**
	 * Get column of mysql type: float
	 */
	public Float getFloat(String column) {
		return TypeUtil.object2Float(super.get(MiniUtil.caps(column)));
	}
	
	/**
	 * Get column of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String column) {
		return (Boolean)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String column) {
		return (java.math.BigDecimal)super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of mysql type: binary, varbinary, tinyblob, blob, mediumblob, longblob
	 * I have not finished the test.
	 */
	public byte[] getBytes(String column) {
		return (byte[])super.get(MiniUtil.caps(column));
	}
	
	/**
	 * Get column of any type that extends from Number
	 */
	public Number getNumber(String column) {
		return (Number)super.get(MiniUtil.caps(column));
	}
	
	/*public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(" {");
		boolean first = true;
		for (Entry<String, Object> e : super.entrySet()) {
			if (first)
				first = false;
			else
				sb.append(", ");
			
			Object value = e.getValue();
			if (value != null)
				value = value.toString();
			sb.append(e.getKey()).append(":").append(value);
		}
		sb.append("}");
		return sb.toString();
	}*/
	
	public boolean equals(Object o) {
		if (!(o instanceof Record))
            return false;
		if (o == this)
			return true;
		return super.equals(((Record)o));
	}
	
	public int hashCode() {
		return this == null ? 0 : this.hashCode();
	}
	
	/**
	 * Return column names of this record.
	 */
	public String[] getColumnNames() {
		Set<String> attrNameSet = super.keySet();
		return attrNameSet.toArray(new String[attrNameSet.size()]);
	}
	
	/**
	 * Return column values of this record.
	 */
	public Object[] getColumnValues() {
		java.util.Collection<Object> attrValueCollection = super.values();
		return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
	}
	
}




