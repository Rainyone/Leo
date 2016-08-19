package com.mini.core.translate;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.Checker;

import com.mini.core.dialect.Dialect;
import com.mini.core.dialect.MysqlDialect;
import com.mini.core.dialect.OracleDialect;
import com.mini.core.utils.StrKit;

/**
 * order by 中文处理函数，对于中文的排序需求为拼音
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class OrderBy implements ITranslate
{
    /**
     * 是否自动处理orderby中文
     */
    private static String orderBy = null;

    /**
     * 忽略处理的字符串
     */
    private static List<String> ignoreList = null;

    static {
        if ("auto".equalsIgnoreCase(orderBy)) {
            ignoreList = new ArrayList<String>();
            ignoreList.add("length");
            ignoreList.add("ordern");
            ignoreList.add("(");
            ignoreList.add("date");
            ignoreList.add("time");
            ignoreList.add("vmlId");
            ignoreList.add("birthday");
        }
    }

    @Override
    public String translate(String sql, Dialect dialect) {
        if ("auto".equalsIgnoreCase(orderBy)) {
            String sqlTemplate = sql.toLowerCase();// 首先我们要解析出order by后面的内容
            int index = sqlTemplate.lastIndexOf(" order ");
            if (index != -1) {
                String behind = sqlTemplate.substring(index + " order ".length()).trim();// 截取order后面的,并且去除首尾空格
                if (behind.startsWith("by")) {// 如果后面跟的是by，那么就是他了
                    behind = behind.substring("by".length(), behind.length()).trim();
                    String before = sql.substring(0, sqlTemplate.indexOf(behind));
                    String limit = " top ";// 为了避免某些阿富汗自己处理了，所以先判断,为了避免某些自己写分页sql的怪胎，下面处理掉后面可能出现的分页内容
                    String dealed = "chinese_prc_cs_as_ks_ws";
                    String convert = "columnName collate chinese_prc_cs_as_ks_ws";
                    if (dialect instanceof MysqlDialect) {
                        limit = " limit ";
                        dealed = "GBK)";
                        convert = "CONVERT(columnName using GBK)";
                    }
                    else if (dialect instanceof OracleDialect) {
                        limit = ") row_ where";
                        dealed = "SCHINESE_PINYIN_M";
                        convert = "nlssort(columnName,'NLS_SORT=SCHINESE_PINYIN_M')";
                    }
                    if (behind.indexOf(dealed) < 0) {// 只有没处理的才需处理
                        int limitIndex = behind.indexOf(limit);
                        String after = "";
                        if (limitIndex != -1) {// 如果还真有阿富汗
                            behind = behind.substring(0, limitIndex).trim();
                            after = behind.substring(limitIndex);
                        }
                        String[] orders = behind.split("\\,");// 好了，下面可以开始
                        String order = " ";
                        int length = 0;
                        for (String item : orders) {
                            item = item.trim();
                            String[] items = item.split(" ");
                            if (checkIsIgnore(items[0], sql)) {// 检查这个排序字段是否可以忽略转换
                                order += items[0];
                            }
                            else {
                                order += convert.replace("columnName", items[0]);
                            }
                            if (items.length > 1) {
                                order += " " + items[1];
                            }
                            if (length < orders.length - 1) {
                                order += ",";
                            }
                            else {
                                order += " ";
                            }
                            length++;
                        }
                        sql = before + order + after;
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 判断当前排序字段是否可以忽略处理
     * @param items  排序字段
     * @param sql 完整sql
     * @return boolean
     */
    private boolean checkIsIgnore(String items, String sql) {
        boolean ignore = false;
        // 如果这个是个排序号，数字类型的，那么没必要,或者带上了length(这种方法的，也不用,如果是个日期也不用
        for (String a : ignoreList) {
            if (items.indexOf(a) != -1) {
                ignore = true;
                break;
            }
        }
        return ignore;
    }

}
