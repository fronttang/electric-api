package com.rosenzest.electric.storage.report;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class EnergyStorageMonthPowerData {

	/**
	 * 年月
	 */
	private Date month;

	/**
	 * 用电开始时间
	 */
	private String startDay;

	/**
	 * 用电结束时间
	 */
	private String endDay;

	/**
	 * 用电天数
	 */
	private Integer days;

	/**
	 * 月总有功电量（kWh)
	 */
	private BigDecimal total;

	/**
	 * 月尖期有功电量（kWh)
	 */
	private BigDecimal jian;

	/**
	 * 月峰期有功电量（kWh)
	 */
	private BigDecimal feng;

	/**
	 * 月平期有功电量（kWh)
	 */
	private BigDecimal ping;

	/**
	 * 月谷期有功电量（kWh)
	 */
	private BigDecimal gu;

	/**
	 * 日平均用电（kWh)
	 */
	private BigDecimal dayAverage;

	/**
	 * 日尖、峰期平均用电（kWh)
	 */
	private BigDecimal dayJianFeng;

	/**
	 * 日平期平均用电（kWh)
	 */
	private BigDecimal dayPing;

	/**
	 * 日谷期平均用电（kWh)
	 */
	private BigDecimal dayGu;

	/**
	 * 日尖、峰期平均功率（kW)
	 */
	private BigDecimal dayJianFengPower;

	/**
	 * 日平期平均功率（kW)
	 */
	private BigDecimal dayPingPower;

	/**
	 * 日谷期平均功率（kW)
	 */
	private BigDecimal dayGuPower;

	/**
	 * 变压器谷期剩余负载（kW)
	 */
	private BigDecimal guLoad;

	/**
	 * 变压器平期期剩余负载（kW)
	 */
	private BigDecimal pingLoad;

}
