package com.rosenzest.server.base.config;

import java.util.Date;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

import com.rosenzest.server.base.interceptor.FeignRequestNoInterceptor;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * feign 配置
 * 
 * @author fronttang
 * @date 2021/07/02
 */
@Configuration
public class FeignConfiguration {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Autowired
    private Date2StringConverter date2StringConverter;

    @Bean
    public Encoder feignEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public FeignRequestNoInterceptor FeignTraceInterceptor() {
        return new FeignRequestNoInterceptor();
    }

    @Configuration
    public class FeignFormatterRegister implements FeignFormatterRegistrar {

        @Override
        public void registerFormatters(FormatterRegistry registry) {
            registry.addConverter(Date.class, String.class, date2StringConverter);
        }
    }

}
