package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 报告类型
 */
@Getter
@AllArgsConstructor
public enum UnitReportType implements IEnum<UnitReportType> {

	/**
	 * 初检
	 */
	INITIAL("1", "初检"),

	/**
	 * 复检
	 */
	REVIEW("2", "复检"),

	;

	final String code;

	final String name;
}
