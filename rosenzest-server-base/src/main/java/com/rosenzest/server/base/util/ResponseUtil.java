package com.rosenzest.server.base.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.rosenzest.base.Result;
import com.rosenzest.base.constant.IResultEnum;
import com.rosenzest.base.exception.BusinessException;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;
import lombok.extern.slf4j.Slf4j;

/**
 * 响应工具类
 *
 */
@Slf4j
public class ResponseUtil {

    /**
     * 直接向前端写response
     */
    public static void response(HttpServletResponse response, String result) throws IOException {
        log.info("响应数据:{}", result);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(ContentType.JSON.toString());
        response.getWriter().write(result);
    }

    /**
     * 直接向前端写response
     *
     */
    public static void response(HttpServletResponse response, Integer code, String message) throws IOException {

        Result<Object> result = Result.ERROR(code, message);
        response(response, result);
    }

    /**
     * 直接向前端写response，
     */
    public static void response(HttpServletResponse response, Result<?> result) throws IOException {
        response(response, JSON.toJSONString(result));
    }

    /**
     * 直接向前端写response，
     */
    public static void response(HttpServletResponse response, IResultEnum result) throws IOException {
        response(response, result.toResult());
    }

    /**
     * 直接向前端写response，
     */
    public static void response(HttpServletResponse response, BusinessException result) throws IOException {
        response(response, result.toResult());
    }

}
