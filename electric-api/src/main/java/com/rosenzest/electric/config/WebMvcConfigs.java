package com.rosenzest.electric.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rosenzest.base.constant.SpringSecurityConstant;
import com.rosenzest.electric.constant.ElectricConstant;
import com.rosenzest.electric.properties.SystemProperties;
import com.rosenzest.server.base.interceptor.SecurityInterceptor;

@Configuration
public class WebMvcConfigs implements WebMvcConfigurer {

	@Bean
	public SecurityInterceptor securityInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上传路径 */
		registry.addResourceHandler(ElectricConstant.RESOURCE_PREFIX + "/**")
				.addResourceLocations("file:" + SystemProperties.getProfile() + "/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		String[] excludeArray = SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS;

		List<String> exclude = new ArrayList<>();
		exclude.addAll(Arrays.asList(excludeArray));
		exclude.add("/login/**");
		exclude.add("/user/login");
		exclude.add("/register");
		exclude.add("/dict/list");
		exclude.add("/v3/3rd/**");
		exclude.add("/profile/**");
		exclude.add("/client/version");

		registry.addInterceptor(securityInterceptor()).order(Integer.MAX_VALUE).addPathPatterns("/**")
				.excludePathPatterns(exclude);
	}
}
