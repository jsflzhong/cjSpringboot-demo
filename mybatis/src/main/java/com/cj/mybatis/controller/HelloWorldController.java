package com.cj.mybatis.controller;

import com.cj.common.entity.ResponseBean;
import com.cj.common.entity.StatusCode;
import com.cj.mybatis.domain.UserT;
import com.cj.mybatis.exception.IllegalParamException;
import com.cj.mybatis.service.UserTService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import javax.validation.Valid;

/**
 * Test Mybatis
 *
 * @author cj
 */
@Controller
@RequestMapping("helloWorldController")
@RestController
public class HelloWorldController extends BaseController{

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
     * http://localhost:8086/helloWorldController/getById?userId=1
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
     * getById with pathVariable
     * Tested
     * http://localhost:8086/helloWorldController/getById_pathVariable/1 (无?key=value)
     *
     * @return json
     * @author cj
     */
    @RequestMapping("/getById_pathVariable/{id}")
    @ResponseBody
    public Object getById_pathVariable(
            @PathVariable(value = "id", required = true) Integer userId) {
        UserT userT = userTService.selectByKey(userId);
        return userT;
    }

    /**
     * getByIdWithoutDB  for unit test
     *
     * @return json
     * @author cj
     */
    @RequestMapping(value = "/getByUsernameForUnitTest" ,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object getByUsernameForUnitTest(String userName) {
        return userTService.getByUsernameForUnitTest(userName);
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
        example.selectProperties("id", "userName");

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
        example.selectProperties("id", "userName");

        // Preparing: SELECT id , user_name FROM user_t WHERE ( user_name like ? )
        List<UserT> userTList = userTService.selectByExample(example);

        //[{"id":2,"userName":"test2","password":null,"age":null},{"id":3,"userName":"test21","password":null,"age":null}]
        return userTList;
    }

    /**
     * getCount
     * Tested
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

    /**
     * test swagger
     * Tested
     * http://localhost:8086/swagger-ui.html
     * <p>
     * API详细说明
     * 注释汇总
     * <p>
     * 作用范围	            API	                使用位置
     * 对象属性	            @ApiModelProperty	用在出入参数对象的字段上
     * 协议集描述	        @Api	            用于controller类上
     * 协议描述	            @ApiOperation	    用在controller的方法上
     * Response集        	@ApiResponses	    用在controller的方法上
     * Response	            @ApiResponse	    用在 @ApiResponses里边
     * 非对象参数集	        @ApiImplicitParams	用在controller的方法上
     * 非对象参数描述	    @ApiImplicitParam	用在@ApiImplicitParams的方法里边
     * 描述返回对象的意义	@ApiModel	        用在返回对象类上
     *
     * @return json
     * @author cj
     */
    @GetMapping("/testSwagger/{id}")
    @ResponseBody
    @ApiOperation(value = "接口描述: Get user by id", httpMethod = "GET", response = UserT.class, authorizations = { @Authorization(value = "Authorization") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true, defaultValue = "") //这个dataType不能用包装类Integer,否则无法通过swagger ui上面的try out的输入框校验.
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = UserT.class, responseContainer = ""), //response影响ui上的"Example Value".
            @ApiResponse(code = 500, message = "Failed, inner error"),
            @ApiResponse(code = 401, message = "Failed, param illegal")
    })
    public Object testSwagger(
            @PathVariable(value = "id", required = true) Integer userId) {
        return userTService.selectByKey(userId);
    }

    /**
     * 测试参数对象的字段@NotNull
     * 抽取接口参数检查方法.
     *
     * 注意:字符串值为空:""时, 检查可以通过. 只有值为null时才会被检查出来.
     *
     * @param userT userT
     * @return
     *  When success: UserT
     *  When failed: {"code":400002,"subCode":null,"message":"invalid parameters","value":null}
     */
    @GetMapping("/testParamNotNull")
    @ApiOperation(value = "接口描述: testParamNotNull", httpMethod = "GET", response = UserT.class, authorizations = { @Authorization(value = "Authorization") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true, defaultValue = ""),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "string", required = true) })
    @ApiResponses({
            @ApiResponse(code = 100000, message = "successful", response = ResponseBean.class),
            @ApiResponse(code = 400002, message = "invalid parameters") })
    public Object testParamNotNull(@Valid UserT userT, BindingResult result) {
        try {
            checkParam(result);
            return ResponseBean.success(userT);
        } catch (IllegalParamException e) {
            logger.error("@@@Error in testParamNotNull(): {}", ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            logger.error("Error in testParamNotNull(): {}", ExceptionUtils.getStackTrace(e));
        }
        return new ResponseBean<>(
                StatusCode.INVALID_PARAMETERS.getCode(),
                StatusCode.INVALID_PARAMETERS.getMsg());
    }

    /**
     * 测试@RequestBody
     * @RequestBody: 主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
     * GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交。
     * 在后端的同一个接收方法里，@RequestBody与@RequestParam()可以同时使用，@RequestBody最多只能有一个，而@RequestParam()可以有多个。
     *
     * 请求:
     * POST方式:{"id":"1","userName":"2"}
     *
     * @param userT userT
     * @param result result
     * @return
     * When success:
     *  {
     *     "code": 100000,
     *     "subCode": null,
     *     "message": "successful",
     *     "value": {
     *         "id": 1,
     *         "userName": "2",
     *         "pswd": null
     *     }
     *  }
     */
    @PostMapping("/testRequestBody")
    public Object testRequestBody(@Valid @RequestBody UserT userT, BindingResult result) {
        try {
            checkParam(result);
            return ResponseBean.success(userT);
        } catch (IllegalParamException e) {
            logger.error("@@@Error in testParamNotNull(): {}", ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            logger.error("Error in testParamNotNull(): {}", ExceptionUtils.getStackTrace(e));
        }
        return new ResponseBean<>(
                StatusCode.INVALID_PARAMETERS.getCode(),
                StatusCode.INVALID_PARAMETERS.getMsg());
    }

}
