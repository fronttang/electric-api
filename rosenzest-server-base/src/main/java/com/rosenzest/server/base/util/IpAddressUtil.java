package com.rosenzest.server.base.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONPath;
import com.rosenzest.base.constant.SymbolConstant;
import com.rosenzest.base.constant.SystemConstants;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 根据ip地址定位工具类，使用阿里云定位api
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Slf4j
public final class IpAddressUtil {

    private static final String LOCAL_IP = "127.0.0.1";

    private static final String LOCAL_REMOTE_HOST = "0:0:0:0:0:0:0:1";

    /**
     * 获取客户端ip
     */
    public static String getIp(HttpServletRequest request) {
        if (ObjectUtil.isEmpty(request)) {
            return SymbolConstant.DASH;
        } else {
            String remoteHost = ServletUtil.getClientIP(request);
            return LOCAL_REMOTE_HOST.equals(remoteHost) ? LOCAL_IP : remoteHost;
        }
    }

    /**
     * 获取客户端ip
     */
    public static String getIp() {
        return getIp(HttpServletUtil.getRequest());
    }

    /**
     * 根据ip地址定位
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public static String getAddress(HttpServletRequest request) {
        String resultJson = SymbolConstant.DASH;

        String ip = getIp(request);

        // 如果是本地ip或局域网ip，则直接不查询
        if (ObjectUtil.isEmpty(ip) || NetUtil.isInnerIP(ip)) {
            return resultJson;
        }

        try {
            // 获取阿里云定位api接口
            String api = "api";
            // 获取阿里云定位appCode
            String appCode = "appCode";
            if (ObjectUtil.isAllNotEmpty(api, appCode)) {
                String path = "$['data']['country','region','city','isp']";
                String appCodeSymbol = "APPCODE";
                HttpRequest http = HttpUtil.createGet(String.format(api, ip));
                http.header(SystemConstants.AUTHORIZATION, appCodeSymbol + " " + appCode);
                resultJson = http.timeout(3000).execute().body();
                resultJson = String.join("", (List<String>)JSONPath.read(resultJson, path));
            }
        } catch (Exception e) {
            resultJson = SymbolConstant.DASH;
            log.error(">>> 根据ip定位异常，具体信息为：{}", e.getMessage());
        }
        return resultJson;
    }

    public static String getAddress() {
        return getAddress(HttpServletUtil.getRequest());
    }

}
