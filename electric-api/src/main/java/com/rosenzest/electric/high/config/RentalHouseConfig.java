package com.rosenzest.electric.high.config;

import lombok.Data;

/**
 * <p>
 * 出租屋消防配置
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Data
public class RentalHouseConfig {

	/**
	 * 是否张贴电动自行车禁止入内标识
	 */
	private String isBicycles;

	/**
	 * 是否张贴消防通道严禁堵塞标识
	 */
	private String isFirePassages;

	/**
	 * ABC干粉灭火器数量
	 */
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	private String abcDpfeStatus;

	/**
	 * 应急照明灯有无1有0无
	 */
	private String emergencyLightingHas;

	/**
	 * 应急照明灯是否完好1是0否
	 */
	private String emergencyLightingStatus;

	/**
	 * 疏散指示标志有无1有0无
	 */
	private String evacuationSignsHas;

	/**
	 * 疏散指示标志是否完好1是0否
	 */
	private String evacuationSignsStatus;

	/**
	 * 独立式感烟探测器有无1有0无
	 */
	private String smokeDetectorHas;

	/**
	 * 独立式感烟探测器是否完好1是0否
	 */
	private String smokeDetectorStatus;

	/**
	 * 消防软管卷盘有无1有0无
	 */
	private String fireHoseReelsHas;

	/**
	 * 消防软管卷盘是否完好1是0否
	 */
	private String fireHoseReelsStatus;

	/**
	 * 室内消火栓系统有无1有0无
	 */
	private String hydrantSystemHas;

	/**
	 * 室内消火栓系统是否完好1是0否
	 */
	private String hydrantSystemStatus;

	/**
	 * 火灾自动报警系统有无1有0无
	 */
	private String alarmSystemHas;

	/**
	 * 火灾自动报警系统是否完好1是0否
	 */
	private String alarmSystemStatus;

	/**
	 * 防火分隔设施有无1有0无
	 */
	private String fireSeparationHas;

	/**
	 * 防火分隔设施是否完好1是0否
	 */
	private String fireSeparationStatus;

	/**
	 * 营业执照
	 */
	private String businessLicense;

	/**
	 * 门头照
	 */
	private String doorPic;

}
