package com.rosenzest.electric.storage.report;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EnergyStorageYearData {

	/**
	 * 年
	 */
	private Integer year;

	/**
	 * 年
	 */
	private String yearName;

	/**
	 * 充电量(万度）
	 */
	private BigDecimal charge;

	/**
	 * 放电量(万度）
	 */
	private BigDecimal discharge;

	/**
	 * 差价收益
	 */
	private BigDecimal income;

	/**
	 * 其他收益
	 */
	private BigDecimal otherIncome;

	/**
	 * 业主收益
	 */
	private BigDecimal ownerIncome;

	/**
	 * 资方收益
	 */
	private BigDecimal investorIncome;
}
