package com.rosenzest.electric.station.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ChargingPileDto {

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
	 * 名称
	 */
	@NotNull(message = "充电桩名称不能为空")
	@ApiModelProperty(value = "名称", required = true)
	private String name;

	/**
	 * 自编号
	 */
	@ApiModelProperty("自编号")
	private String code;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
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
	 * 出厂编号
	 */
	@ApiModelProperty("出厂编号")
	private String serialNumber;

	/**
	 * 生产日期
	 */
	@ApiModelProperty("生产日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date productionDate;

}
