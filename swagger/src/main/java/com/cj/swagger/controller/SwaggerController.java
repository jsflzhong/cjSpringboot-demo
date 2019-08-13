package com.cj.swagger.controller;

import javax.validation.Valid;

import com.cj.common.controller.BaseController;
import com.cj.common.entity.ResponseBean;
import com.cj.common.entity.StatusCode;
import com.cj.common.exception.IllegalParamException;
import com.cj.swagger.entity.User;
import com.cj.swagger.service.SwaggerService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/swaggerController")
@Slf4j
public class SwaggerController extends BaseController {

    @Autowired
    private SwaggerService swaggerService;

    /**
     * test swagger - one single parameter.
     *
     * Tested
     *
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
    @GetMapping("/getById/{id}")
    @ResponseBody
    @ApiOperation(value = "接口描述: Get user by id", httpMethod = "GET", response = User.class, authorizations = { @Authorization(value = "Authorization") })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", dataType = "int", required = true, defaultValue = "") //这个dataType不能用包装类Integer,否则无法通过swagger ui上面的try out的输入框校验.
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = User.class, responseContainer = ""), //response影响ui上的"Example Value".
            @ApiResponse(code = 500, message = "Failed, inner error"),
            @ApiResponse(code = 401, message = "Failed, param illegal")
    })
    public Object getById(
            @PathVariable(value = "id", required = true) Integer userId) {
        return swaggerService.getById(userId);
    }

    /**
     * 测试entity类型参数的swagger显示.
     * 测试参数对象的字段@NotNull
     *
     * 抽取接口参数检查方法.
     *
     * 注意:字符串值为空:""时, 检查可以通过. 只有值为null时才会被检查出来.
     *
     * @param user user
     * @return
     *  When success: UserT
     *  When failed: {"code":400002,"subCode":null,"message":"invalid parameters","value":null}
     */
    @GetMapping("/getByEntity")
    @ApiOperation(value = "接口描述: get user By Entity", httpMethod = "GET", response = User.class, authorizations = { @Authorization(value = "Authorization") })
    @ApiImplicitParams({ @ApiImplicitParam(name = "user", value = "用户entity")})
    @ApiResponses({
            @ApiResponse(code = 100000, message = "successful", response = ResponseBean.class),
            @ApiResponse(code = 400002, message = "invalid parameters") })
    public Object getByEntity(@Valid User user, BindingResult result) {
        try {
            checkParam(result);
            return ResponseBean.success(user);
        } catch (IllegalParamException e) {
            log.error("@@@Error in testParamNotNull(): {}", ExceptionUtils.getStackTrace(e));
        } catch (Exception e) {
            log.error("Error in testParamNotNull(): {}", ExceptionUtils.getStackTrace(e));
        }
        return new ResponseBean<>(
                StatusCode.INVALID_PARAMETERS.getCode(),
                StatusCode.INVALID_PARAMETERS.getMsg());
    }

}
