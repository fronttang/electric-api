package com.rosenzest.electric.station.dto;

import javax.validation.constraints.NotNull;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ChargingPileQuery extends PageQuery {

	/**
	 * 充电站ID
	 */
	@ApiModelProperty("充电站ID")
	@NotNull(message = "充电站ID不能为空")
	private Long unitId;

	/**
	 * 充电桩类型
	 */
	@ApiModelProperty("充电桩类型")
	private String type;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String keyword;

	/**
	 * 轮次
	 */
	@ApiModelProperty("轮次")
	private Integer rounds;
}
