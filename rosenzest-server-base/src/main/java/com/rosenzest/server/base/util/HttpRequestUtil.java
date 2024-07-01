package com.rosenzest.server.base.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.HandlerMapping;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.rosenzest.base.constant.SymbolConstant;
import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.base.util.IdUtils;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.Browser;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * HTTP request 工具类
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Slf4j
public final class HttpRequestUtil {

    /**
     * 获取请求入参
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getRequestParam(HttpServletRequest request) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            paramMap.put(key, request.getParameter(key));
        }

        return paramMap;
    }

    /**
     * 获取请求入参
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getRequestParam() {
        return getRequestParam(HttpServletUtil.getRequest());
    }

    /**
     * 获取请求 path variable
     * 
     * @param request
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, Object> getPathVariable(HttpServletRequest request) {
        Map map = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (MapUtil.isNotEmpty(map)) {
            return map;
        }
        return new LinkedHashMap<>();
    }

    /**
     * 获取请求头
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> getHeader(HttpServletRequest request) {
        Map<String, Object> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                headers.put(name, request.getHeader(name));
            }
        }
        return headers;
    }

    /**
     * 获取请求头
     * 
     * @return
     */
    public static Map<String, Object> getHeader() {
        return getHeader(HttpServletUtil.getRequest());
    }

    /**
     * 获取请求 path variable
     * 
     * @param request
     * @return
     */
    public static Map<String, Object> getPathVariable() {
        return getPathVariable(HttpServletUtil.getRequest());
    }

    /**
     * 获取请求 RequestNo
     * 
     * @return
     */
    public static String getRequestNo() {
        IRequestContext current = RequestContextHolder.getCurrent();
        String reqSeqNo = current.getRequestNo();
        if (StrUtil.isBlank(reqSeqNo)) {
            reqSeqNo = IdUtils.requestNo();
        }
        return reqSeqNo;
    }

    /**
     * 获取参数对象
     *
     * @param params
     * @return
     */
    public static Map<String, Object> getRequestBody(byte[] params) {
        if (params == null) {
            return null;
        }
        try {
            return JSON.parseObject(params, Map.class);
        } catch (Exception e) {
            try {
                return convertRequestBodyToMap(IOUtils.toString(params, CharsetUtil.UTF_8));
            } catch (IOException e1) {
                log.error("", e);
            }
        }
        return null;
    }

    /**
     * 将参数转换为Map类型
     *
     * @param param
     * @return
     */
    public static Map<String, Object> convertRequestBodyToMap(String param) {
        if (StrUtil.isEmpty(param)) {
            return Collections.emptyMap();
        }
        Map<String, Object> pMap = Maps.newLinkedHashMap();
        List<String> pArray = StrUtil.split(param, SymbolConstant.AND);
        for (String key : pArray) {
            List<String> array = StrUtil.split(key, SymbolConstant.EQUAL);
            if (array.size() == 2) {
                pMap.put(array.get(0), array.get(1));
            }
        }
        return pMap;
    }

    /**
     * 获取客户端浏览器
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtil.isEmpty(userAgent)) {
            return SymbolConstant.DASH;
        } else {
            String browser = userAgent.getBrowser().toString();
            return SystemConstants.UNKNOWN.equals(browser) ? SymbolConstant.DASH : browser;
        }
    }

    /**
     * 获取客户端浏览器
     * 
     * @return
     */
    public static String getBrowser() {
        return getBrowser(HttpServletUtil.getRequest());
    }

    /**
     * 获取客户端操作系统
     */
    public static String getOs(HttpServletRequest request) {
        UserAgent userAgent = getUserAgent(request);
        if (ObjectUtil.isEmpty(userAgent)) {
            return SymbolConstant.DASH;
        } else {
            String os = userAgent.getOs().toString();
            return SystemConstants.UNKNOWN.equals(os) ? SymbolConstant.DASH : os;
        }
    }

    /**
     * 获取客户端操作系统
     */
    public static String getOs() {
        return getOs(HttpServletUtil.getRequest());
    }

    /**
     * 获取请求代理头
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        String userAgentStr = ServletUtil.getHeaderIgnoreCase(request, SystemConstants.USER_AGENT);
        UserAgent userAgent = UserAgentUtil.parse(userAgentStr);
        // 判空
        if (ObjectUtil.isNotEmpty(userAgentStr)) {
            // 如果根本没获取到浏览器
            if (SystemConstants.UNKNOWN.equals(userAgent.getBrowser().getName())) {
                // 则将ua设置为浏览器
                userAgent.setBrowser(new Browser(userAgentStr, null, ""));
            }
        }
        return userAgent;
    }

    /**
     * 获取请求代理头
     */
    public static UserAgent getUserAgent() {
        return getUserAgent(HttpServletUtil.getRequest());
    }
}
