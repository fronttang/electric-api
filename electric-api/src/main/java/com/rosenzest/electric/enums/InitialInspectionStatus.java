package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 初检状态
 */
@Getter
@AllArgsConstructor
public enum InitialInspectionStatus implements IEnum<InitialInspectionStatus> {

	/**
	 * 检测中
	 */
	CHECKING("0", "检测中"),

	/**
	 * 无法检测
	 */
	UNABLE_TO_DETECT("1", "无法检测"),

	/**
	 * 完成
	 */
	FINISH("2", "完成"),

	;

	final String code;

	final String name;
}
