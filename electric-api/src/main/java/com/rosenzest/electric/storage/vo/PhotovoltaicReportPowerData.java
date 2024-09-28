package com.rosenzest.electric.storage.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PhotovoltaicReportPowerData {

	/**
	 * 年份
	 */
	private String year;

	/**
	 * 发电量万KWh
	 */
	private BigDecimal annualPower;

	/**
	 * 年份
	 */
	private String year1;

	/**
	 * 发电量万KWh
	 */
	private BigDecimal annualPower1;
}
