package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class UnitAreaDangerQuery {

	@ApiModelProperty(value = "业主单元ID", required = true)
	@NotNull(message = "业主单元ID不能为空")
	private Long unitId;

	@ApiModelProperty("公共区域/户ID")
	private Long unitAreaId;

	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 检测表ID
	 */
	@ApiModelProperty("检测表ID,B类表不传ID,只传编号和类型")
	private Long formId;

	/**
	 * 检测表编号
	 */
	@ApiModelProperty("检测表编号")
	private String formCode;

	/**
	 * 检测表类型A/B/C
	 */
	@ApiModelProperty("检测表类型:A/B/C")
	private String type;
}
