package com.rosenzest.electric.high.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnitResidentialVo {

	/**
	 * 高风险类型
	 */
	private String highRiskType;

	/**
	 * ID
	 */
	@ApiModelProperty("ID,添加是不传,修改时传")
	private Long id;

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
	 * 项目ID
	 */
	@NotNull(message = "项目ID不能为空")
	@ApiModelProperty(value = "项目ID", required = true)
	private Long projectId;

	/**
	 * 项目名称
	 */
	// private String projectName;

	/**
	 * 联系人/负责人/业主/楼栋长
	 */
	@ApiModelProperty("负责人")
	private String contact;

	/**
	 * 联系电话
	 */
	@ApiModelProperty("联系电话")
	private String phone;

	/**
	 * 建筑面积
	 */
	@ApiModelProperty("建筑面积")
	private String acreage;

	/**
	 * 建筑层数
	 */
	@ApiModelProperty("建筑层数")
	private Integer layers;

	/**
	 * 户数
	 */
	@ApiModelProperty("房间（套间）数")
	private Integer doorNumber;

	/**
	 * 区县
	 */
	@ApiModelProperty("区")
	@NotBlank(message = "区不能为空")
	private String district;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
	@NotBlank(message = "街道不能为空")
	private String street;

	/**
	 * 社区
	 */
	@ApiModelProperty("社区")
	@NotBlank(message = "社区不能为空")
	private String community;

	/**
	 * 4KgABC手提干粉灭火器数量
	 */
	@ApiModelProperty("4KgABC手提干粉灭火器数量")
	private Integer fireExtinguisherQuantity;

	/**
	 * 4KgABC手提干粉灭火器是否完好1是0否
	 */
	@ApiModelProperty("4KgABC手提干粉灭火器是否完好1是0否")
	private String fireExtinguisherStatus;

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
	 * 营业执照
	 */
	@ApiModelProperty("营业执照")
	private String businessLicense;

	/**
	 * 门头照
	 */
	@ApiModelProperty("门头照")
	private String doorPic;
}