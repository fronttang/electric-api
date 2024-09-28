package com.rosenzest.electric.storage.entity;

import java.math.BigDecimal;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 储能月数据
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EnergyStorageMonth extends BaseEntity<EnergyStorageMonth> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 储能项目ID
	 */
	private Long storageId;

	/**
	 * 月份 yyyy-MM
	 */
	private String month;

	/**
	 * 天数
	 */
	private Integer days;

	/**
	 * 尖电量（kWh）
	 */
	private BigDecimal jian;

	/**
	 * 峰电量（kWh）
	 */
	private BigDecimal feng;

	/**
	 * 平电量（kWh）
	 */
	private BigDecimal ping;

	/**
	 * 谷电量（kWh）
	 */
	private BigDecimal gu;

}
