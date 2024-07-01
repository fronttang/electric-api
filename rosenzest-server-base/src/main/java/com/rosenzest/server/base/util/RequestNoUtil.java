package com.rosenzest.server.base.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;

import com.rosenzest.base.constant.SystemConstants;

/**
 * 请求序列号工具
 * 
 * @author fronttang
 * @date 2021/07/29
 */
public final class RequestNoUtil {

    /**
     * 先从MDC里拿requestNo
     * 
     * @return
     */
    public static String getRequestNoFormMDC() {
        String requestNo = MDC.get(SystemConstants.REQUEST_NO_HEADER_NAME);
        return requestNo;
    }

    /**
     * request请求头里拿requestNo
     * 
     * @return
     */
    public static String getRequestNoFormRequest() {

        HttpServletRequest request = HttpServletUtil.getRequest();
        String requestNo = request.getHeader(SystemConstants.REQUEST_NO_HEADER_NAME);
        return requestNo;
    }
}
