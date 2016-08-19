package com.mini.example;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mini.core.dao.MiniDao;
import com.mini.example.bean.Demo;

public class MiniStrongExample {

    static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    static MiniDao dao = (MiniDao) ctx.getBean("miniDao");

    /**
     * 测试插入强类型实例User
     */
    public static int testInsert() {
        Demo demo = new Demo();
        demo.setCreateTime(new Date());
        demo.setName("username");
        demo.setType(new Random().nextInt(5));
        return dao.insert(demo);
    }

    /**
     * 更新强类型实例
     * @return
     */
    public static int testUpdate() {
        Demo demo = dao.findById(Demo.class, "68");
        demo.setName("你好");
        return dao.update(demo);
    }

    /**
     * 删除强类型实体
     * @return
     */
    public static int testDelete() {
        Demo demo = new Demo();
        demo.setId("81");
        return dao.delete(demo);
    }

    /**
     * 根据id获取强类型实例User
     * @return
     */
    public static Demo getObject1() {
    	Demo demo = dao.findById(Demo.class, 22);
        return demo;
    }

    /**
     * 无参获取强类型实例User
     * @return
     */
    public static Demo getObject2() {
        Demo demo = dao.find("select * from users where id=4", Demo.class);
        return demo;
    }

    /**
     * 有参获取强类型实例User
     * @return
     */
    public static Demo getObject3() {
        Demo demo = dao.find("select * from users where id=? and name=?", Demo.class, 2, "helelo");
        return demo;
    }

    /**
     * 无参获取强类型实例列表
     * @return
     */
    public static List<Demo> getList() {
        List<Demo> users = dao.findList("select * from users where id<99", Demo.class);
        return users;
    }

    /**
     * 有参获取强类型实例列表
     * @return
     */
    public static List<Demo> getList2() {
        List<Demo> users = dao.findList("select * from users where id <? and password like ?", Demo.class, 5, "%'");
        return users;
    }

    /**
     * 无参强类型分页
     */
    public static List<Demo> testGetPage() {
        String sql = "select * from users where id < 8 order by id desc";
        List<Demo> users = dao.paginate(sql, 2, 2, Demo.class);
        return users;
    }

    /**
     * 有参强类型分页
     */
    public static List<Demo> testGetPage2() {
        String sql = "select * from users where id < ? order by id desc";
        List<Demo> users = dao.paginate(sql, 2, 2, Demo.class, 18);
        return users;
    }
    
    public static void main(String[] args) {
    	testInsert();
        getObject1();
        getObject2();
        getObject3();
        getList();
        getList2();
        testUpdate();
        testDelete();
        testGetPage();
        testGetPage2();
    }
}
