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
public class OwnerUnitDangerVo {

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
	 * 业主单元名称
	 */
	@ApiModelProperty("业主单元名称")
	private String unitName;

	/**
	 * 检测单位名称
	 */
	@ApiModelProperty("检测单位名称")
	private String detectName;

	/**
	 * 单元区域ID
	 */
	@ApiModelProperty("公共区域/户ID")
	private Long unitAreaId;

	/**
	 * 类型,见字典:owner_unit_area_type
	 */
	@ApiModelProperty("类型,公共区域/户，见字典:owner_unit_area_type")
	private String unitAreatype;

	/**
	 * 公共区域/户名称
	 */
	@ApiModelProperty("公共区域/户名称")
	private String unitAreaName;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 楼栋名称
	 */
	@ApiModelProperty("楼栋名称")
	private String buildingName;

	/**
	 * 隐患ID
	 */
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	/**
	 * 检测表类型:A/B/C
	 */
	@ApiModelProperty("检测表类型:A/B/C")
	private String formType;

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
	@ApiModelProperty("状态,见字典复检状态：again_test_status")
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

	/**
	 * 整改员
	 */
	@ApiModelProperty("整改员")
	private String rectification;

	/**
	 * 整改时间
	 */
	@ApiModelProperty("整改时间")
	private Date rectificationDate;

	/**
	 * 复检员
	 */
	@ApiModelProperty("复检员")
	private String reviewer;

	/**
	 * 复检时间
	 */
	@ApiModelProperty("复检时间")
	private Date reviewerDate;

}
