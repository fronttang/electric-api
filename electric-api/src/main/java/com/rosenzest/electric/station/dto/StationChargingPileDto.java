package com.rosenzest.electric.station.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author fronttang
 * @since 2024-07-18
 */
@Data
public class StationChargingPileDto {

	/**
	 * ID
	 */
	@ApiModelProperty("id")
	private Long id;

	/**
	 * 充电站ID
	 */
	@ApiModelProperty(value = "充电站ID", required = true)
	@NotNull(message = "充电站ID不能为空")
	private Long unitId;

	/**
	 * 类型
	 */
	@NotBlank(message = "充电桩类型不能为空")
	@ApiModelProperty(value = "类型", required = true)
	private String type;

	/**
	 * 品牌
	 */
	@ApiModelProperty("品牌")
	private String brand;

	/**
	 * 型号
	 */
	@ApiModelProperty("型号")
	private String model;

	/**
	 * 功率
	 */
	@ApiModelProperty("功率")
	private Long power;

	/**
	 * 数量
	 */
	@ApiModelProperty("数量")
	private Long quantity;

}
