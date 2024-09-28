package com.rosenzest.electric.storage.vo;

import java.math.BigDecimal;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
public class PhotovoltaicAnnualPowerData {

	/**
	 * 年份
	 */
	private Integer year;

	/**
	 * 年份
	 */
	private String yearName;

	public String getYearName() {
		if (StrUtil.isBlank(this.yearName)) {
			this.yearName = StrUtil.format("第{}年", this.year);
		}
		return this.yearName;
	}

	/**
	 * 发电量万KWh
	 */
	private BigDecimal annualPower;

	/**
	 * 电价（度/元）
	 */
	private BigDecimal price;

	/**
	 * 项目收益（万元）
	 */
	private BigDecimal benefits;

	/**
	 * 投资方收益占比
	 */
	private BigDecimal investorBenefitsRate;

	/**
	 * 业主方收益占比
	 */
	private BigDecimal ownerBenefitsRate;

	/**
	 * 投资方收益（万元）
	 */
	private BigDecimal investorBenefits;

	/**
	 * 业主方收益（万元）
	 */
	private BigDecimal ownerBenefits;

}
