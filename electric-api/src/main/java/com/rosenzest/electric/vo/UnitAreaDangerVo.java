package com.rosenzest.electric.vo;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class UnitAreaDangerVo {

	/**
	 * ID
	 */
	@ApiModelProperty("隐患数据ID")
	private Long id;

	/**
	 * 单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 单元区域ID
	 */
	@ApiModelProperty("公共区域/户ID")
	private Long unitAreaId;

	/**
	 * 隐患ID
	 */
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	/**
	 * 检测表ID
	 */
	@ApiModelProperty("检测表ID")
	private Long formId;

	/**
	 * 检测表编号
	 */
	@ApiModelProperty("检测表编号")
	private String formCode;

	/**
	 * 检测表类型A/B/C
	 */
	@ApiModelProperty(" 检测表类型:A/B/C")
	private String type;

	/**
	 * 隐患等级
	 */
	@ApiModelProperty("隐患等级")
	private String level;

	/**
	 * 隐患描述
	 */
	@ApiModelProperty("隐患描述")
	private String description;

	/**
	 * 整改建议
	 */
	@ApiModelProperty("整改建议")
	private String suggestions;

	/**
	 * 位置
	 */
	@ApiModelProperty("位置")
	private String location;

	/**
	 * 隐患图片
	 */
	@ApiModelProperty("隐患图片")
	private String dangerPic;

	/**
	 * 整改图
	 */
	@ApiModelProperty("整改图")
	private String rectificationPic;

	/**
	 * 检测图
	 */
	@ApiModelProperty("检测图")
	private String detectPic;

	/**
	 * 整改未通过原因
	 */
	@ApiModelProperty("整改未通过原因")
	private String reason;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,同字典复检状态：again_test_status")
	private String status;

	/**
	 * 检测员
	 */
	@ApiModelProperty("检测员")
	private String inspector;

	/**
	 * 检测员ID
	 */
	@ApiModelProperty("检测员ID")
	private Long inspectorId;

	/**
	 * 初检时间
	 */
	@ApiModelProperty("初检时间")
	private Date initialTime;

	/**
	 * B类表数据
	 */
	@ApiModelProperty("B类表数据")
	private JSONObject formb;
}
