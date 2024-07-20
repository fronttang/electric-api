package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB12 {

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 其他隐患检测
	 */
	@ApiModelProperty("其他隐患检测")
	private String other;

	/**
	 * 判定结果 合格/不合格
	 */
	@ApiModelProperty("判定结果 合格/不合格")
	private String result;

	/**
	 * 整体外观图
	 */
	@ApiModelProperty("整体外观图")
	private String overallPic;

	/**
	 * 现场检测图
	 */
	@ApiModelProperty("现场检测图")
	private String inspectionPic;

}
