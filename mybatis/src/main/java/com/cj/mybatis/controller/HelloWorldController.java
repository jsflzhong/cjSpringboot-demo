package com.cj.mybatis.controller;

import com.cj.mybatis.domain.UserT;
import com.cj.mybatis.service.UserTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Test Mybatis
 *
 * @author cj
 */
@Controller
@RequestMapping("helloWorldController")
public class HelloWorldController {

    private final static Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private UserTService userTService;

    /**
     * hello world handler
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String index() {
        return "hello world!!!~~";
    }

    /**
     * getById
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/getById")
    @ResponseBody
    public Object getById(Integer userId) {
        UserT userT = userTService.selectByKey(userId);
        return userT;
    }

    /**
     * getByExample
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/getByExample")
    @ResponseBody
    public Object getByExample() {
        Example example = new Example(UserT.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("age", 11)
                .andEqualTo("userName", "test2");
        List<UserT> userTList = userTService.selectByExample(example);
        return userTList;
    }

    /**
     * GetByExample
     * Just get the specific fields to provide better performance!
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/getByExampleWithSpecificFields")
    @ResponseBody
    public Object getByExampleWithSpecificFields() {
        Example example = new Example(UserT.class);

        //condition
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("age", 11)
                .andEqualTo("userName", "test2");

        //Specific fileds
        example.selectProperties("id","userName");

        //SELECT id , user_name FROM user_t WHERE ( age = ? and user_name = ? )
        List<UserT> userTList = userTService.selectByExample(example);

        //[{"id":2,"userName":"test2","password":null,"age":null}]
        return userTList;
    }

    /**
     * GetByExampleWithLike
     * Use keyword 'like' as condition.
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/getByExampleWithLike")
    @ResponseBody
    public Object getByExampleWithLike() {
        Example example = new Example(UserT.class);

        //condition
        Example.Criteria criteria = example.createCriteria();
        //I should define the symbol '%' myself. It can be the prefix or suffix to do the different query!
        criteria.andLike("userName", "test2%");

        //Specific fileds
        example.selectProperties("id","userName");

        // Preparing: SELECT id , user_name FROM user_t WHERE ( user_name like ? )
        List<UserT> userTList = userTService.selectByExample(example);

        //[{"id":2,"userName":"test2","password":null,"age":null},{"id":3,"userName":"test21","password":null,"age":null}]
        return userTList;
    }

    /**
     * getCount
     *Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/getCount")
    @ResponseBody
    public Object getCount(Integer id) {
        UserT userT = new UserT();
        userT.setId(id);
        int i = userTService.selectCount(userT);
        return i;
    }

    /**
     * insert
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(Integer id) {
        UserT userT = new UserT();
        userT.setId(id);
        userT.setAge(13);
        userT.setUserName("testName");
        userT.setPassword("testPswd");
        int lines = userTService.save(userT);
        return lines;
    }

    /**
     * deleteByKey
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/deleteByKey")
    @ResponseBody
    public Object deleteByKey(Integer id) {
        int lines = userTService.delete(id);
        return lines;
    }

    /**
     * Update all the fields include the null value.
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/updateAll")
    @ResponseBody
    public Object updateAll(Integer id) {
        UserT userT = new UserT();
        userT.setPassword("999");
        userT.setUserName("999");
        userT.setId(id);
        userTService.updateAll(userT);
        int lines = userTService.updateAll(userT);
        return lines;
    }

    /**
     * Update all the fields but the null value.
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/updateNotNull")
    @ResponseBody
    public Object updateNotNull(Integer id) {
        UserT userT = new UserT();
        userT.setPassword("999");
        userT.setUserName("999");
        userT.setId(id);
        int lines = userTService.updateNotNull(userT);
        return lines;
    }

    /**
     * Test transaction.
     * Tested
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/testTransaction")
    @ResponseBody
    public Object testTransaction(Integer id) {
        UserT userT = new UserT();
        userT.setPassword("999");
        userT.setUserName("999");
        userT.setId(id);
        int lines = userTService.testTransaction(userT);
        return lines;
    }

}
