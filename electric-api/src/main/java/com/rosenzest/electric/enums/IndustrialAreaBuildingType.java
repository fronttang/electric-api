package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工业园楼栋类型
 */
@Getter
@AllArgsConstructor
public enum IndustrialAreaBuildingType implements IEnum<IndustrialAreaBuildingType> {

	/**
	 * 其他
	 */
	OTHER("0", "其他"),

	/**
	 * 配电房
	 */
	POWER_ROOM("1", "配电房"),

	/**
	 * 宿舍楼
	 */
	DORMITORY("2", "宿舍楼"),

	;

	final String code;

	final String name;
}
