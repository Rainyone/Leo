package com.mini.core.translate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 拼接函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class Contact implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof OracleDialect) {
            if (sql.indexOf("+") != -1) {// 如果出现sqlserver的函数,那么需要转换,mysql的函数的话无需,因为本身支持
            	Pattern p = Pattern.compile("\\(\\s*\\+\\s*\\)");
        		Matcher m = p.matcher(sql); 
        		if(!m.find())
        			sql = sql.replaceAll("+", "||");
            }
        }
        else if (dialect instanceof SqlServerDialect) {
            if (sql.indexOf("||") != -1) { // 如果出现oracle的函数
                sql = sql.replaceAll("||", "+");
            }
            else if (sql.indexOf("concat(") != -1) {// 如果出现mysql或者oracle的另一个拼接函数
                int start = sql.indexOf("concat(");
                int end = sql.indexOf(")", start);
                String pre = sql.substring(0, start);
                String beh = sql.substring(end + 1, sql.length());
                String middle = sql.substring(start, end + 1);
                middle = middle.replace("concat", "");
                middle = middle.replace(",", "+");
                sql = pre + middle + beh;
                sql = translate(sql, dialect);
            }
        }
        else if (dialect instanceof MysqlDialect) {// 对于mysql来说,||,+都是它的内置函数,所以不好转换
        }
        return sql;
    }

}
