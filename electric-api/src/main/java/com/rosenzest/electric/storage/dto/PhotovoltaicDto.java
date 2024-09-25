package com.rosenzest.electric.storage.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 光伏
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Data
public class PhotovoltaicDto {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 项目名称
	 */
	@ApiModelProperty("项目名称")
	private String name;

	/**
	 * 业主方
	 */
	@ApiModelProperty("业主方")
	private String owner;

	/**
	 * 项目地址
	 */
	@ApiModelProperty("项目地址")
	private String address;

	/**
	 * 屋顶面积
	 */
	@ApiModelProperty("屋顶面积")
	private String acreage;

	/**
	 * 电价折扣比例（%）
	 */
	@ApiModelProperty("电价折扣比例（%）")
	private BigDecimal discountRate;

	/**
	 * 省市
	 */
	@ApiModelProperty("省市")
	private String area;

	/**
	 * 安装方式
	 */
	@ApiModelProperty("安装方式")
	private String installation;

	/**
	 * 安装容量（MW）
	 */
	@ApiModelProperty("安装容量（MW）")
	private BigDecimal capacityMw;

	/**
	 * 安装容量（KW）
	 */
	@ApiModelProperty("安装容量（KW）")
	private BigDecimal capacityKw;

	/**
	 * 光伏组件（综合单价元/W）
	 */
	@ApiModelProperty("光伏组件（综合单价元/W）")
	private BigDecimal unitPrice;

	/**
	 * 光伏发电时间段（峰、平时段加权平均电价（度/元）
	 */
	@ApiModelProperty("光伏发电时间段（峰、平时段加权平均电价（度/元）")
	private BigDecimal averagePrice;

	/**
	 * 年有效利用小时数（h）
	 */
	@ApiModelProperty("年有效利用小时数（h）")
	private BigDecimal effectiveHours;

	/**
	 * 首年衰减率（%）
	 */
	@ApiModelProperty("首年衰减率（%）")
	private BigDecimal firstDecayRate;

	/**
	 * 逐步衰减率（%）
	 */
	@ApiModelProperty("逐步衰减率（%）")
	private BigDecimal stepDecayRate;

	/**
	 * 光伏组件功率（W）
	 */
	@ApiModelProperty("光伏组件功率（W）")
	private BigDecimal power;

	/**
	 * 项目总投资(元）
	 */
	@ApiModelProperty("项目总投资(元）")
	private BigDecimal totalInvestment;

}
