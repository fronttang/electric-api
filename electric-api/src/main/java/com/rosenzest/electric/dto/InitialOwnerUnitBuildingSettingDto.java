package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class InitialOwnerUnitBuildingSettingDto {

	@ApiModelProperty(value = "业主单元ID", required = true)
	@NotNull(message = "业主单元ID不能为空")
	private Long id;

	@ApiModelProperty(value = "楼栋ID", required = true)
	@NotNull(message = "楼栋ID不能为空")
	private Long buildingId;

	/**
	 * 是否完成入户率
	 */
	@ApiModelProperty("是否完成入户率 0否 1是")
	private String isHouseholdRate = "0";

	/**
	 * 无法检测
	 */
	@ApiModelProperty("是否无法检测 0否 1是")
	private String isTest;

	/**
	 * 无法检测原因
	 */
	@ApiModelProperty("无法检测原因")
	private String isTestReason;
}
