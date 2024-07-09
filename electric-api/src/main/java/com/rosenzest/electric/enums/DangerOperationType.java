package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DangerOperationType implements IEnum<DangerOperationType> {

	/**
	 * 初检
	 */
	INITIAL("1", "初检"),

	/**
	 * 整改
	 */
	RECTIFICATION("2", "整改"),

	/**
	 * 复检
	 */
	REVIEW("3", "复检"),

	/**
	 * 无法检测
	 */
	UNABLE_TO_DETECT("4", "无法检测"),

	;

	final String code;

	final String name;
}
