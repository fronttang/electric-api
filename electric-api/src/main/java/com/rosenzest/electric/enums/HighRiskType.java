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

	/**
	 * 三小场所
	 */
	SMALL("2", "三小场所"),

	/**
	 * 住宅小区
	 */
	RESIDENTIAL("3", "住宅小区"),

	/**
	 * 工业企业
	 */
	INDUSTRIAL("4", "工业企业"),

	/**
	 * 公共场所
	 */
	PUBLIC_PLACES("5", "公共场所"),

	/**
	 * 大型综合体
	 */
	COMPLEX("6", "大型综合体"),

	;

	final String code;

	final String name;
}
