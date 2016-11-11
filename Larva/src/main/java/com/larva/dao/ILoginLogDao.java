package com.larva.dao;

import com.larva.model.LoginLog;
import com.mini.core.PageResult;

public interface ILoginLogDao {

	PageResult<LoginLog> query(int pageNow,int count);
	int insertLoginLog(LoginLog loginLog);
}
