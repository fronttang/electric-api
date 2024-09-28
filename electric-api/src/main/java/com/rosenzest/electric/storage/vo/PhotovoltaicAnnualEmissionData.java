package com.rosenzest.electric.storage.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PhotovoltaicAnnualEmissionData {

	/**
	 * 发电量万KWh
	 */
	private BigDecimal annualPower = BigDecimal.ZERO;

	/**
	 * 标准煤消耗量（吨）
	 */
	private BigDecimal coal = BigDecimal.ZERO;

	/**
	 * 二氧化碳排放量（吨）
	 */
	private BigDecimal co2 = BigDecimal.ZERO;

	/**
	 * 硫氧化物排放量（吨）
	 */
	private BigDecimal sulfur = BigDecimal.ZERO;

	/**
	 * 氮氧化物排放量（吨）
	 */
	private BigDecimal nitrogen = BigDecimal.ZERO;

}
