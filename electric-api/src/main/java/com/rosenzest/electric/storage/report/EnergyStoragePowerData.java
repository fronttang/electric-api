package com.rosenzest.electric.storage.report;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EnergyStoragePowerData {

	/**
	 * 综合消纳功率水平
	 */
	private BigDecimal t1;

	/**
	 * 变压器综合剩余情况
	 */
	private BigDecimal t2;

	/**
	 * 综合预计装机功率
	 */
	private BigDecimal t3;

	/**
	 * 预计装机容量
	 */
	private BigDecimal t4a;

	/**
	 * 预计装机容量
	 */
	private BigDecimal t4b;
}
