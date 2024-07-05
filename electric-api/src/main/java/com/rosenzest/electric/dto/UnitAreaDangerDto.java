package com.rosenzest.electric.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class UnitAreaDangerDto {

	/**
	 * ID
	 */
	@ApiModelProperty("隐患数据ID")
	private Long id;

	/**
	 * 单元ID
	 */
	@NotNull(message = "业主单元ID不能为空")
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
	@ApiModelProperty("检测表类型:A/B/C")
	@NotBlank(message = "检测表类型不能为空")
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
	@NotBlank(message = "隐患位置不能为空")
	@ApiModelProperty("位置")
	private String location;

	/**
	 * 隐患图片
	 */
	@ApiModelProperty("隐患图片")
	private String dangerPic;

	/**
	 * B类表数据
	 */
	@ApiModelProperty("B类表数据")
	private JSONObject formb;
}
