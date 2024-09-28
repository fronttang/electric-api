package com.rosenzest.electric.storage.report;

import java.util.List;

import com.rosenzest.electric.storage.vo.EnergyStorageVo;

import lombok.Data;

@Data
public class EnergyStorageReportData {

	/**
	 * 项目信息
	 */
	private EnergyStorageVo project;

	/**
	 * 年数据
	 */
	private List<EnergyStorageYearData> yearList;

	/**
	 * 计算信息
	 */
	private EnergyStoragePowerData power;

	/**
	 * 首年数据
	 */
	private EnergyStorageYearData first;

	/**
	 * 平均数据
	 */
	private EnergyStorageYearData average;

	/**
	 * 总数据
	 */
	private EnergyStorageYearData total;

}