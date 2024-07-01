package com.rosenzest.server.base.aspect;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.rosenzest.base.IResult;
import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.server.base.context.RequestContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局 responsebody 处理
 * 
 * @author fronttang
 * @date 2021/08/02
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class GlobalResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType,
        MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {

        try {

            String requestNo = RequestContextHolder.getCurrent().getRequestNo();
            response.getHeaders().add(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo);

            Object value = bodyContainer.getValue();
            if (value instanceof IResult) {
                // 返回结果日志打印
                log.info("响应数据:{}", JSON.toJSONString(value));
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
