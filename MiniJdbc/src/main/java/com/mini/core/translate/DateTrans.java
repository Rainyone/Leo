package com.mini.core.translate;

import java.util.ArrayList;
import java.util.List;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.dialect.SqlServerDialect;
import com.mini.core.utils.StrKit;

/**
 * 日期函数
 * 
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class DateTrans implements ITranslate
{
    @Override
    public String translate(String sql, Dialect dialect) {
        if (dialect instanceof OracleDialect) {
            if (sql.indexOf("sysdate()") != -1) {// 如果出现mysql的函数
                sql = sql.replaceAll("sysdate\\(\\)", "sysdate from dual");
            }
            if (sql.indexOf("current_date()") != -1) {// 如果出现mysql的函数
                sql = sql.replaceAll("current_date\\(\\)", "sysdate from dual");
            }
            if (sql.indexOf("getdate()") != -1) {// 如果出现sqlserver的函数
                sql = sql.replaceAll("getdate\\(\\)", "sysdate from dual");
            }
            if (sql.indexOf("sysdatetime()") != -1) {// 如果出现sqlserver的函数
                sql = sql.replaceAll("sysdatetime\\(\\)", "sysdate from dual");
            }

            if (sql.indexOf("str_to_date(") != -1) {// 如果出现mysql的函数
                sql = dealDateChangeFunc(sql, "str_to_date(", dialect);
            }
            // // 如果出现sqlserver的函数 TODO cast并不一定出现在此处场景，所以这个切换还需考虑，待定
            // if (sql.indexOf("cast(") != -1) {
            // sql = dealDateChangeFunc(sql, "cast(", JdbcConstantValue.ORACLE);
            // }
            if (sql.indexOf("convert(") != -1) {// 如果出现sqlserver的函数
                sql = dealDateChangeFunc(sql, "convert(", dialect);
            }
        }
        else if (dialect instanceof SqlServerDialect) {
            if (sql.indexOf("sysdate from dual") != -1) {// 如果出现oracle的函数
                sql = sql.replaceAll("sysdate from dual", "sysdatetime\\(\\)");
            } else {// 如果出现mysql的函数
                if (sql.indexOf("current_date()") != -1) {
                    sql = sql.replaceAll("current_date\\(\\)", "sysdatetime\\(\\)");
                }
                if (sql.indexOf("sysdate()") != -1) {
                    sql = sql.replaceAll("sysdate\\(\\)", "sysdatetime\\(\\)");
                }
            }
            if (sql.indexOf("to_date(") != -1) {// 如果出现oracle的函数
                sql = dealDateChangeFunc(sql, "to_date(", dialect);
            }
            if (sql.indexOf("str_to_date(") != -1) {// 如果出现mysql的函数
                sql = dealDateChangeFunc(sql, "str_to_date(", dialect);
            }
        }
        else if (dialect instanceof MysqlDialect) {
            if (sql.indexOf("sysdate from dual") != -1) {// 如果出现oracle的函数
                sql = sql.replaceAll("sysdate from dual", "sysdate\\(\\)");
            } else {// 如果出现sqlserver的函数
                if (sql.indexOf("getdate()") != -1) {
                    sql = sql.replaceAll("getdate\\(\\)", "sysdate\\(\\)");
                }
                if (sql.indexOf("sysdatetime()") != -1) {
                    sql = sql.replaceAll("sysdatetime\\(\\)", "sysdate\\(\\)");
                }
            }

            if (sql.indexOf("to_date(") != -1) {// 如果出现oracle的函数
                sql = dealDateChangeFunc(sql, "to_date(", dialect);
            }
            // // 如果出现sqlserver的函数 TODO cast并不一定出现在此处场景，所以这个切换还需考虑，待定
            // if (sql.indexOf("cast(") != -1) {
            // sql = dealDateChangeFunc(sql, "cast(", JdbcConstantValue.MYSQL);
            // }
            if (sql.indexOf("convert(") != -1) {// 如果出现sqlserver的函数
                sql = dealDateChangeFunc(sql, "convert(", dialect);
            }
        }
        return sql;
    }

    private String dealDateChangeFunc(String sql, String fromFunc, Dialect to) {
        List<String> condition = new ArrayList<String>();
        sql = subStrSql(fromFunc, null, null, 0, ")", false, sql, condition, "★_TEMP_DATE_DEFINE_★");
        for (String item : condition) {
            String castSource = null;
            boolean dateTime = true;

            // 如果有,那么可能是oracle、mysql或者是sqlserver的convert函数
            if (item.indexOf(",") != -1) {
                String[] def = item.split(",");
                // convert函数
                if (def[0].equalsIgnoreCase("datetime")) {
                    castSource = def[1].trim();
                    dateTime = false;
                    if (def.length == 3 && (def[2].equals("120") || def[2].equals("20"))) {
                        dateTime = true;
                    }
                }
                else {
                    castSource = def[0];
                    dateTime = def[1].indexOf(":") > -1 ? true : false;
                }
            }
            // sqlserver的cast函数
            else {
                String[] def = item.split("as");
                castSource = def[0].trim();
            }
            String castDef = null;
            if (to instanceof OracleDialect) {
                if (dateTime) {
                    castDef = "to_date(" + castSource + ",'yyyy-MM-dd HH24:MI:SS')";
                }
                else {
                    castDef = "to_date(" + castSource + ",'yyyy-MM-dd')";
                }
            }
            else if (to instanceof MysqlDialect) {
                if (dateTime) {
                    castDef = "str_to_date(" + castSource + ",'%Y-%m-%d %H:%i:%s')";
                }
                else {
                    castDef = "str_to_date(" + castSource + ",'%Y-%m-%d')";
                }
            }
            else if (to instanceof SqlServerDialect) {
                castDef = "cast(" + castSource + " as datetime)";
            }

            sql = sql.replaceFirst("★_TEMP_DATE_DEFINE_★", castDef);
        }
        return sql;
    }
    
    
    /**
     * 从某字符串中截取出指定内容,截取的同时源内容会截掉
     * @param startBegin 开始字符串的起始标记
     * @param startMiddle 开始字符串的中间标记
     * @param startEnd 开始字符串的结束标记
     * @param from 从开始的多少偏移量开始截取
     * @param to 结束截取的字符串标记,可以有多种情况，用@分割即可
     * @param keep 是否保留首尾标签
     * @param fileContent 源字符串
     * @param result 用于存储截取结果的list，需要外部new后传入
     * @param uidefine 截去元素之后 留下的占位符，可以为空
     * @return List<String> 截取出的字符串
     */
    private static String subStrSql(String startBegin, String startMiddle, String startEnd, int from, String to,
            boolean keep, String fileContent, List<String> result, String uidefine) {
        if (fileContent != null && fileContent.trim().length() > 0 && fileContent.indexOf(startBegin) != -1) {
            int startIndex = -1;// 开始index
            int startWidth = -1;// 开始标签的长度
            int endIndex = -1;// 结束index
            int endWidth = -1;// 结束标签长度
            int index = -1;
            if (StrKit.notBlank(startMiddle)) {// 如果有中间位置进行定位，那么开始的位置就需要很小心的处理了，不只是简单的用startBegin去定位
                index = fileContent.indexOf(startMiddle);
                if (index != -1) {
                    List<Integer> indexList = getIndex(fileContent, startBegin, 0, index);// 查询出所有在index之前出现的位置
                }
            } else {// 其他的话简单点
                startIndex = fileContent.indexOf(startBegin);
            }

            if (StrKit.notBlank(startEnd)) {
                startWidth = startBegin.length();
            } else {
                int startEndIndex = fileContent.indexOf(startEnd, startIndex);
                startWidth = startEndIndex - startIndex + startEnd.length();
            }

            if (startIndex != -1) {// 先找到开始
                String[] toParams = to.split("@");// 结束标签可能有多种情况，首先需要找到正确的那个
                for (String item : toParams) {// 找到正确的结束位置
                    int tempEndIndex = fileContent.indexOf(item, startIndex);
                    if (tempEndIndex != -1) {// 如果找到
                        if (endIndex == -1 || tempEndIndex < endIndex) {// 如果是第一次找到，或者找到的位置靠前，那么重新指向
                            endIndex = tempEndIndex;
                            endWidth = item.length();
                        }
                    }
                }
                if (endIndex == -1) {
                    endIndex = fileContent.length();
                }
                if (!keep) {// 把需要的内容截取出来// 判断是否需要保留标记
                    result.add(fileContent.substring(startIndex + startWidth, endIndex).trim());
                } else {
                    result.add(fileContent.substring(startIndex, endIndex + endWidth).trim());
                }
                String begin = fileContent.substring(0, startIndex);// 从源字符串中截取掉处理过的
                String end = fileContent.substring(endIndex + endWidth);
                if (StrKit.notBlank(uidefine)) {
                    begin = begin + uidefine;
                }
                fileContent = begin + end;
                fileContent = subStrSql(startBegin, startMiddle, startEnd, from, to, keep, fileContent, result, uidefine);// 进入递归

            }
        }
        return fileContent;
    }
    
    private static List<Integer> getIndex(String fileContent, String tag, int start, int end) {
        List<Integer> result = new ArrayList<Integer>();
        int index = fileContent.indexOf(tag, start);
        if (index != -1 && index < end) {// 如果找到了，并且在结束之前
            result.add(index);
            result.addAll(getIndex(fileContent, tag, index + tag.length(), end));// 进入递归
        }

        return result;
    }

}
