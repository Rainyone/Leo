package com.mini.core.translate;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 判空函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class Null implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof OracleDialect) {
            if (sql.indexOf("ifnull(") != -1) {// 如果出现mysql的函数
                sql = sql.replaceAll("ifnull\\(", "nvl\\(");
            }
            else if (sql.indexOf("isnull(") != -1) {// 如果出现sqlserver的函数
                sql = sql.replaceAll("isnull\\(", "nvl\\(");
            }
        }
        else if (dialect instanceof SqlServerDialect) {
            if (sql.indexOf("nvl(") != -1) {// 如果出现oracle的函数
                sql = sql.replaceAll("nvl\\(", "isnull\\(");
            }
            else if (sql.indexOf("ifnull(") != -1) {// 如果出现mysql的函数
                sql = sql.replaceAll("ifnull\\(", "isnull\\(");
            }
        }
        else if (dialect instanceof MysqlDialect) {
            if (sql.indexOf("nvl(") != -1) {// 如果出现oracle的函数
                sql = sql.replaceAll("nvl\\(", "ifnull\\(");
            }
            else if (sql.indexOf("isnull(") != -1) {// 如果出现sqlserver的函数
                sql = sql.replaceAll("isnull\\(", "ifnull\\(");
            }
        }
        return sql;
    }
}
