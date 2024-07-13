package com.rosenzest.electric.high.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公共场所
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PublicPlacesConfig extends BaseHighConfigEntity<PublicPlacesConfig> {

	private static final long serialVersionUID = 1L;

	/**
	 * 是否属于消防安全重点单位1是0否
	 */
	private String fireSafetyUnit;

	/**
	 * 是否建立逐级消防安全责任制，明确单位消防安全管理人员
	 */
	private String accountability;

	/**
	 * 是否制定符合本单位实际的消防安全制度1是0否
	 */
	private String fireSafetySystem;

	/**
	 * ABC干粉灭火器数量
	 */
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	private String abcDpfeStatus;

	/**
	 * 消防给水及消火栓系统有无1有0无
	 */
	private String hydrantSystemHas;

	/**
	 * 消防给水及消火栓系统是否完好1是0否
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
	 * 自动灭火系统有无1有0无
	 */
	private String automaticFireSystemHas;

	/**
	 * 自动灭火系统是否完好1是0否
	 */
	private String automaticFireSystemStatus;

	/**
	 * 防/排烟系统有无1有0无
	 */
	private String exhaustSystemHas;

	/**
	 * 防/排烟系统是否完好1是0否
	 */
	private String exhaustSystemStatus;

	/**
	 * 总平面布局、灭火救援设施是否符合要求1是0否
	 */
	private String rescueFacilities;

	/**
	 * 耐火等级、防火分区、平面布置是否符合要求1是0否
	 */
	private String fireResistantLevel;

	/**
	 * 防火构造及室内装修是否符合规范要求1是0否
	 */
	private String fireproofStructure;

	/**
	 * 安全疏散设施及管理是否符合要求1是0否
	 */
	private String safeEvacuation;

	/**
	 * 是否设置消防控制室1物业所有0否
	 */
	private String fireControlRoom;

	/**
	 * 电气线路敷设、电气设备使用是否符合要求1是0否
	 */
	private String electricalEquipment;

	/**
	 * 是否开展经常性消防安全宣传工作1是0否
	 */
	private String safetyPromotion;

	/**
	 * 是否组织员工每半年至少开展一次消防培训1是0否
	 */
	private String fireTraining;

	/**
	 * 新上岗的员工是否开展岗前消防培训1是0否
	 */
	private String prejobFireTraining;

	/**
	 * 是否在人群密集场所区域内张贴通知1是0否
	 */
	private String postNotice;

	/**
	 * 用火、动火是否符合要求1是0否
	 */
	private String meetsEquirements;

	/**
	 * 是否每日开展防火巡查1是0否
	 */
	private String fireInspection;

	/**
	 * 是否制定灭火和应急疏散预案，并定期开展演练1是0否
	 */
	private String emergencyEvacuationPlan;

	/**
	 * 门头照
	 */
	private String doorPic;

}
