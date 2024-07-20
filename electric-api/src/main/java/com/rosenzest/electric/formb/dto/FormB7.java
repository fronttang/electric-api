package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB7 {

	/**
	 * 被测设备型号
	 */
	@ApiModelProperty("被测设备型号")
	private String deviceModel;

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 额定电流（A)
	 */
	@ApiModelProperty("额定电流（A)")
	private String ratedCurrent;

	/**
	 * 额定动作电流（mA）
	 */
	@ApiModelProperty("额定动作电流（mA）")
	private String ratedActionCurrent;

	/**
	 * 检测结果
	 */
	@ApiModelProperty("检测结果")
	private String testResults;

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
