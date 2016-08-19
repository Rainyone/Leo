package com.mini.core.translate;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 把多行转换成一合并列函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class ManyRowToOneColumnTranslate implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof SqlServerDialect || dialect instanceof MysqlDialect) {
            if (sql.indexOf("wm_concat(") != -1) {
                sql = sql.replaceAll("wm_concat\\(", "group_concat\\(");
            }
        }
        else if (dialect instanceof OracleDialect) {
            if (sql.indexOf("group_concat(") != -1) {
                sql = sql.replaceAll("group_concat\\(", "wm_concat\\(");
            }
        }
        return sql;
    }

}
