package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB9 {

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

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
	 * 电气设备对地电压
	 */
	@ApiModelProperty("电气设备对地电压")
	private String voltage;

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
