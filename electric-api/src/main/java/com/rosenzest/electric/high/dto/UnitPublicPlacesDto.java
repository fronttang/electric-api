package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UnitPublicPlacesDto extends BaseHighDto {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 检测地址
	 */
	@ApiModelProperty("检测地址")
	private String address;

	/**
	 * 业主单元类型,同项目类型
	 */
	// private String type;

	/**
	 * 检测单位ID
	 */
	// private Long detectId;

	/**
	 * 检测单位名称
	 */
	// private String detectName;

	/**
	 * 项目名称
	 */
	// private String projectName;

	/**
	 * 场所类型
	 */
	@ApiModelProperty("场所类型")
	private String venueType;

	/**
	 * 经营范围
	 */
	@ApiModelProperty("经营范围")
	private String businessScope;

	/**
	 * 员工人数
	 */
	@ApiModelProperty("员工人数")
	private Integer staffs;

	/**
	 * 建筑面积
	 */
	@ApiModelProperty("使用面积")
	private String acreage;

	/**
	 * 有无证照1有0无
	 */
	@ApiModelProperty("有无证照1有0无")
	private String licence;

	/**
	 * 消防安全负责人
	 */
	@ApiModelProperty("消防安全负责人")
	private String safetyIncharge;

	/**
	 * 消防安全负责人电话
	 */
	@ApiModelProperty("消防安全负责人电话")
	private String safetyInchargePhone;

	/**
	 * 消防安全管理人
	 */
	@ApiModelProperty("消防安全管理人")
	private String safetyManager;

	/**
	 * 消防安全管理人电话
	 */
	@ApiModelProperty("消防安全管理人电话")
	private String safetyManagerPhone;

	/**
	 * 单位类型,见字典:industrial_unit_type
	 */
	@ApiModelProperty("单位类型,见字典:public_places_unit_type")
	private String unitType;

	/**
	 * 区县
	 */
	@ApiModelProperty(value = "区", required = true)
	@NotBlank(message = "区不能为空")
	private String district;

	/**
	 * 街道
	 */
	@ApiModelProperty(value = "街道", required = true)
	@NotBlank(message = "街道不能为空")
	private String street;

	/**
	 * 社区
	 */
	@ApiModelProperty(value = "社区", required = true)
	@NotBlank(message = "社区不能为空")
	private String community;

	/**
	 * 是否属于消防安全重点单位1是0否
	 */
	@ApiModelProperty("是否属于消防安全重点单位1是0否")
	private String fireSafetyUnit;

	/**
	 * 是否建立逐级消防安全责任制，明确单位消防安全管理人员
	 */
	@ApiModelProperty("是否建立逐级消防安全责任制，明确单位消防安全管理人员1是0否")
	private String accountability;

	/**
	 * 是否制定符合本单位实际的消防安全制度1是0否
	 */
	@ApiModelProperty("是否制定符合本单位实际的消防安全制度1是0否")
	private String fireSafetySystem;

	/**
	 * ABC干粉灭火器数量
	 */
	@ApiModelProperty("ABC干粉灭火器数量")
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	@ApiModelProperty("ABC干粉灭火器是否完好1是0否")
	private String abcDpfeStatus;

	/**
	 * 消防给水及消火栓系统有无1有0无
	 */
	@ApiModelProperty("消防给水及消火栓系统有无1有0无")
	private String hydrantSystemHas;

	/**
	 * 消防给水及消火栓系统是否完好1是0否
	 */
	@ApiModelProperty("消防给水及消火栓系统是否完好1是0否")
	private String hydrantSystemStatus;

	/**
	 * 火灾自动报警系统有无1有0无
	 */
	@ApiModelProperty("火灾自动报警系统有无1有0无")
	private String alarmSystemHas;

	/**
	 * 火灾自动报警系统是否完好1是0否
	 */
	@ApiModelProperty("火灾自动报警系统是否完好1是0否")
	private String alarmSystemStatus;

	/**
	 * 自动灭火系统有无1有0无
	 */
	@ApiModelProperty("自动灭火系统有无1有0无")
	private String automaticFireSystemHas;

	/**
	 * 自动灭火系统是否完好1是0否
	 */
	@ApiModelProperty("自动灭火系统是否完好1是0否")
	private String automaticFireSystemStatus;

	/**
	 * 防/排烟系统有无1有0无
	 */
	@ApiModelProperty("防/排烟系统有无1有0无")
	private String exhaustSystemHas;

	/**
	 * 防/排烟系统是否完好1是0否
	 */
	@ApiModelProperty("防/排烟系统是否完好1是0否")
	private String exhaustSystemStatus;

	/**
	 * 总平面布局、灭火救援设施是否符合要求1是0否
	 */
	@ApiModelProperty("总平面布局、灭火救援设施是否符合要求1是0否")
	private String rescueFacilities;

	/**
	 * 耐火等级、防火分区、平面布置是否符合要求1是0否
	 */
	@ApiModelProperty("耐火等级、防火分区、平面布置是否符合要求1是0否")
	private String fireResistantLevel;

	/**
	 * 防火构造及室内装修是否符合规范要求1是0否
	 */
	@ApiModelProperty("防火构造及室内装修是否符合规范要求1是0否")
	private String fireproofStructure;

	/**
	 * 安全疏散设施及管理是否符合要求1是0否
	 */
	@ApiModelProperty("安全疏散设施及管理是否符合要求1是0否")
	private String safeEvacuation;

	/**
	 * 是否设置消防控制室1物业所有0否
	 */
	@ApiModelProperty("是否设置消防控制室1物业所有0否")
	private String fireControlRoom;

	/**
	 * 电气线路敷设、电气设备使用是否符合要求1是0否
	 */
	@ApiModelProperty("电气线路敷设、电气设备使用是否符合要求1是0否")
	private String electricalEquipment;

	/**
	 * 是否开展经常性消防安全宣传工作1是0否
	 */
	@ApiModelProperty("是否开展经常性消防安全宣传工作1是0否")
	private String safetyPromotion;

	/**
	 * 是否组织员工每半年至少开展一次消防培训1是0否
	 */
	@ApiModelProperty("是否组织员工每半年至少开展一次消防培训1是0否")
	private String fireTraining;

	/**
	 * 新上岗的员工是否开展岗前消防培训1是0否
	 */
	@ApiModelProperty("新上岗的员工是否开展岗前消防培训1是0否")
	private String prejobFireTraining;

	/**
	 * 是否在人群密集场所区域内张贴通知1是0否
	 */
	@ApiModelProperty("是否在人群密集场所区域内张贴通知1是0否")
	private String postNotice;

	/**
	 * 用火、动火是否符合要求1是0否
	 */
	@ApiModelProperty("用火、动火是否符合要求1是0否")
	private String meetsEquirements;

	/**
	 * 是否每日开展防火巡查1是0否
	 */
	@ApiModelProperty("是否每日开展防火巡查1是0否")
	private String fireInspection;

	/**
	 * 是否制定灭火和应急疏散预案，并定期开展演练1是0否
	 */
	@ApiModelProperty("是否制定灭火和应急疏散预案，并定期开展演练1是0否")
	private String emergencyEvacuationPlan;

	/**
	 * 门头照
	 */
	@ApiModelProperty("门头照")
	private String doorPic;

}
