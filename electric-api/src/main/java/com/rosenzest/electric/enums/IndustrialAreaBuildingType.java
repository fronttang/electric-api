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

	OTHER("0", "其他"),

	POWER_ROOM("1", "配电房"),

	DORMITORY("2", "宿舍楼"),

	;

	final String code;

	final String name;
}
