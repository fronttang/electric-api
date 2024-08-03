package com.rosenzest.electric.owner.vo;

import java.util.Map;

import lombok.Data;

@Data
public class OwnerUnitBuildingDangerStatisticsVo {

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 楼栋名称
	 */
	private String name;

	/**
	 * 类型配电房/宿舍楼/其他
	 */
	private String type;

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
