package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AreaUserType implements IEnum<AreaUserType> {

	/**
	 * 区账号
	 */
	DISTRICT("district", "区账号"),

	/**
	 * 街道账号
	 */
	STREET("street", "区账号"),

	/**
	 * 社区账号
	 */
	COMMUNITY("community", "社区账号"),

	/**
	 * 村账号
	 */
	HAMLET("hamlet", "村账号"),

	;

	final String code;

	final String name;
}
