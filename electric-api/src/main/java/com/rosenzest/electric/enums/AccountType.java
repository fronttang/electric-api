package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType implements IEnum<AccountType> {

	/**
	 * 区账号
	 */
	OPERATOR("1", "操作员"),

	/**
	 * 街道账号
	 */
	COMPANY("2", "单位"),

	;

	final String code;

	final String name;
}
