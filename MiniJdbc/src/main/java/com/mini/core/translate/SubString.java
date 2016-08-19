package com.mini.core.translate;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 字符串截取函数
 * 
 * @author komojoemary
 * @version [版本号, 2016-1-27]
 */
public class SubString implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof SqlServerDialect || dialect instanceof MysqlDialect) {
            if (sql.indexOf("substr(") != -1) {
                sql = sql.replaceAll("substr\\(", "substring\\(");
            }
        }
        else if (dialect instanceof OracleDialect) {
            if (sql.indexOf("substring(") != -1) {
                sql = sql.replaceAll("substring\\(", "substr\\(");
            }
        }
        return sql;
    }

}
