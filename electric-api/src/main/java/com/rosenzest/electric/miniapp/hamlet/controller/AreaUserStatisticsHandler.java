package com.rosenzest.electric.miniapp.hamlet.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.rosenzest.electric.enums.AreaUserType;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Component
public @interface AreaUserStatisticsHandler {

	/**
	 * 街区用户 用户类型
	 * 
	 * @return
	 */
	AreaUserType value();
}
