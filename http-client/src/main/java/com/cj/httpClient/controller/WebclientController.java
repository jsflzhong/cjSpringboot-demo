package com.cj.httpClient.controller;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.vo.ServiceResult;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jian.Cui on 2018/11/30.
 * Test the WebClient in Spring5 instead of RestTemplate.
 * All Tested.
 *
 * @author Jian.Cui
 */
@RestController
@RequestMapping("/webclientController")
public class WebclientController {

    private Logger logger = LoggerFactory.getLogger(WebclientController.class);

    private WebClient webclient = WebClient.builder()
            .baseUrl("http://127.0.0.1:8080") //This can just be defined here!
            .clientConnector(new ReactorClientHttpConnector(
                    options -> options.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                            .compression(true)
                            .afterNettyContextInit(ctx -> {
                                ctx.addHandlerLast(new ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS));
                            })))
            //.defaultHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)")
            //.defaultCookie("ACCESS_TOKEN", "test_token")
            .build();

    /**
     * If you want to test the TIMEOUT function, please DO NOT use these target interfaces in the same project,or it NOT works!
     *
     * @param serviceResult serviceResult
     * @return ServiceResult
     * @author cj
     */
    @GetMapping("/targetGet")
    public Object TargetGet(ServiceResult serviceResult) {
        logger.info("@@@TargetGet...{}", serviceResult);

        return new ServiceResult().setMessage("targetGet success").setCode("0");
    }

    /**
     * If you want to test the TIMEOUT function, please DO NOT use these target interfaces in the same project,or it NOT works!
     *
     * @param serviceResult serviceResult
     * @return ServiceResult
     * @author cj
     */
    @PostMapping("/targetPost")
    public Object TargetPost(ServiceResult serviceResult) {
        logger.info("@@@TargetPost...{}", serviceResult);

        return new ServiceResult().setMessage("targetPost success").setCode("0");
    }

    /**
     * If you want to test the TIMEOUT function, please DO NOT use these target interfaces in the same project,or it NOT works!
     *
     * @param serviceResult serviceResult
     * @return ServiceResult
     * @author cj
     */
    @PostMapping("/targetPostWithRequestBody")
    public Object TargetPostWithRequestBody(@RequestBody ServiceResult serviceResult) {
        logger.info("@@@TargetPost...{}", serviceResult);

        return new ServiceResult().setMessage("targetPost success").setCode("0");
    }

    /**
     * 1.Get with WebClient - blocked.
     * Timeout works.
     * Tested
     *
     * @return ServiceResult
     * @author cj
     */
    @RequestMapping("/webclientGet")
    public Object useWebclientGet() {
        logger.info("@@@useWebclientGet is running...");

        try {
            Mono<ServiceResult> response = webclient.get().uri("/targetGet?code={code}&message={message}", "testCode", "testMsg")
                    .retrieve()
                    //.onStatus(HttpStatus::isError, res -> Mono.error(new RuntimeException("###MonoError:" + res.statusCode().value() + ":" + res.statusCode().getReasonPhrase())))
                    .bodyToMono(ServiceResult.class)
                    //.timeout(Duration.of(10, ChronoUnit.SECONDS))
                    .doAfterSuccessOrError((obj, ex) -> {
                        logger.info("@@@Obj from WebclientGet :{}", obj);
                        if (ex != null) {
                            logger.error("###HttpError:{}", ExceptionUtils.getStackTrace(ex));
                        }
                    });
            //.doOnError(e -> logger.error("###doOnError:{}", e.getMessage()));

            Optional<ServiceResult> serviceResult = response.blockOptional();
            ServiceResult result = serviceResult.get();
            logger.info("@@@webclientGet is done. {}", result);

            return result;
        } catch (Exception e) {
            logger.error("###HttpError,exception:{}", ExceptionUtils.getStackTrace(e));
        }

        return new ServiceResult().setCode("1").setMessage("Exception in server!");
    }

    /**
     * 2.Post with WebClient - blocked.
     * Type of param: Map. (It DOES NOT work when the target http interface use : @RequestBody on the param).
     * Timeout works.
     * Tested
     *
     * @return ServiceResult
     * @author cj
     */
    @RequestMapping("/webclientPostMap")
    public Object useWebclientPost_param_map() {
        logger.info("@@@useWebclientPost_map is running...");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>(2);
        formData.add("code", "testCode");
        formData.add("message", "testMessage");

        ServiceResult demoObj = null;
        try {
            Mono<ServiceResult> response = webclient.post().uri("/targetPost")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ServiceResult.class).timeout(Duration.of(10, ChronoUnit.SECONDS));

            demoObj = response.block();
            logger.info("@@@Obj from useWebclientPost_map :{}", demoObj);
        } catch (Exception e) {
            logger.error("###HttpError,param:{},exception:{}", formData, ExceptionUtils.getStackTrace(e));
        }

        return demoObj;
    }

    /**
     * 3.Post with WebClient - blocked.
     * Type of param: JSON. (It DOES work when the target http interface use : @RequestBody on the param).
     * Timeout works.
     * Tested
     *
     * @return ServiceResult
     * @author cj
     */
    @RequestMapping("/webclientPostJson")
    public Object useWebclientPost_param_json() {
        logger.info("@@@useWebclientPost_param_json is running...");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "testCode");
        jsonObject.put("message", "testMessage");

        try {
            Mono<ServiceResult> response = webclient.post().uri("/targetPostWithRequestBody")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(BodyInserters.fromObject(jsonObject.toString()))
                    .retrieve()
                    .bodyToMono(ServiceResult.class)
                    .doAfterSuccessOrError((obj, ex) -> { //Still can do this when POST.
                        logger.info("@@@Obj from useWebclientPost_param_json :{}", obj);
                        Optional.ofNullable(ex).ifPresent(o ->
                                logger.error("###HttpError:{}", ExceptionUtils.getStackTrace(o)));
                    });

            return response.block();
        } catch (Exception e) {
            logger.error("###HttpError,param:{},exception:{}", jsonObject, ExceptionUtils.getStackTrace(e));
        }
        return new ServiceResult().setCode("1").setMessage("Exception in server");
    }

    /**
     * 4.Post with WebClient - blocked.
     * Type of param: Java bean. (It DOES work when the target http interface use : @RequestBody on the param).
     * Timeout works.
     * Tested
     *
     * @return ServiceResult
     * @author cj
     */
    @RequestMapping("/webclientPostBean")
    public Object useWebclientPost_param_bean() {
        logger.info("@@@useWebclientPost_param_bean is running...");

        ServiceResult javaBean = new ServiceResult().setMessage("testMsg").setCode("0");

        try {
            Mono<ServiceResult> response = webclient.post().uri("/targetPostWithRequestBody")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    //.body(Mono.just(javaBean),ServiceResult.class) //This can also go.
                    .syncBody(javaBean)
                    .retrieve()
                    .bodyToMono(ServiceResult.class)
                    .doAfterSuccessOrError((obj, ex) -> { //Still can do this when POST.
                        logger.info("@@@Obj from useWebclientPost_param_bean :{}", obj);
                        Optional.ofNullable(ex).ifPresent(o ->
                                logger.error("###HttpError:{}", ExceptionUtils.getStackTrace(o)));
                    });

            return response.block();
        } catch (Exception e) {
            logger.error("###HttpError,param:{},exception:{}", javaBean, ExceptionUtils.getStackTrace(e));
        }
        return new ServiceResult().setCode("1").setMessage("Exception in server");
    }

    /**
     * 5.Post with WebClient - unblocked.
     * Type of param: Java bean. (It DOES work when the target http interface use : @RequestBody on the param).
     * Timeout works.(Attention: The timeout exception in Async environment is special,see the annotation in the code.)
     * Tested
     *
     * @return ServiceResult
     * @author cj
     */
    @RequestMapping("/webclientPostBean_unblock")
    public Object useWebclientPost_param_bean_unblock() {
        logger.info("@@@useWebclientPost_param_bean_unblock is running...");

        ServiceResult javaBean = new ServiceResult().setMessage("testMsg").setCode("0");

        try {
            Mono<ServiceResult> response = webclient.post().uri("/targetPostWithRequestBody")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    //.body(Mono.just(javaBean),ServiceResult.class) //This can also go.
                    .syncBody(javaBean)
                    .retrieve()
                    .bodyToMono(ServiceResult.class)
                    .doAfterSuccessOrError((obj, ex) -> { //Still can do this when POST.
                        //3.This is called at last.
                        logger.info("@@@Obj from useWebclientPost_param_bean_unblock :{},i can also handle the body here.", obj);
                        //5.这里的意义出来了: 异步请求时,先返回响应后,下面的catch无法再抓取异常,但是这里可以.因为这里是与请求一体的处理方式.
                        Optional.ofNullable(ex).ifPresent(o ->
                                logger.error("###HttpError:{}", ExceptionUtils.getStackTrace(o)));
                    });

            //2.Async request then.
            response.subscribe(body ->
                    logger.info("@@@Async response body is:{},i can handle the body here.", body));

            //1.Return immediately first.
            return new ServiceResult().setCode("0").setMessage("async response success!");
        } catch (Exception e) {
            //4.注意,如果上面第1步返回了,第2步异步请求超时了,那么这里将无法再抓到异常,因为已经返回了;但可以在上面第5步抓到!
            logger.error("###HttpError,param:{},exception:{}", javaBean, ExceptionUtils.getStackTrace(e));
        }
        return new ServiceResult().setCode("1").setMessage("Exception in server");
    }

    public static void main(String[] args) {
        Exception e = null;
        Optional.ofNullable(e).ifPresent(o -> System.out.println("@@@NOt null: " + o.getMessage()));
        System.out.println("@@@null: " + Optional.ofNullable(e).orElse(new RuntimeException("111")));
    }

}
