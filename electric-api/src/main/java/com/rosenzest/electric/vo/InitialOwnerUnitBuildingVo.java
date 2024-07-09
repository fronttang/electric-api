package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InitialOwnerUnitBuildingVo {

	/**
	 * id
	 */
	@ApiModelProperty("id")
	private Long id;

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 类型配电房/宿舍楼/其他
	 */
	@ApiModelProperty("类型,1配电房/2宿舍楼/0其他, 见字典：industrial_area_building_type")
	private String type;

	/**
	 * 隐患数
	 */
	@ApiModelProperty("隐患数")
	private Integer dangers;

	/**
	 * 户数
	 */
	@ApiModelProperty("户数")
	private Integer doors;

	/**
	 * 入户数
	 */
	@ApiModelProperty("入户数")
	private Integer households;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典初检状态：initial_test_status")
	private String status;

}
