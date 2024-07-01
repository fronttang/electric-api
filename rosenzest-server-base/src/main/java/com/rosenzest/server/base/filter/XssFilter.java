package com.rosenzest.server.base.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.rosenzest.base.constant.SpringSecurityConstant;

/**
 * xss过滤器
 * 
 * @author fronttang
 * @date 2021/07/30
 */
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String servletPath = httpServletRequest.getServletPath();

        // 获取不进行url过滤的接口
        List<String> unXssFilterUrl = Arrays.asList(SpringSecurityConstant.UN_XSS_FILTER_URL);
        if (unXssFilterUrl != null && unXssFilterUrl.contains(servletPath)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest)request), response);
        }
    }

}