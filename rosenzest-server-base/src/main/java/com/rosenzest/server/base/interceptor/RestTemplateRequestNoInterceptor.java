package com.rosenzest.server.base.interceptor;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.util.StrUtil;

/**
 * restTemplate 请求号拦截器
 * 
 * @author fronttang
 * @date 2021/07/29
 */
public class RestTemplateRequestNoInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes,
        ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        IRequestContext current = RequestContextHolder.getCurrent();
        String requestNo = current.getRequestNo();
        if (StrUtil.isNotBlank(requestNo)) {
            httpRequest.getHeaders().add(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo);
        }

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
