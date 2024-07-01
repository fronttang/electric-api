package com.rosenzest.server.base.interceptor;

import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.server.base.context.IRequestContext;
import com.rosenzest.server.base.context.RequestContextHolder;

import cn.hutool.core.util.StrUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign调用请求号拦截器
 * 
 * @author fronttang
 * @date 2021/07/29
 */
public class FeignRequestNoInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {

        IRequestContext current = RequestContextHolder.getCurrent();
        String requestNo = current.getRequestNo();
        if (StrUtil.isNotBlank(requestNo)) {
            template.header(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo);
        }
    }

}
