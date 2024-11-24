package com.rosenzest.electric.station.vo;

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
public class ChargingPileVo {

	/**
	 * ID
	 */
	// @JsonSerialize(using = ToStringSerializer.class)
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
	@ApiModelProperty("名称")
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
	 * 轮次
	 */
	@ApiModelProperty("轮次")
	private Integer rounds;

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

	/**
	 * 生产日期
	 */
	@ApiModelProperty("生产日期")
	private String productionDate;

}
