package com.mini.core.translate;

import java.util.ArrayList;
import java.util.List;

import com.mini.core.dialect.Dialect;

/**
 * 下划线处理函数，由于sql语句对于下划线是一个敏感字符，所以模糊查询的时候需要进行转义处理
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class Underline implements ITranslate
{
    private static List<String> underline = new ArrayList<String>();
    private static String special = "komojoemary_suiyue";
    static {
        underline.add("%_%");
        underline.add("_%");
        underline.add("%_");
    }

    @Override
    public String translate(String sql, Dialect dialect) {
        String in = isIncludeUnderLine(sql);// 判断sql中有没有下划线的模糊查询
        if (in != null) {// 如果有，那么需要进行转义处理
            sql = sql.replaceFirst(in, special);// 将这个下划线替换成为一个特殊字符
            int index = sql.indexOf("'", sql.indexOf(special));// 找到这个特殊字符串后面第一个'位置
            if (index >= 0) {
                sql = sql.substring(0, index + 1) + " escape '\\'" + sql.substring(index + 1);// 在后面拼接上转义
            }
            in = in.replace("_", "\\_");// 将下划线进行转义处理
            sql = sql.replace(special, in);// 重新替换特殊符号
            sql = translate(sql, dialect);// 避免sql中有多处此类场景，递归一下
        }
        return sql;
    }

    private String isIncludeUnderLine(String sql) {
        String result = null;
        for (String item : underline) {
            int index = sql.indexOf(item);
            if (index >= 0) {
                boolean tr = true;
                if (item.equalsIgnoreCase("_%")) {// 如果是这个特殊字符
                    String before = sql.substring(index - 1, index);// 要判断下前面的是不是已经转义过的\,否则会进入死循环
                    if (before.equalsIgnoreCase("\\")) {
                        tr = false;
                    }
                }
                if (tr) {
                    result = item;
                }
                break;
            }
        }
        return result;
    }
}
