package com.rosenzest.electric.station.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConstructionDetailsVo {

	/**
	 * 充电站ID
	 */
	@ApiModelProperty("充电站ID")
	private Long unitId;

	/**
	 * 建设明细
	 */
	@ApiModelProperty("建设明细")
	private String details;
}
