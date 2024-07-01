package com.rosenzest.server.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.base.exception.BusinessException;

/**
 * HttpServlet工具类，获取当前request和response
 */
public class HttpServletUtil {

    /**
     * 获取当前请求的request对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR);
        } else {
            return requestAttributes.getRequest();
        }
    }

    /**
     * 获取当前请求的response对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new BusinessException(ResultEnum.BUSINESS_ERROR);
        } else {
            return requestAttributes.getResponse();
        }
    }
}
