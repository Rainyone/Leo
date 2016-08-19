package com.larva.dao;

import com.github.pagehelper.PageInfo;
import com.larva.model.LoginLog;
import com.mini.core.PageResult;

/**
 * Created by sxjun on 15-9-12.
 */
public interface ILoginLogDao {

	PageResult<LoginLog> query(int pageNow,int count);

}
