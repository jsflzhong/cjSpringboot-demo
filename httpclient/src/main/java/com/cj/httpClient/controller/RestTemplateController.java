package com.cj.httpClient.controller;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.service.AsyncRestService;
import com.cj.httpClient.service.RestService;
import com.cj.httpClient.vo.ServiceResult;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for RestTemplate in Spring.
 *
 * @author cj
 */
@Controller
@RestController
public class RestTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateController.class);

    @Autowired
    private RestTemplate restTemplate;

    /***********HTTP GET method  (Tested)*************/

    /**
     * 测试直接用RestTemplate的get方法调用.(无参)
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/get")
    public String hello() {
        String url = "http://localhost:8081/getApi";
        //在发出请求后,得到回应前,如果超过了在配置restTemplate这个bean时的setReadTimeout时间,则调用这里会抛异常:SocketTimeoutException
        JSONObject json = restTemplate.getForEntity(url, JSONObject.class).getBody();
        return json.toJSONString();
    }

    @RequestMapping("/getApi")
    public Object genJson() {
        JSONObject json = new JSONObject();
        json.put("result", "Hello get!");
        return json;
    }


    /**********HTTP POST method  (Tested)**************/

    /**
     * 测试直接用RestTemplate的post方法调用.(Json类型的参数)
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/post")
    public Object testPost() {
        String url = "http://localhost:8081/postApi";
        JSONObject postData = new JSONObject();
        postData.put("descp", "request for post");
        //在发出请求后,得到回应前,如果超过了在配置restTemplate这个bean时的setReadTimeout时间,则调用这里会抛异常:SocketTimeoutException
        //注意postForEntity方法的第三参,是JsonObject,这就决定了被调用的对方的接口的参数类型应该是JSONObject类型的,而且参数名不限.看下面的方法.
        JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
        return json.toJSONString();
    }

    @RequestMapping("/postApi")
    public Object iAmPostApi(@RequestBody JSONObject param) { //.(Json类型的参数)
        System.out.println("@@@Server accepting the json:" + param.toJSONString());
        param.put("result", "Hello post!");
        return param;
    }


    /**********########################## 测试封装好的service--同步调用 (Tested) ##########################**************/

    @Autowired
    private RestService restService;

    /****************************** get ********************************/

    /**
     * 1.调用以 Hashmap为参数的RestTemplate方法,则url不用做特殊处理.(两参)
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/get2")
    public Object get() {
        String url = "http://localhost:8081/getApi2";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("param1", "v1");
        paramMap.put("param2", "v2");

        //调用以 Hashmap为参数的方法,则url不用做特殊处理.
        ServiceResult serviceResult = restService.parseGetResult(paramMap, url);
        return serviceResult;
    }

    @RequestMapping("/getApi2")
    public Object getApi2(String param1, String param2) {
        JSONObject json = new JSONObject();
        json.put("param1", param1);
        json.put("param2", param2);
        json.put("result", "Hello get2!");
        return json;
    }

    /**
     * 2.注意:如果调用以 Hashmap为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.(两参,url需做占位符)
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/get3")
    public Object get3() {
        //注意:如果调用以 Hashmap为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
        String url = "http://localhost:8081/getApi3?param1={k1}&param2={k2}";
        ImmutableMap<String, Object> paramMap = ImmutableMap.of("k1", "v1", "k2", "v2");

        //注意:如果调用以 Hashmap为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
        ServiceResult serviceResult = restService.parseGetResult(paramMap, url);

        return serviceResult;
    }

    @RequestMapping("/getApi3")
    public Object getApi3(String param1, String param2) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("param1", param1);
        paramMap.put("param2", param2);
        return paramMap;
    }


    /****************************** post ********************************/

    /**
     * 调用封装好的post方法.(两参,字符串类型)
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/post2")
    public Object testPost2() {
        String url = "http://localhost:8081/postApi2";

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("channel", 1);
        map.add("goods_id", 2);

        //该方法中封装的RestTemplate的postForObject方法的第三参是String.class,这就决定了对方的接口参数类型必须是String,且参数名于map中的key要匹配.
        //看下面的方法参数签名.
        ServiceResult serviceResult = restService.parsePostResult(map, url);
        return serviceResult;
    }

    /**
     * 本次本方法的参数类型不能是 JSONObject 类型,
     * 这取决于上面的parsePostResult方法中的postForObject方法的第三参的类型. 看上述说明.
     * 见:com/cj/httpClient/service/impl/RestServiceImpl.java:142
     *
     * @param channel
     * @param goods_id
     * @return Object
     * @author cj
     */
    @RequestMapping("/postApi2")
    public Object postApi2(String channel, String goods_id) {
        return "1";
    }


    /****************************** post with headers ********************************/

    /**
     * 调用封装好的post方法.自定义header.(两参,字符串类型)
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/postWithHeader")
    public Object postWithHeader() {
        String url = "http://localhost:8081/postWithHeaderTarget";

        //Self defined header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8")); //Type of json.
        headers.add("Authorization", "testAuthorization");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel", 1);
        jsonObject.put("goods_id", 2);

        //该方法中封装的RestTemplate的postForObject方法的第三参是String.class,这就决定了对方的接口参数类型必须是String,且参数名于map中的key要匹配.
        //看下面的方法参数签名.
        return restService.parsePostResultWithHeader(headers, jsonObject, url);
    }

    /**
     * 本次本方法的参数类型不能是 JSONObject 类型,
     * 这取决于上面的parsePostResult方法中的postForObject方法的第三参的类型. 看上述说明.
     * 见:com/cj/httpClient/service/impl/RestServiceImpl.java:142
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/postWithHeaderTarget")
    public Object postWithHeaderTarget(HttpServletRequest request, HttpEntity<Map<String, Object>> httpEntity) {
        //获取body中的参数.key与上面JSON中封装的key匹配.
        Map<String, Object> body = httpEntity.getBody();
        Object channel = body.get("channel"); //1
        Object goods_id = body.get("goods_id"); //2

        //获取header中的参数.key与上面自定义的header一致. (有两种方式)
        //获取header中的参数的方式一:
        HttpHeaders headers = httpEntity.getHeaders();
        List<String> authorizationList = headers.get("Authorization");
        String value = ""; //testAuthorization
        if(authorizationList != null && authorizationList.size() > 0) {
            value = authorizationList.get(0);
        }
        logger.info("@@@用第一种方式获得的header中的自定义值:{}",value);

        //获取header中的参数的方式二:
        String header = request.getHeader("Authorization");  //testAuthorization
        logger.info("@@@用第二种方式获得的header中的自定义值:{}",header);

        logger.info("@@@header:{},channel:{},goods_id:{}", header, channel, goods_id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response","ok");
        return jsonObject; //这里必须返回JSON,否则调用方封装的方法里在解析这里的response时会报错.
    }


    /**********########################## 测试封装好的service--异步调用 (Untested) ##########################**************/

    @Autowired
    private AsyncRestService asyncRestService;

    /****************************** get ********************************/

    /**
     * 1.调用以 Hashmap为参数的RestTemplate方法,则url不用做特殊处理.
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/get4")
    public Object get4() {
        String url = "http://localhost:8081/getApi4";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("param1", "v1");
        paramMap.put("param2", "v2");

        //调用以 Hashmap为参数的方法,则url不用做特殊处理.
        ServiceResult serviceResult = asyncRestService.parseGetResult(paramMap, url);

        logger.info("@@@调用方已经[异步]的执行完,即将返回预设响应,而不是对方接口的响应值...");

        //3.由当前线程异步返回信息.
        return serviceResult;
    }

    @RequestMapping("/getApi4")
    public Object getApi4(String param1, String param2) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            Thread.currentThread().interrupt();
        }
        logger.info("@@@被调用的接口已苏醒...");

        JSONObject json = new JSONObject();
        json.put("param1", param1);
        json.put("param2", param2);
        json.put("result", "Hello get4!");
        return json;
    }


    /**
     * 2.注意:如果调用以map为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/get5")
    public Object get5() {
        //注意:如果调用以 Hashmap为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
        String url = "http://localhost:8081/getApi5?param1={k1}&param2={k2}";
        ImmutableMap<String, Object> paramMap = ImmutableMap.of("k1", "v1", "k2", "v2");

        //注意:如果调用以 Hashmap为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
        ServiceResult serviceResult = asyncRestService.parseGetResult(paramMap, url);

        logger.info("@@@调用方已经[异步]的执行完,即将返回预设响应,而不是对方接口的响应值...");
        return serviceResult;
    }

    @RequestMapping("/getApi5")
    public Object getApi5(String param1, String param2) { //v1,v2
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            Thread.currentThread().interrupt();
        }
        logger.info("@@@被调用的接口已苏醒...");

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("param1", param1);
        paramMap.put("param2", param2);
        return paramMap;
    }


    /****************************** post ********************************/

    /**
     * 2.注意:如果调用以map为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
     * Tested
     * Tested: set time out 5秒
     *
     * @return Object
     * @author cj
     */
    @RequestMapping("/post3")
    public Object post3() {
        String url = "http://localhost:8081/postApi3";
        LinkedMultiValueMap paramMap = new LinkedMultiValueMap();
        paramMap.add("k1", "v1");
        paramMap.add("k2", "v2");

        //注意:如果调用以 Hashmap为参数的方法,则url需要做特殊处理,要指明对方接口的形参名.具体进去看方法注释.
        ServiceResult serviceResult = asyncRestService.parsePostResult(paramMap, url);

        logger.info("@@@调用方已经[异步]的执行完,即将返回预设响应,而不是对方接口的响应值...");
        return serviceResult;
    }

    @RequestMapping("/postApi3")
    public Object postApi3(String k1, String k2) { //v1,v2
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            Thread.currentThread().interrupt();
        }
        logger.info("@@@被调用的接口已苏醒...");

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("park1am1", k1);
        paramMap.put("k2", k2);
        return paramMap;
    }

}
