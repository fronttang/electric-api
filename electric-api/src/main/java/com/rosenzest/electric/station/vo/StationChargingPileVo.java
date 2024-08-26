package com.rosenzest.electric.station.vo;

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
public class StationChargingPileVo {

	/**
	 * ID
	 */
	@ApiModelProperty("id")
	private Long id;

	/**
	 * 充电站ID
	 */
	@ApiModelProperty("充电站ID")
	private Long unitId;

	/**
	 * 类型
	 */
	@ApiModelProperty("充电桩类型")
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
	private String power;

	/**
	 * 数量
	 */
	@ApiModelProperty("数量")
	private Long quantity;

}
