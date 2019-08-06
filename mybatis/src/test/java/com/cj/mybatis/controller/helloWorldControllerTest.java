package com.cj.mybatis.controller;

import java.util.ArrayList;
import java.util.List;

import com.cj.common.entity.ResponseBean;
import com.cj.common.entity.StatusCode;
import com.cj.mybatis.domain.UserT;
import com.cj.mybatis.service.UserTService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Mock了Service层. 测试Controller接口层.
 */
@RunWith(MockitoJUnitRunner.class)
public class helloWorldControllerTest {

    private MockMvc mockMvc;

    //要测试的目标Controller.该字段名需要与下面的MockMvcBuilders.standaloneSetup()中的名字匹配.
    @InjectMocks
    HelloWorldController helloWorldController;

    //Mock了业务Service,而非注入
    @Mock
    UserTService userTService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
    }

    /**
     * 测试HTTP REST接口, mock service,而不会测试业务service方法.
     * @throws Exception
     */
    @Test
    public void getContractStatus_THEN_RETURN_SUCCESSFUL_NO_DATA() throws Exception {
        //手动封装想要的返回值
        List<UserT> statusList = new ArrayList<>();
        ResponseBean<List<UserT>> responseBean = ResponseBean.success(statusList);
        //此行执行后并不会真正调用service方法. 此行的目的是,当调用when()里面的业务service方法时,直接返回thenReturn()里面的值,而不会真的去调用那个service方法.(已断点测试)
        when(userTService.getByUsernameForUnitTest(anyString())).thenReturn(responseBean);

        this.mockMvc
                .perform(get("/helloWorldController/getByUsernameForUnitTest?userName=username1")) //此行执行后会真正调用目标controller方法
                .andDo(print()) //打印请求头, 请求体,请求类型, 响应类型, 响应体数据 等信息.
                .andExpect(status().isOk()) //上一行打印出来了响应的status=200
                .andExpect(content().contentType("application/json;charset=UTF-8"));//上上行打印出来了:Headers = {Content-Type=[application/json;charset=UTF-8]}

        verify(userTService, times(1)).getByUsernameForUnitTest("username1"); //需要和上面的perform()中的参数值一致.
        assertEquals(StatusCode.SUCCESSFUL_NO_DATA.getCode(), responseBean.getCode()); //responseBean在上面已经手动封装好了.
    }

    @Test
    public void getContractStatus_THEN_RETURN_SUCCESSFUL() throws Exception {
        List<UserT> statusList = new ArrayList<>();
        UserT userT = new UserT();
        userT.setId(1);
        userT.setAge(2);
        userT.setPassword("a");
        userT.setUserName("abc");

        UserT userT2 = new UserT();
        userT2.setId(2);
        userT2.setAge(3);
        userT2.setPassword("b");
        userT2.setUserName("abcd");

        statusList.add(userT);
        statusList.add(userT2);

        ResponseBean<List<UserT>> responseBean = ResponseBean.success(statusList);
        when(userTService.getByUsernameForUnitTest(anyString())).thenReturn(responseBean);

        this.mockMvc.perform(get("/helloWorldController/getByUsernameForUnitTest?userName=username1")).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"));
        verify(userTService, times(1)).getByUsernameForUnitTest("username1");
        assertEquals(StatusCode.SUCCESSFUL.getCode(), responseBean.getCode());
    }

    @Test
    public void getContractStatus_THEN_THROW_EXCEPTION_AND_RETURN_INTERNAL_ERROR() throws Exception {
        //模拟业务service的方法.
        ResponseBean<List<UserT>> responseBean = new ResponseBean<>(
                StatusCode.INTERNAL_ERROR.getCode(), StatusCode.INTERNAL_ERROR.getMsg());
        when(userTService.getByUsernameForUnitTest(anyString())).thenReturn(responseBean);

        //对REST接口发起HTTP请求
        this.mockMvc.perform(get("/helloWorldController/getByUsernameForUnitTest?userName=username1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        verify(userTService, times(1)).getByUsernameForUnitTest("username1");
        assertEquals(StatusCode.INTERNAL_ERROR.getCode(), responseBean.getCode());
    }

}
