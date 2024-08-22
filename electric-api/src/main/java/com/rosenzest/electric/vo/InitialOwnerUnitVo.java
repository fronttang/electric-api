package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InitialOwnerUnitVo {

	/**
	 * id
	 */
	@ApiModelProperty("id")
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
	 * 层数
	 */
	@ApiModelProperty("层数")
	private Integer layers;

	/**
	 * 户数
	 */
	@ApiModelProperty("户数")
	private Integer doors;

	/**
	 * 入户数
	 */
	@ApiModelProperty("入户数")
	private Integer households;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典初检状态：initial_test_status")
	private String status;

	/**
	 * 区县
	 */
	@ApiModelProperty("区县")
	private String district;

	/**
	 * 区县
	 */
	@ApiModelProperty("区县")
	private String districtName;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
	private String street;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
	private String streetName;

	/**
	 * 社区
	 */
	@ApiModelProperty("社区")
	private String community;

	/**
	 * 社区
	 */
	@ApiModelProperty("社区")
	private String communityName;

	/**
	 * 村
	 */
	@ApiModelProperty("村")
	private String hamlet;

	/**
	 * 村
	 */
	@ApiModelProperty("村")
	private String hamletName;

	/**
	 * 高风险类型
	 */
	@ApiModelProperty("高风险类型,见字典：high_risk_type")
	private String highRiskType;

	/**
	 * 总隐患数
	 */
	@ApiModelProperty("总隐患数")
	private Integer dangers;

	/**
	 * 待整改数
	 */
	@ApiModelProperty("待整改数")
	private Integer rectifications;

	/**
	 * 待复检数
	 */
	@ApiModelProperty("待复检数")
	private Integer reexaminations;

	/**
	 * 完成数
	 */
	@ApiModelProperty("完成数")
	private Integer finishs;

	/**
	 * 充电桩数
	 */
	@ApiModelProperty("充电桩数")
	private Integer piles;

	/**
	 * 当前轮次
	 */
	@ApiModelProperty("当前轮次")
	private Integer rounds;

	/**
	 * 检测模块
	 */
	@ApiModelProperty("检测模块，见数据字典：detect_module")
	private String detectModule;

	/**
	 * 是否开启隐患通知单
	 */
	@ApiModelProperty("是否开启隐患通知单 0否 1是")
	private String isDangerNotice = "0";

	/**
	 * 是否完成入户率
	 */
	@ApiModelProperty("是否完成/完成入户率 0否 1是")
	private String isComplete = "0";

	/**
	 * 无法检测
	 */
	@ApiModelProperty("是否无法检测 0否 1是")
	private String isTest;

	/**
	 * 无法检测原因
	 */
	@ApiModelProperty("无法检测原因")
	private String isTestReason;

	/**
	 * 温度
	 */
	@ApiModelProperty("温度")
	private String temperature;

	/**
	 * 湿度
	 */
	@ApiModelProperty("湿度")
	private String humidity;
}
