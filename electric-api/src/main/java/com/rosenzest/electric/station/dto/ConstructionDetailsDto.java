package com.rosenzest.electric.station.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConstructionDetailsDto {

	/**
	 * 充电站ID
	 */
	@NotNull(message = "充电站ID不能为空")
	@ApiModelProperty(value = "充电站ID", required = true)
	private Long unitId;

	/**
	 * 建设明细
	 */
	@ApiModelProperty("建设明细")
	private String details;
}
