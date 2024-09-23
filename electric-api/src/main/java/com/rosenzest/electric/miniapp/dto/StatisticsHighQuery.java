package com.rosenzest.electric.miniapp.dto;

import lombok.Data;

@Data
public class StatisticsHighQuery {

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 高风险类型
	 */
	private String type;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 村
	 */
	private String hamlet;
}
