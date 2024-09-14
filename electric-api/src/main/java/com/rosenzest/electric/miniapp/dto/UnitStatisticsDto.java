package com.rosenzest.electric.miniapp.dto;

import lombok.Data;

@Data
public class UnitStatisticsDto {

	/**
	 * 业主单元ID
	 */
	private Long id;

	/**
	 * 业主单元名称
	 */
	private String name;

	/**
	 * 报告ID
	 */
	private Long reportId;

	/**
	 * 报告状态
	 */
	private String status;
}
