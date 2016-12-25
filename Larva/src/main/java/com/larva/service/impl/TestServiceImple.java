package com.larva.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.larva.dao.TestDao;
import com.larva.service.TestService;
@Service("testService")
public class TestServiceImple implements TestService {
	 @Resource
	private TestDao testDao;

	@Override
	public void aupdateTest() {	
		testDao.updateTestA();
		testDao.updateTestB();
	}
	
}
