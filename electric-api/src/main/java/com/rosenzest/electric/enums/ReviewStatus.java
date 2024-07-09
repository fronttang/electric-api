package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 复检状态
 */
@Getter
@AllArgsConstructor
public enum ReviewStatus implements IEnum<ReviewStatus> {

	/**
	 * 待整改
	 */
	RECTIFIED("0", "待整改"),

	/**
	 * 待复检
	 */
	RE_EXAMINATION("1", "待复检"),

	/**
	 * 完成
	 */
	FINISH("2", "完成"),

	;

	final String code;

	final String name;
}
