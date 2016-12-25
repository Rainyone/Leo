package com.larva.dao.impl;

import org.springframework.stereotype.Repository;

import com.larva.dao.TestDao;
import com.mini.core.dao.MiniDao;
@Repository("testDao")
public class TestDaoImpl extends MiniDao implements TestDao {

	@Override
	public int updateTestA() {
		return this.execute("update account set password ='65432133333' where account = ? ","123456");
	}

	@Override
	public int updateTestB() {
		return this.execute("update account2 set password ='654321a' where account = ? ","wanghan");
	}

}
