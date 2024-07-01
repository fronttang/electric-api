package com.rosenzest.server.base.aspect;

import java.lang.reflect.Type;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.enums.RequestParamType;

/**
 * 全局requestbody处理
 * 
 * @author fronttang
 * @date 2021/08/02
 */
@RestControllerAdvice
public class GlobalRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
        Class<? extends HttpMessageConverter<?>> converterType) {

        RequestContextHolder current = (RequestContextHolder)RequestContextHolder.getCurrent();
        current.setParam(RequestParamType.REQUEST_BODY, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

}
