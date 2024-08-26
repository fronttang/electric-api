package com.rosenzest.server.base.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements IEnum<UserStatus> {

	OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

	final String code;

	final String name;
}
