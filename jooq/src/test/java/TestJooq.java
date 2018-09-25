import com.cj.jooq.Application;
import com.cj.jooq.jooq.tables.pojos.CustDeviceInfo;
import com.cj.jooq.jooq.tables.pojos.UUser;
import com.cj.jooq.service.TestJooqService;
import com.cj.jooq.service.TestUserServiceFromBase;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Jian.Cui on 2018/9/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestJooq {
    Logger logger = LoggerFactory.getLogger(TestJooq.class);

    @Autowired
    TestJooqService testJooqService;
    @Autowired
    TestUserServiceFromBase testUserServiceFromBase;

    /**
     * Find all
     *
     * @deprecated @deprecated This is just for test. I should use 'TestUserServiceFromBase' for development.
     * Tested
     */
    @Test
    @Deprecated
    public void test1() {
        List<UUser> all = testJooqService.findAll();
        logger.info("@@@test1: {}", all);
    }

    /**
     * Insert
     *
     * @deprecated This is just for test. I should use 'TestUserServiceFromBase' for development.
     * Tested
     */
    @Test
    @Deprecated
    public void test2() {
        UUser user = new UUser();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUsername("test1");
        user.setRealname("testRealName1");
        user.setEmail("test1@1.com");
        user.setPassword("pass1");
        String save = testJooqService.save(user);
        logger.info("@@@test2: {}", save);
    }


    /*********************************TestBase******************************************/

    /**
     * Find all
     * Tested
     */
    @Test
    public void test3() {
        List<UUser> userList = testUserServiceFromBase.findAll();
        logger.info("@@@userList:{}", userList);
    }

    /**
     * Find by id
     * It works even the real type of id in the table is not Integer but String.
     * Tested
     */
    @Test
    public void test4() {
        Integer id = 1;
        Optional<UUser> user = testUserServiceFromBase.findOptionalById(id);
        logger.info("@@@user:{}", user);
    }

    /**
     * Find by field
     * The class "DSL" has a lot of useful things such as Field,condition,row...
     * Tested
     */
    @Test
    public void test5() {
        String userName = "admin";
        List<UUser> user = testUserServiceFromBase.find(DSL.field("username"), userName);
        logger.info("@@@user:{}", user);
    }

    /**
     * findOptional by field
     * The Optional class provides a lot of api about null or empty judgement.
     * Tested
     */
    @Test
    public void test6() {
        String userName = "admin";
        Optional<UUser> user = testUserServiceFromBase.findOptional(DSL.field("username"), userName);
        logger.info("@@@user:{}", user);
    }

    /**
     * findOptional by condition
     * Tested
     */
    @Test
    public void test7() {
        String condition = "username='admin'";
        Optional<UUser> user = testUserServiceFromBase.findOptional(DSL.condition(DSL.sql(condition)));
        logger.info("@@@user:{}", user);
    }

    /**
     * find by multi conditions
     * DO NOT use "and" in the condition because jooq already do it for me.
     * Tested
     */
    @Test
    public void test8() {
        String condition1 = "username='admin'";
        String condition2 = "realname='管理员'";
        List<UUser> userList = testUserServiceFromBase.find(DSL.condition(DSL.sql(condition1)), DSL.condition(DSL.sql(condition2)));
        logger.info("@@@user:{}", userList);
    }

    /**
     * find by condition and specific Class
     * Tested
     */
    @Test
    public void test9() {
        String condition1 = "username='admin'";
        List<UUser> userList = testUserServiceFromBase.find(DSL.condition(DSL.sql(condition1)), UUser.class);
        logger.info("@@@user:{}", userList);
    }

    /**
     * count all
     * Tested
     */
    @Test
    public void test10() {
        long lines = testUserServiceFromBase.countAll();
        logger.info("@@@lines:{}", lines);
    }

    /**
     * insert
     * Tested
     */
    @Test
    public void test11() {
        UUser user = new UUser();
        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        user.setUsername("test1");
        user.setRealname("testRealName1");
        user.setEmail("test1@1.com");
        user.setPassword("pass1");
        boolean result = testUserServiceFromBase.insert(user);
        logger.info("@@@result:{}", result);
    }

    /**
     * insert and return the primary key of this data.
     * Attention: The type of id should be Integer or sth related in this function.
     * If it is a String, then there will be an exception even i use a Integer as the id.
     * Tested --- Exception.
     */
    @Test
    public void test12() {
        UUser user = new UUser();
        user.setId("123");
        user.setUsername("test2");
        user.setRealname("testRealName2");
        user.setEmail("test2@1.com");
        Integer integer = testUserServiceFromBase.insertAndReturnId(user); //Exception occurred.
        logger.info("@@@id:{}", integer);
    }

    /**
     * insert, or, if the primary key is already existed,then update.
     * Tested
     */
    @Test
    public void test13() {
        HashMap<Field<?>, Object> paramMap = new HashMap<>();
        paramMap.put(DSL.field("id"),"122");
        paramMap.put(DSL.field("userName"),"test4");
        paramMap.put(DSL.field("email"),"2@2.com");
        paramMap.put(DSL.field("realname"),"testRealName4");
        Integer save = testUserServiceFromBase.save(paramMap);
        logger.info("@@@save:{}", save);
    }

    /**
     * Update
     * Exception occurred because of the type of the primary key is not Integer.
     * Tested
     */
    @Test
    public void test14() {
        //The type of primary key is String ,which causes an exception.
        try {
            UUser user = new UUser();
            user.setId("123");
            user.setUsername("test2");
            user.setRealname("testRealName2");
            user.setEmail("test2@1.com");
            //Exception occurred because of the type of the primary key is not Integer.
            boolean result = testUserServiceFromBase.update(user);
            logger.info("@@@result:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
