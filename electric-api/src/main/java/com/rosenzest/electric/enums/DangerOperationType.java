package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DangerOperationType implements IEnum<DangerOperationType> {

	INITIAL("1", "初检"),

	RECTIFICATION("2", "整改"),

	REVIEW_PASS("3", "复检通过"),

	REVIEW_NOTPASS("4", "复检不通过"),

	UNABLE_TO_DETECT("5", "无法检测"),

	;

	final String code;

	final String name;
}
