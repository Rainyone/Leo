package com.mini.core;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;

/**
 * 完全拷贝RecordRowMapper，修改mapRow方法
 * 有个前提，所有的Bean必须继承BaseEntity
 * 
 * @author sxjun
 * @param <T>
 * @date 2015-12-29 下午10:00:39 
 *
 */
public class WeakEntityRowMapper<T> implements RowMapper<T> {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private Class<? extends WeakEntity> clazz;
	
	/** The class we are mapping to */
	private Class<T> mappedClass;
	
	/** Map of the fields we provide mapping for */
	private Map<String, PropertyDescriptor> mappedFields;
	
	/** Whether we're defaulting primitives when mapping a null value */
	private boolean primitivesDefaultedForNullValue = false;

	public Map<String, PropertyDescriptor> getMappedFields() {
		return mappedFields;
	}

	public void setMappedFields(Map<String, PropertyDescriptor> mappedFields) {
		this.mappedFields = mappedFields;
	}

	/** Set of bean properties we provide mapping for */
	private Set<String> mappedProperties;

	
	public Set<String> getMappedProperties() {
		return mappedProperties;
	}

	public void setMappedProperties(Set<String> mappedProperties) {
		this.mappedProperties = mappedProperties;
	}

	public WeakEntityRowMapper(Class clazz){
		this.clazz = clazz;
	}
	
	public void setMappedClass(Class<T> mappedClass) {
		if (this.mappedClass == null) {
			initialize(mappedClass);
		}
		else {
			if (!this.mappedClass.equals(mappedClass)) {
				throw new InvalidDataAccessApiUsageException("The mapped class can not be reassigned to map to " +
						mappedClass + " since it is already providing mapping for " + this.mappedClass);
			}
		}
	}

	protected void initialize(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
		this.mappedFields = new HashMap<String, PropertyDescriptor>();
		this.mappedProperties = new HashSet<String>();
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null) {
				this.mappedFields.put(pd.getName().toLowerCase(), pd);
				String underscoredName = underscoreName(pd.getName());
				if (!pd.getName().toLowerCase().equals(underscoredName)) {
					this.mappedFields.put(underscoredName, pd);
				}
				this.mappedProperties.add(pd.getName());
			}
		}
	}
	
	private String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			result.append(name.substring(0, 1).toLowerCase());
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.equals(s.toUpperCase())) {
					result.append("_");
					result.append(s.toLowerCase());
				}
				else {
					result.append(s);
				}
			}
		}
		return result.toString();
	}

	/**
	 * 弱实体的处理
	 */
	public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
		Assert.state(this.mappedClass != null, "Mapped class was not specified");
		T mappedObject = BeanUtils.instantiate(this.mappedClass);
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int index = 1; index <= columnCount; index++) {
			String column = JdbcUtils.lookupColumnName(rsmd, index);
			PropertyDescriptor pd = this.mappedFields.get(BaseEntityMapper.getPropertyName(mappedObject.getClass().getName(), column.replace(" ", "")).toLowerCase());
			if (pd != null) {
				try {
					Object value = getColumnValue(rs, index, pd);
					if (logger.isDebugEnabled() && rowNumber == 0) {
						logger.debug("Mapping column '" + column + "' to property '" + pd.getName() + "' of type " + pd.getPropertyType());
					}
					try {
						bw.setPropertyValue(pd.getName(), value);
					}
					catch (TypeMismatchException e) {
						if (value == null && primitivesDefaultedForNullValue) {
							logger.debug("Intercepted TypeMismatchException for row " + rowNumber +
									" and column '" + column + "' with value " + value +
									" when setting property '" + pd.getName() + "' of type " + pd.getPropertyType() +
									" on object: " + mappedObject);
						}
						else {
							throw e;
						}
					}
				}
				catch (NotWritablePropertyException ex) {
					throw new DataRetrievalFailureException(
							"Unable to map column " + column + " to property " + pd.getName(), ex);
				}
			}
		}
		if(mappedObject instanceof Record)
			((Record)mappedObject).clearModifyFlag();
		return mappedObject;
		/*WeakEntity o = null;
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int columns = meta.getColumnCount();
			o = clazz.newInstance();
			o.setPrimaryKeys(o.getPrimaryKeys());
			o.setTableName(o.getTableName());
			for(int i=1;i<=columns;i++)
			    o.set(meta.getColumnName(i), rs.getObject(meta.getColumnName(i)));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
        return (T) o;*/
	}
	
	protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
	}
	
	/**
	 * 初始化弱实体
	 * @param mappedClass
	 * @return
	 */
	public static <T> WeakEntityRowMapper<T> newInstance(Class<T> mappedClass) {
		WeakEntityRowMapper<T> newInstance = new WeakEntityRowMapper<T>(mappedClass);
		newInstance.setMappedClass(mappedClass);
		return newInstance;
	}
}
