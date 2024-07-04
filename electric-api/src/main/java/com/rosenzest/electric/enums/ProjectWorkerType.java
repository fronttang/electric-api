package com.rosenzest.electric.enums;

import com.rosenzest.base.enums.IEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectWorkerType implements IEnum<ProjectWorkerType> {

	INSPECTOR("1", "检测员"), PROJECT_MANAGER("2", "项目经理"), APPROVER("2", "审批员"),

	;

	final String code;

	final String name;
}
