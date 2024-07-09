package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 项目类型
 */
@Getter
@AllArgsConstructor
public enum ProjectType implements IEnum<ProjectType> {

	/**
	 * 城中村
	 */
	URBAN_VILLAGE("1", "城中村"),

	/**
	 * 工业园
	 */
	INDUSTRIAL_AREA("2", "工业园"),

	/**
	 * 火灾高风险
	 */
	HIGH_RISK("3", "火灾高风险"),

	/**
	 * 充电场站
	 */
	CHARGING_STATION("4", "充电场站"),

	;

	final String code;

	final String name;
}
