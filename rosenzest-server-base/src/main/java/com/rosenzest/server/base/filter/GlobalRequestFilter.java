package com.rosenzest.server.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import com.rosenzest.base.constant.SystemConstants;
import com.rosenzest.base.util.IdUtils;
import com.rosenzest.server.base.context.RequestContextHolder;
import com.rosenzest.server.base.util.IpAddressUtil;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fronttang
 * @date 2021/08/03
 */
@Slf4j
public class GlobalRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {

        RequestContextHolder current = (RequestContextHolder)RequestContextHolder.getCurrent();
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String requestNo = null;
        try {

            // 如果有上层调用就用上层的ID
            requestNo = request.getHeader(SystemConstants.REQUEST_NO_HEADER_NAME);
            if (StrUtil.isBlank(requestNo)) {
                requestNo = IdUtils.requestNo();
            }

            MDC.put(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo);

            log.info("请求地址:{} {}", request.getMethod(), request.getRequestURI());

            current.setRequest(request);
            current.setRequestNo(requestNo);

            String ipAddress = IpAddressUtil.getIp(request);
            current.setUserIp(ipAddress);

            current.setStart(System.currentTimeMillis());

            // log.info("请求信息:IP:{} OS:{} Browser:{}", ipAddress, HttpRequestUtil.getOs(request),
            // HttpRequestUtil.getBrowser(request));

            // 包装请求
            ServletRequest requestWrapper = req;
            if (req instanceof HttpServletRequest) {
                requestWrapper = new BodyRepeatableReadRequestWrapper(request);
            }
            chain.doFilter(requestWrapper, response);

        } finally {

            response.addHeader(SystemConstants.REQUEST_NO_HEADER_NAME, requestNo);

            long end = System.currentTimeMillis();
            current.setEnd(end);
            long start = current.getStart();

            log.info("响应时间:{} ms!", (end - start));

            // 清除临时存储的唯一编号
            RequestContextHolder.getCurrent().destroy();
            MDC.remove(SystemConstants.REQUEST_NO_HEADER_NAME);
        }
    }

}
