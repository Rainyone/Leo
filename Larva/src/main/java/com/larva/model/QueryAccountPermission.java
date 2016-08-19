package com.larva.model;

public class QueryAccountPermission {
    private Integer accountId;

    private String querySql;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql == null ? null : querySql.trim();
    }
}