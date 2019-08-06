package com.cj.mybatis.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试REST接口的 get(有参/无参), post, put
 * 没有Mock service层, 所以会真实的通过Controller调用service执行业务.
 *
 * 注意: POST和PUT的请求,需要指定contentType和accept
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JunitControllerTest {

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws Exception {
        RequestBuilder request;

        // 1、get(无参) 查询用户列表list
        request = get("/user");
        mvc.perform(request) //执行调用
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"userName\":\"abc\",\"pswd\":\"a\"},{\"id\":2,\"userName\":\"abcd\",\"pswd\":\"b\"}]")));

        // 2、post 新建一个用户
        request = post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"userName\":\"zxc\",\"age\":18}");
        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", equalTo("zxc")));

        // 3、get查一下user列表，应该不为空 (如果第一步查询的是空白的表,第二步新建了一个用户, 那么这一步就查出来用户不为空了. 但是本次第一步就假设能查出来用户了, 所以注释掉该步骤.
       /*request = get("/user");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(not("")))
                .andExpect(content().string(not("[]")));*/

        // 4、get(有参) 查一个id等于1的用户，注意age字段由于@JsonIgnore而不会返回.
        request = get("/user/{id}", 1);
        mvc.perform(request)
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.content().string(not(""))) //api有问题.
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userName").value("abc"));

        // 4、put 更新用户zxc
        request = put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"userName\":\"zxc\",\"age\":20,\"pswd\":\"eee\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.pswd").value("eee"))
                .andExpect(jsonPath("$.userName").value("zxc"));
    }
}