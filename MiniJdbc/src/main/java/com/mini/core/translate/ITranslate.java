package com.mini.core.translate;

import com.mini.core.dialect.Dialect;

public interface ITranslate
{
    public String translate(String sql, Dialect dialect);
}
