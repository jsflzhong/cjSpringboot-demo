package com.cj.spring.exceptionHandler;

import javax.validation.constraints.NotNull;

import com.cj.common.exception.IllegalParamException;
import com.cj.spring.common.entity.UserT;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 在接口层对异常的处理.
 */
@RestController
@RequestMapping("/exception")
@Validated
public class ExceptionHandleController {

    /**
     * 测试在POST请求中, 如果后端直接抛出非检查异常而不处理, 客户端会怎么样.
     * (没有统一异常处理的情况下)
     *
     * 结果:
     * 在不定义异常处理器的前提下,这个POST的响应,在postman中看到的结果是:
     * Status:500
     * body:
     * {
     *     "timestamp": "2019-08-15T01:45:31.141+0000",
     *     "status": 500,
     *     "error": "Internal Server Error",
     *     "message": "@@@test",
     *     "path": "/rest/testException"
     * }
     *
     * @param userT
     * @return
     */
    @PostMapping("/testExceptionPOST")
    @ResponseStatus(HttpStatus.CREATED)
    public UserT NoUnifiedHandle_testExceptionPOST(UserT userT) {
        throw new IllegalParamException("@@@test");
    }

    /**
     *  (没有统一异常处理的情况下)
     *  测试在GET请求中, 如果后端直接抛出非检查异常而不处理, 客户端会怎么样.
     *
     * 结果: 同上POST
     * 在不定义异常处理器的前提下,这个POST的响应,在postman中看到的结果是:
     * Status:500
     * body:
     * {
     *     "timestamp": "2019-08-15T01:52:53.005+0000",
     *     "status": 500,
     *     "error": "Internal Server Error",
     *     "message": "@@@test",
     *     "path": "/rest/testExceptionGET"
     * }
     *
     * @param userT
     * @return
     */
    @GetMapping("/testExceptionGET")
    @ResponseStatus(HttpStatus.CREATED)
    public UserT NoUnifiedHandle_testExceptionGET(UserT userT) {
        throw new IllegalParamException("@@@test");
    }

    /**需要自定义异常处理器.可以统一定义在common模块中.  @ExceptionHandler todo*/

    /**
     * 测试对非对象型的参数非空.
     * 需要配合类头的@Validated + @NotNull
     *
     * 会抛异常: javax.validation.ConstraintViolationException, 会被异常处理器捕获.
     */
    @RequestMapping("/testNotNullParam1")
    public Object testNotNullParam1(@NotNull String param1) {
        return "1";
    }

    /**
     * 测试对非对象型的参数非空.
     * 不需要配合类头的@Validated, 只需要用@RequestParam在参数前即可.
     *
     * 会抛异常:org.springframework.web.bind.MissingServletRequestParameterException, 会被异常处理器捕获.
     *
     */
    @RequestMapping("/testNotNullParam2")
    public Object testNotNullParam2(@RequestParam String param2) {
        return "1";
    }


}
