package com.rosenzest.electric.storage.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 储能月数据
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Data
public class EnergyStorageMonthDto {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 储能项目ID
	 */
	@ApiModelProperty("储能项目ID")
	private Long storageId;

	/**
	 * 月份
	 */
	@ApiModelProperty("月份")
	private String month;

	/**
	 * 天数
	 */
	@ApiModelProperty("天数")
	private Integer days;

	/**
	 * 尖电量（kWh）
	 */
	@ApiModelProperty("尖电量（kWh）")
	private BigDecimal jian;

	/**
	 * 峰电量（kWh）
	 */
	@ApiModelProperty("峰电量（kWh）")
	private BigDecimal feng;

	/**
	 * 平电量（kWh）
	 */
	@ApiModelProperty("平电量（kWh）")
	private BigDecimal ping;

	/**
	 * 谷电量（kWh）
	 */
	@ApiModelProperty("谷电量（kWh）")
	private BigDecimal gu;

}
