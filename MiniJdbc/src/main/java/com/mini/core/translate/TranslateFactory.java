package com.mini.core.translate;

import com.mini.core.dialect.Dialect;

/**
 * sql 函数差异转换
 * @author sxjun
 * @version [版本号, 2016-1-27]
 */
public class TranslateFactory
{
    private static final ITranslate[] translate = new ITranslate[] {
            new Length(), new Trim(), new SubString(), new Null(), new Instr(),
            new DateTrans(), new Underline(), new OrderBy() };

    public static String translate(String sql, Dialect dialect) {
        for (ITranslate item : translate) {
            sql = item.translate(sql, dialect);
        }
        return sql;
    }
}
