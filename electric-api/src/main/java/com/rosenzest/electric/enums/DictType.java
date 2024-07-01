package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DictType implements IEnum<DictType> {

	PROJECT_TYPE("project_type", "项目类型"),

	;


    final String code;

    final String name;
}
