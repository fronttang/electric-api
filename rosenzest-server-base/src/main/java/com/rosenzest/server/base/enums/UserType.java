package com.rosenzest.server.base.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType implements IEnum<UserType> {

	/**
	 * 平台用户
	 */
	SYSTEM("00", "平台用户"),

	/**
	 * 检测单位管理员
	 */
	DETECT_UNIT_ADMIN("01", "检测单位管理员"),

	/**
	 * 工作人员账号
	 */
	WORKER("02", "工作人员账号"),

	/**
	 * 街区账号
	 */
	AREA_USER("03", "街区账号"),

	/**
	 * 网格员
	 */
	GRADMAN("04", "网格员"),

	/**
	 * 业主单元账号
	 */
	OWNER_UNIT("05", "业主单元账号"),

	/**
	 * 电力设计账号
	 */
	ELECTRIC("06", "电力设计"),

	/**
	 * 光伏储能账号
	 */
	STORAGE("07", "光伏储能"),

	/**
	 * 游客
	 */
	VISITOR("99", "游客"),;

	final String code;

	final String name;
}
