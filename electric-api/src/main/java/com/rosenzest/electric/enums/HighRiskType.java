package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HighRiskType implements IEnum<HighRiskType> {

	/**
	 * 出租屋
	 */
	RENTAL_HOUSE("1", "出租屋"),

	;

	final String code;

	final String name;
}
