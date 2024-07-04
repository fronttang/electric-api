package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 初检状态
 */
@Getter
@AllArgsConstructor
public enum InitialInspectionStatus implements IEnum<InitialInspectionStatus> {

	CHECKING("0", "检测中"), UNABLE_TO_DETECT("1", "无法检测"), FINISH("2", "完成"),

	;

	final String code;

	final String name;
}
