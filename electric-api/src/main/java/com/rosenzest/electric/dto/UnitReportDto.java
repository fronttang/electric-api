package com.rosenzest.electric.dto;

import java.util.Date;

import com.rosenzest.electric.enums.UnitReportType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitReportDto {

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	private Long buildingId;

	/**
	 * 是否开启隐患通知单1开启
	 */
	private String isDangerNotice = "0";

	/**
	 * 是否完成入户率1是0否
	 */
	private String isHouseholdRate = "0";

	/**
	 * 是否无法检测1是0否
	 */
	private String isTest = "0";

	/**
	 * 无法检测原因
	 */
	private String isTestReason;

	/**
	 * 报告类型1初检,2复检
	 */
	private UnitReportType type;

	/**
	 * 报告编号
	 */
	private String code;

	/**
	 * 检测时间
	 */
	private Date detectData;

	/**
	 * 检测状态
	 */
	private String detectStatus = "0";

	/**
	 * 检测员
	 */
	private String inspector;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;
}
