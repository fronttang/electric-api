package com.rosenzest.electric.high.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 三小场所
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmallConfig extends BaseHighConfigEntity<SmallConfig> {

	private static final long serialVersionUID = 1L;

	/**
	 * 签订安全承诺书1是0否
	 */
	private String safetyCommitment;

	/**
	 * 张贴违规住人海报1是0否
	 */
	private String illegalResidence;

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
