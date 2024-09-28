package com.rosenzest.electric.storage.vo;

import java.util.List;

import lombok.Data;

@Data
public class PhotovoltaicReportData {

	/**
	 * 项目信息
	 */
	private PhotovoltaicVo project;

	/**
	 * 发电量
	 */
	private List<PhotovoltaicAnnualPowerData> annualPowerList;

	/**
	 * 发电量
	 */
	private List<PhotovoltaicReportPowerData> powerList;

	/**
	 * 首年发电量
	 */
	private PhotovoltaicAnnualPowerData firstPower;

	/**
	 * 总发电量
	 */
	private PhotovoltaicAnnualPowerData totalPower;

	/**
	 * 平均发电量
	 */
	private PhotovoltaicAnnualPowerData avaragePower;

	/**
	 * 总排放数据
	 */
	private PhotovoltaicAnnualEmissionData totalEmission;

	/**
	 * 平均排放数据
	 */
	private PhotovoltaicAnnualEmissionData avarageEmission;

}
