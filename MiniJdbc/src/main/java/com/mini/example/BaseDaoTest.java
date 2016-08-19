/**   
 * BaseDaoTest.java
 *
 * @author sxjun
 * @date 2014-7-20 下午10:19:01 
 * @verion 0.1.0
 */
package com.mini.example;

import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mini.core.dao.MiniDao;
import com.mini.example.bean.Demo;


/**
 * TEST CASE
 * 
 * @author sxjun
 * @date 2014-7-20 下午10:19:01
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../../../applicationContext.xml")
public class BaseDaoTest {

	@Autowired
	private MiniDao dao;

	@Test
	public void testInsert() {
	try {
		
			Demo demo = new Demo();
			demo.setCreateTime(new Date());
			demo.setName("demo_" + new Random().nextInt(10000));
			demo.setType(new Random().nextInt(5));
			demo.setPassword("000000");
			int c = dao.insert(demo);
			System.out.println("testInsert restult : " + c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
