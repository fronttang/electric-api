package com.rosenzest.electric.owner.vo;

import java.util.Map;

import lombok.Data;

@Data
public class OwnerUnitDangerStatisticsVo {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 业主单元名称
	 */
	private String name;

	/**
	 * 楼栋ID
	 */
	private Long buildingId;

	/**
	 * 楼栋名称
	 */
	private String buildingName;

	/**
	 * 总隐患数
	 */
	private Long danger;

	/**
	 * 整改数
	 */
	private Long finish;

	/**
	 * 隐患数
	 */
	private Map<String, Long> dangers;

	/**
	 * 整改数
	 */
	private Map<String, Long> finishs;

}
