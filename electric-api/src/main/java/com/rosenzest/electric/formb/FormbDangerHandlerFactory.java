package com.rosenzest.electric.formb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.electric.enums.DetectFormB;
import com.rosenzest.electric.formb.handler.IFormbDangerHandler;

import cn.hutool.core.collection.CollUtil;

@Component
public class FormbDangerHandlerFactory implements ApplicationContextAware {

	private static final ConcurrentHashMap<DetectFormB, IFormbDangerHandler> HANDLER_MAP = new ConcurrentHashMap<DetectFormB, IFormbDangerHandler>();

	public static IFormbDangerHandler getFormbDangerHander(DetectFormB type) {
		return HANDLER_MAP.get(type);
	}

	public static IFormbDangerHandler getFormbDangerHander(String type) {
		DetectFormB formb = EnumUtils.init(DetectFormB.class).fromCode(type);
		if (formb != null) {
			return HANDLER_MAP.get(formb);
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		Map<String, IFormbDangerHandler> handerBeans = applicationContext.getBeansOfType(IFormbDangerHandler.class);

		if (CollUtil.isNotEmpty(handerBeans)) {
			handerBeans.forEach((name, bean) -> {
				FormbDangerHandler annotation = AnnotationUtils.findAnnotation(bean.getClass(),
						FormbDangerHandler.class);
				if (annotation != null && annotation.value() != null) {
					HANDLER_MAP.put(annotation.value(), bean);
				}
			});
		}
	}

}
