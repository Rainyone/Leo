package com.mini.core.translate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 空格截取函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class Trim implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof OracleDialect || dialect instanceof SqlServerDialect) {
            boolean haveLtrim = false;
            boolean haveRtrim = false;
            if (sql.indexOf("ltrim(") != -1) {
                sql = sql.replaceAll("ltrim\\(", "LEFTTRIM_FUNCTION");
                haveLtrim = true;
            }
            if (sql.indexOf("rtrim(") != -1) {
                sql = sql.replaceAll("rtrim\\(", "RIGHTTTRIM_FUNCTION");
                haveRtrim = true;
            }
            if (sql.indexOf("trim(") != -1) {
                sql = sql.replaceAll("trim\\(", "ltrim\\(rtrim\\(");
                sql = formatTrim(sql);
            }
            if (haveLtrim) {
                sql.replaceAll("LEFTTRIM_FUNCTION", "ltrim\\(");
            }
            if (haveRtrim) {
                sql.replaceAll("RIGHTTTRIM_FUNCTION", "rtrim\\(");
            }
        }
        return sql;
    }

    private String formatTrim(String sql) {
        Pattern p1 = Pattern.compile("ltrim\\(rtrim\\(");
        Matcher m1 = p1.matcher(sql);
        int count = 0;
        while (m1.find()) {
            int start = m1.end();
            int end = sql.indexOf(")", start) + count;
            sql = sql.substring(0, end) + ")" + sql.substring(end);
            count++;
        }
        return sql;
    }

}
