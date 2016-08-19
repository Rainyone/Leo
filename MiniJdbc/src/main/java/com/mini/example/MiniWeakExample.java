package com.mini.example;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mini.core.Record;
import com.mini.core.dao.MiniDao;
import com.mini.example.bean.User;

public class MiniWeakExample {

    static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    static MiniDao dao = (MiniDao) ctx.getBean("miniDao");


    /**
     * 测试插入强类型实例User
     */
    public static int testInsert() {
        User user = new User();
        user.setCreateTime(new Date()).setId("iiiiiiiiiiiiiiii").setName("username") .setType(new Random().nextInt(5));
        return dao.insert(user);
    }

    /**
     * 测试插入强类型实例Record
     */
    public static int testInsert2() {
        Record record = new Record("users","id");
        record.set("id", new Random().nextInt(100)) .set("name", "username") .set("type", new Random().nextInt(5)).set("password", "000000");
        return dao.insert(record);
    }

    /**
     * 更新强类型实例
     * @return
     */
    public static int testUpdate() {
        User user = new User();
        user.setName("hello_update");
        return dao.update(user);
    }

    /**
     * 更新弱类型记录
     * @return
     */
    public static int testUpdateRecord() {
        Record record = new Record();
        record.setTableName("users");
        record.setPrimaryKeys("id");
        record.set("id", "8").set("name", "record_update");
        return dao.update(record);
    }

    /**
     * 删除强类型实体
     * @return
     */
    public static int testDelete() {
        User user = new User();
        user.setId("1");
        return dao.delete(user);
    }

    /**
     * 删除弱类型记录
     * @return
     */
    public static int testDeleteRecord() {
        Record record = new Record();
        record.setTableName("users");
        record.setPrimaryKeys("id");
        record.set("id", "8");
        return dao.delete(record);
    }
    
    /**
     * 无参执行sql
     */
    public static int testExecute() {
        String sql = "update users set password='2' where id=2";
        return dao.execute(sql);
    }

    /**
     * 有参执行sql
     */
    public static int testExecute2() {
        String sql = "update users set password='wefw' where id=? and name=?";
        return dao.execute(sql, 4, "wef");
    }

    /**
     * 无参查询字符串
     * @return
     */
    public static String testQueryString() {
        return dao.find("select name from users where id=10",String.class);
    }

    /**
     * 有参查询字符串
     * @return
     */
    public static String testQueryString2() {
    	return dao.find("select name from users where id=?",String.class, 23);
    }

    /**
     * 根据id获取强类型实例User
     * @return
     */
    public static User getObject1() {
    	User user = dao.findById(User.class, 22);
        return user;
    }

    /**
     * 无参获取强类型实例User
     * @return
     */
    public static User getObject2() {
        User user = dao.cacheFind("cacheName", "key","select * from users", User.class);
        return user;
    }

    /**
     * 有参获取强类型实例User
     * @return
     */
    public static User getObject3() {
        User user = dao.find("select * from users where id=? and name=?", User.class, 2, "helelo");
        return user;
    }

    /**
     * 无参获取弱类型记录
     * @return
     */
    public static Record getRecord() {
        Record record = dao.find("select name,type from users where id=3", Record.class);
        return record;
    }

    /**
     * 有参获取弱类型记录
     * @return
     */
    public static Record getRecord2() {
        Record record = dao.find("select name,type from users where id =?", Record.class, 3);
        return record;
    }

    /**
     * 无参获取强类型实例列表
     * @return
     */
    public static List<User> getList() {
        List<User> users = dao.findList("select * from users where id<99", User.class);
        return users;
    }

    /**
     * 有参获取强类型实例列表
     * @return
     */
    public static List<User> getList2() {
        List<User> users = dao.findList("select * from users where id <? and password like ?", User.class, 5, "%'");
        return users;
    }

    /**
     * 无参获取弱类型记录列表
     * @return
     */
    public static List<Record> getRecordList() {
        List<Record> users = dao.findList("select name,type from users where id<777", Record.class);
        return users;
    }

    /**
     * 有参获取弱类型记录列表
     * @return
     */
    public static List<Record> getRecordList2() {
        List<Record> result = null;
        result = dao.findList("select name ,type from users where id <? and name like ?", Record.class, 57, "user");
        return result;
    }

    /**
     * 无参强类型分页
     */
    public static List<User> testGetPage() {
        String sql = "select * from users where id < 8 order by id desc";
        List<User> users = dao.paginate(sql, 2, 2, User.class);
        return users;
    }

    /**
     * 有参强类型分页
     */
    public static List<User> testGetPage2() {
        String sql = "select * from users where id < ? order by id desc";
        List<User> users = dao.paginate(sql, 2, 2, User.class, 18);
        return users;
    }

    /**
     * 无参弱类型分页
     */
    public static List<Record> testGetPage3() {
        String sql = "select id,name from users where id < 8 order by id desc";
        return dao.paginate(sql, 2, 2,Record.class);
    }

    /**
     * 有参弱类型分页
     */
    public static List<Record> testGetPage4() {
        String sql = "select id,name from users where id < ? order by id desc";
        return dao.paginate(sql, 2, 2, Record.class, 88);
    }

    public static void main(String[] args) {
      /*  testInsert();
        testInsert2();
        testExecute();
        testExecute2();*/
        //testQueryString();
        //testQueryString2();
        getObject1();
        getObject2();
        getObject2();
      /*  getObject3();
        getRecord();
        getRecord2();*/
        /* getList();
        getList2();
        getRecordList();
        getRecordList2();
        testUpdate();
        testUpdateRecord();
        testDelete();
        testDeleteRecord();
        testGetPage();
        testGetPage2();
        testGetPage3();
        testGetPage4();*/
    }
}
