package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectWorkerAreaRoleType implements IEnum<ProjectWorkerAreaRoleType> {

	/**
	 * 查询
	 */
	VIEW("1", "查询"),

	/**
	 * 编辑
	 */
	EDIT("2", "编辑")

	;

	final String code;

	final String name;
}
