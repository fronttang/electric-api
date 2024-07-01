package com.rosenzest.api;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.map.MapUtil;

/**
 * 抽象HTTP调用API
 * 
 * @author fronttang
 * @date 2021/08/27
 */
public abstract class AbstractHttpClientApi<P extends IApiRequest, R extends IApiResponse>
    extends AbstractEditableUriApi<P, R> {

    /**
     * 构建请求参数
     * 
     * @param param
     * @return
     */
    protected Object getRequestBody(P param) {
        if (Objects.nonNull(param)) {
            return JSON.toJSONString(param);
        }
        return null;
    }

    /**
     * 构建请求头
     */
    protected Map<String, String> getRequestHeader() {
        Map<String, String> header = MapUtil.newHashMap();
        header.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return header;
    }
}
