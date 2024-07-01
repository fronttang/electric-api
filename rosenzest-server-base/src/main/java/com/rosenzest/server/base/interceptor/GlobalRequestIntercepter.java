package com.rosenzest.server.base.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.enums.RequestParamType;
import com.rosenzest.server.base.util.HttpRequestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局请求拦截器
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Slf4j
public class GlobalRequestIntercepter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        try {

            RequestContextHolder current = (RequestContextHolder)RequestContextHolder.getCurrent();

            Map<String, Object> requestParam = HttpRequestUtil.getRequestParam(request);
            Map<String, Object> pathVariable = HttpRequestUtil.getPathVariable(request);
            // 取参数
            current.setParam(RequestParamType.REQUEST_PARAM, requestParam);
            current.setParam(RequestParamType.PATH_VARIABLE, pathVariable);

        } catch (Exception e) {
            log.error("", e);
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {

        response.addHeader(SystemConstants.REQUEST_NO_HEADER_NAME, RequestContextHolder.getCurrent().getRequestNo());

        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        if (ex != null) {
            log.info("执行异常:{}", ex.getMessage());
        }
        super.afterCompletion(request, response, handler, ex);
    }

}
