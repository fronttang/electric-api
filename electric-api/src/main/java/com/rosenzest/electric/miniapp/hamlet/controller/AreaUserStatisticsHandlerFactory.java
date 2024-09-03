package com.rosenzest.electric.miniapp.hamlet.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.rosenzest.electric.enums.AreaUserType;

import cn.hutool.core.collection.CollUtil;

@Component
public class AreaUserStatisticsHandlerFactory implements ApplicationContextAware {

	private static final ConcurrentHashMap<AreaUserType, IAreaUserStatisticsHandler> HANDLER_MAP = new ConcurrentHashMap<AreaUserType, IAreaUserStatisticsHandler>();

	public static IAreaUserStatisticsHandler getHander(AreaUserType userType) {
		return HANDLER_MAP.get(userType);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, IAreaUserStatisticsHandler> handerBeans = applicationContext
				.getBeansOfType(IAreaUserStatisticsHandler.class);

		if (CollUtil.isNotEmpty(handerBeans)) {
			handerBeans.forEach((name, bean) -> {
				AreaUserStatisticsHandler annotation = AnnotationUtils.findAnnotation(bean.getClass(),
						AreaUserStatisticsHandler.class);
				if (annotation != null && annotation.value() != null) {
					HANDLER_MAP.put(annotation.value(), bean);
				}
			});
		}
	}

}
