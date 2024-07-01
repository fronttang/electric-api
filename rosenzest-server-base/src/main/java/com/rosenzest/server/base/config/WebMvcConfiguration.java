package com.rosenzest.server.base.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rosenzest.base.annotation.EnableRedis;
import com.rosenzest.server.base.annotation.EnableJwt;
import com.rosenzest.server.base.annotation.EnableSwagger;
import com.rosenzest.server.base.filter.GlobalRequestFilter;
import com.rosenzest.server.base.filter.XssFilter;
import com.rosenzest.server.base.interceptor.GlobalRequestIntercepter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.extra.spring.EnableSpringUtil;

/**
 * web mvc config
 * 
 * @author fronttang
 * @date 2021/07/12
 */
@Configuration
@EnableJwt
@EnableRedis
@EnableSwagger
@EnableAsync
@EnableSpringUtil
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private String2DateConverter string2DateConverter;

    // @Autowired
    // private Date2StringConverter date2StringConverter;

    @Bean
    public GlobalRequestIntercepter globalRequestIntercepter() {
        return new GlobalRequestIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalRequestIntercepter()).order(Ordered.HIGHEST_PRECEDENCE).addPathPatterns("/**");
    }

    // 接收页面参数使用DateConverterConfig自动转化
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // registry.addConverter(string2DateConverter);
        registry.addConverter(String.class, Date.class, string2DateConverter);
    }

    /**
     * xss过滤器
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterFilterRegistrationBean() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 全局请求过滤器
     * 
     * @return
     */
    @Bean
    public FilterRegistrationBean<GlobalRequestFilter> globalRequestFilterFilterRegistrationBean() {
        FilterRegistrationBean<GlobalRequestFilter> registration =
            new FilterRegistrationBean<>(new GlobalRequestFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return new Jackson2ObjectMapperBuilderCustomizer() {

            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
            }
        };
    }

}
