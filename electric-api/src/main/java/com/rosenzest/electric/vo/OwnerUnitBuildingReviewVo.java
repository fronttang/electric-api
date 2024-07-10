package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OwnerUnitBuildingReviewVo {

	/**
	 * id
	 */
	@ApiModelProperty("id")
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 类型配电房/宿舍楼/其他
	 */
	@ApiModelProperty("类型,1配电房/2宿舍楼/0其他, 见字典：industrial_area_building_type")
	private String type;

	/**
	 * 总隐患数
	 */
	@ApiModelProperty("总隐患数")
	private Integer dangers;

	/**
	 * 待整改数
	 */
	@ApiModelProperty("待整改数")
	private Integer rectifications;

	/**
	 * 待复检数
	 */
	@ApiModelProperty("待复检数")
	private Integer reexaminations;

	/**
	 * 完成数
	 */
	@ApiModelProperty("完成数")
	private Integer finishs;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典复检状态：again_test_status")
	private String status;

}
