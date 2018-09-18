package com.cj.httpClient.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cj.httpClient.service.AsyncRestService;
import com.cj.httpClient.vo.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cj
 */
@Service
public class AsyncRestServiceImpl extends BaseService implements AsyncRestService {

    protected Logger logger = LoggerFactory.getLogger(AsyncRestServiceImpl.class);

    @Autowired
    private AsyncRestTemplate restTemplate;

    /**
     * 发送get请求(url需要用占位符)
     *
     * @param map 参数map
     * @param url 请求地址，参数拼接在链接中，用通配符代表
     *            示例 :
     *            Map<String, Object> map = new HashMap<String, Object>;
     *            map.put("channel", 1);
     *            map.put("goodsId", 2);
     *            String url = "http://localhost:8083/goods/goodsIndex?channel={channel}&goods_id={goodsId}";
     *            根据实际情况自己添加参数
     * @return
     */
    @Override
    public ServiceResult parseGetResult(Map<String, Object> map, String url) {

        ServiceResult result = new ServiceResult();
        ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.getForEntity(url, String.class, map);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity>() {
            public void onSuccess(ResponseEntity resp) {
                String sResult = resp.getBody().toString();
                if (!StringUtils.isEmpty(sResult)) {
                    JSONObject jResult = JSONObject.parseObject(sResult);
                    result.setContent(jResult);
                    result.setCode(CODE_SUCCESS);
                    result.setMessage("success");
                    logger.info("url:{}, map:{} 请求成功", url, map);
                    //业务处理--省略......
                } else {
                    this.onFailure(new Throwable("请求结果为空"));
                }
            }

            public void onFailure(Throwable t) {
                result.setMessage(t.getMessage());
                result.setCode(CODE_ERROR);
                t.printStackTrace();
                logger.info("url:{}, map:{} 请求失败", url, map);
            }
        });

        return result;
    }

    /**
     * 发送get请求
     *
     * @param url 请求地址
     *            请求为纯字符串，可以是无参数的get请求，也可以是将参数的K V都拼接好传入
     *            示例
     *            1、http://localhost:8083/goods/goodsIndex
     *            2、http://localhost:8083/goods/goodsIndex?channel=1&goods_id=2
     *            以上两种方式都支持
     * @return
     */
    @Override
    public ServiceResult parseGetResult(String url) {
        ServiceResult result = new ServiceResult();
        //1.当前线程调用完这行后,会立即异步进入最下面的return,返回预设的信息,而不是对方接口返回的信息.
        ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.getForEntity(url, String.class);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity>() {
            public void onSuccess(ResponseEntity resp) {
                String sResult = resp.getBody().toString();
                if (!StringUtils.isEmpty(sResult)) {
                    JSONObject jResult = JSONObject.parseObject(sResult);
                    result.setContent(jResult);
                    result.setCode(CODE_SUCCESS);
                    result.setMessage("success");
                    logger.info("@@@url:{} 请求成功,可以继续对本次异步返回的接口数据进行业务处理,此处省略...", url);
                    //业务处理--省略......

                } else {
                    this.onFailure(new Throwable("请求结果为空"));
                }
            }

            public void onFailure(Throwable t) {
                result.setMessage(t.getMessage());
                result.setCode(CODE_ERROR);
                t.printStackTrace();
                logger.info("url:{} 请求失败", url);
            }
        });

        //2.这里会由当前线程,异步直接返回空对象(在请求的响应返回数据之前,这里就先返回了).
        return buildAsyncSuccessResult();
    }

    /**
     * 发送get请求(推荐)
     *
     * @param map 参数map
     * @param url 请求地址，地址中不需要拼接参数，在方法中处理
     *            示例 :
     *            HashMap<String, Object> map = new HashMap<String, Object>;
     *            map.put("channel", 1);
     *            map.put("goodsId", 2);
     *            String url = "http://localhost:8083/goods/goodsIndex";
     *            根据实际情况自己添加参数
     * @return
     */
    @Override
    public ServiceResult parseGetResult(HashMap<String, Object> map, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!CollectionUtils.isEmpty(map)) {
            Set<String> keys = map.keySet();
            sb.append("?");
            for (String string : keys) {
                sb.append(string);
                sb.append("=");
                sb.append(map.get(string).toString());
                sb.append("&");
            }
        }
        String finalUrl = sb.toString();
        if (finalUrl.endsWith("&")) {
            finalUrl = finalUrl.substring(0, finalUrl.length() - 1);
        }
        ServiceResult result = this.parseGetResult(finalUrl);
        return result;
    }

    /**
     * 发送post请求(推荐)
     *
     * @param map  请求参数集合
     * @param url 请求地址 示例：
     *             MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
     *             map.add("channel", 1);
     *             map.add("goods_id", 2);
     *             String url = "http://localhost:8083/goods/goodsIndex";
     *             根据实际情况自己添加参数
     * @return
     */
    @Override
    public ServiceResult parsePostResult(LinkedMultiValueMap<String, Object> map, String url) {
        ServiceResult result = new ServiceResult();
        String header = "";
        HttpEntity httpEntity = new HttpEntity(header);
        ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.postForEntity(url, httpEntity, String.class, map);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity>() {
            public void onSuccess(ResponseEntity resp) {
                String sResult = resp.getBody().toString();
                if (!StringUtils.isEmpty(sResult)) {
                    JSONObject jResult = JSONObject.parseObject(sResult);
                    result.setContent(jResult);
                    result.setCode(CODE_SUCCESS);
                    result.setMessage("success");
                    logger.info("url:{}, map:{} 请求成功", url, map);
                } else {
                    this.onFailure(new Throwable("请求结果为空"));
                }
            }

            public void onFailure(Throwable t) {
                result.setMessage(t.getMessage());
                result.setCode(CODE_ERROR);
                t.printStackTrace();
                logger.info("url:{}, map:{} 请求失败", url, map);
            }
        });
        return result;
    }

    @Override
    public ServiceResult parsePostResultWithHeader(HttpHeaders headers, JSONObject params, String url) {
        ServiceResult result = new ServiceResult();
        HttpEntity httpEntity = new HttpEntity(headers);
        ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.postForEntity(url, httpEntity, String.class, params);
        forEntity.addCallback(new ListenableFutureCallback<ResponseEntity>() {
            public void onSuccess(ResponseEntity resp) {
                String sResult = resp.getBody().toString();
                if (!StringUtils.isEmpty(sResult)) {
                    JSONObject jResult = JSONObject.parseObject(sResult);
                    result.setContent(jResult);
                    result.setCode(CODE_SUCCESS);
                    result.setMessage("success");
                    logger.info("url:{}, map:{} 请求成功", url, params);
                } else {
                    this.onFailure(new Throwable("请求结果为空"));
                }
            }

            public void onFailure(Throwable t) {
                result.setMessage(t.getMessage());
                result.setCode(CODE_ERROR);
                t.printStackTrace();
                logger.info("url:{}, map:{} 请求失败", url, params);
            }
        });
        return result;
    }

}

