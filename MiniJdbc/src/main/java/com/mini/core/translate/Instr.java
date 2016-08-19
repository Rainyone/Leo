package com.mini.core.translate;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;

/**
 * 字符串截取函数
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class Instr implements ITranslate
{

    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof OracleDialect || dialect instanceof MysqlDialect) {// 如果当前数据库是oracle或者mysql，但是却有sqlserver的charindex函数，那么需要处理
            int index = sql.indexOf("charindex(");
            if (index != -1) {
                sql = changePosition(sql, "charindex(", index);// 另外需要切换函数内部参数的位置
                sql = sql.replaceAll("charindex\\(", "instr\\(");
            }
        }
        else if (dialect instanceof SqlServerDialect) {
            int index = sql.indexOf("instr(");
            if (index != -1) {
                sql = changePosition(sql, "instr(", index);
                sql = sql.replaceAll("instr\\(", "charindex\\(");
            }
        }
        return sql;
    }

    /**
     * 切换函数内部参数的位置
     * @param sql 原始sql
     * @param item 要查询的函数名称
     * @param index 第一个函数的起始位置
     * @return String 处理好的sql
     */
    private String changePosition(String sql, String item, int index) {
        String result = null;
        if (index != -1) {
            int end = sql.indexOf(")", index);// 找到函数结束位置
            String funcParam = sql.substring(index + item.length(), end);// 截取出函数参数
            String[] params = funcParam.split(",");// 切出参数
            funcParam = params[1] + "," + params[0];// 互换位置
            sql = sql.substring(0, index + item.length()) + funcParam + sql.substring(end);// 重新拼出sql
            index = sql.indexOf(item, end);// check后面是否还有该函数
            if (index != -1) {// 如果有、递归一下
                result = changePosition(sql, item, index);
            }
            else {// 没有的话直接返回
                result = sql;
            }
        }
        return result;
    }
}
