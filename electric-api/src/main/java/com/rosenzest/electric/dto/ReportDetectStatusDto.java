package com.rosenzest.electric.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ReportDetectStatusDto {

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	private Long buildingId;

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
}
