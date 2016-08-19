package com.mini.core.translate;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 长度函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class Length implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof OracleDialect) {
            if (sql.indexOf("len(") != -1) {
                sql = sql.replaceAll("len\\(", "length\\(");
            }
            else if (sql.indexOf("char_length(") != -1) {
                sql = sql.replaceAll("char_length\\(", "length\\(");
            }
        }
        else if (dialect instanceof SqlServerDialect) {
            if (sql.indexOf("char_length(") != -1) {
                sql = sql.replaceAll("char_length\\(", "len\\(");
            }
            if (sql.indexOf("length(") != -1) {
                sql = sql.replaceAll("length\\(", "len\\(");
            }
        }
        else if (dialect instanceof MysqlDialect) {
            if (sql.indexOf("len(") != -1) {
                sql = sql.replaceAll("len\\(", "char_length\\(");
            }
        }
        return sql;
    }
}
