/**
 * 
 */
package com.rosenzest.server.base.util;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.rosenzest.base.exception.BusinessException;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Http请求 辅助类 使用 {@link RestTemplate}
 * 
 * @author fronttang
 * @date 2021/08/24
 */
@Slf4j
public final class RestTemplateUtils {

    @Setter
    private static RestTemplate httpClientTemplate;

    private static final RestTemplateUtils httpClient = new RestTemplateUtils();

    private RestTemplateUtils() {

    }

    /**
     * 初始化
     * 
     * @param httpClientTemplate
     * @return
     */
    public static RestTemplateUtils init(RestTemplate httpClientTemplate) {
        RestTemplateUtils.setHttpClientTemplate(httpClientTemplate);
        return httpClient;
    }

    /**
     * 发送HTTP请求
     * 
     * @param method
     * @param url
     * @param headerMap
     * @param param
     * @param clazz
     * @return
     * @throws BusinessException
     */
    public static <T> T exchange(URI uri, HttpMethod method, Map<String, String> headerMap, Object param,
        Class<T> clazz) throws BusinessException {

        // 执行请求
        ResponseEntity<T> response = send(uri, method, headerMap, param, clazz);

        if (response.getStatusCode() != HttpStatus.OK && response.getStatusCode() != HttpStatus.UNAUTHORIZED) {
            throw new BusinessException(StrUtil.format("--->>HTTP访问异常:{}", response.getStatusCode().toString()));
        }

        // 获取请求响应对象
        T body = response.getBody();
        log.info("--->>响应内容:{}", JSON.toJSONString(body));

        return body;
    }

    /**
     * 发送HTTP请求
     * 
     * @param <T>
     * @param method
     * @param url
     * @param headerMap
     * @param param
     * @param clazz
     * @return
     * @throws BusinessException
     */
    public static <T> T exchange(String url, HttpMethod method, Map<String, String> headerMap, Object param,
        Class<T> clazz) throws BusinessException {
        return exchange(URI.create(url), method, headerMap, param, clazz);
    }

    /**
     * 发送HTTP请求
     * 
     * @param <T>
     * @param uri
     * @param method
     * @param headerMap
     * @param param
     * @param clazz
     * @return
     */
    public static <T> ResponseEntity<T> send(URI uri, HttpMethod method, Map<String, String> headerMap, Object param,
        Class<T> clazz) {

        HttpEntity<Object> httpEntity = buildHttpEntity(uri, method, headerMap, param);

        // 执行请求
        ResponseEntity<T> response = httpClientTemplate.exchange(uri, method, httpEntity, clazz);
        log.info("--->>响应状态:{}", response.getStatusCode().toString());

        return response;
    }

    private static HttpEntity<Object> buildHttpEntity(URI uri, HttpMethod method, Map<String, String> headerMap,
        Object param) {
        log.info("--->>开始向地址 [{}] 发起 [{}] 请求", uri.toString(), method.name());

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        if (MapUtil.isNotEmpty(headerMap)) {
            headerMap.forEach((key, value) -> {
                headers.add(key, value);
            });
        }

        log.info("--->>请求头为:{}", JSON.toJSONString(headers));

        // 添加参数 参数是json字符串
        if (param != null && param instanceof String) {
            String paramStr = String.valueOf(param);
            log.info("--->>请求参数:{}", paramStr);
        }
        // 参数时字节流数组
        else if (param != null && param instanceof byte[]) {
            log.info("--->>请求参数为文件流");
            // param = new ByteArrayResource((byte[]) param);
        }
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(param, headers);
        return httpEntity;
    }

    /**
     * 执行http调用 自定义回调
     * 
     * @param <T>
     * @param uri
     * @param method
     * @param headerMap
     * @param param
     * @param function
     * @return
     */
    public static <T> T execute(URI uri, HttpMethod method, Map<String, String> headerMap, Object param,
        Function<ClientHttpResponse, T> function) {

        HttpEntity<Object> httpEntity = buildHttpEntity(uri, method, headerMap, param);

        RequestCallback requestCallback = httpClientTemplate.httpEntityCallback(httpEntity);

        T response = httpClientTemplate.execute(uri, method, requestCallback, (clientHttpResponse) -> {
            return function.apply(clientHttpResponse);
        });

        return response;
    }
}
