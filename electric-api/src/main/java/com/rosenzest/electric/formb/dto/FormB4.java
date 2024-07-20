package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB4 {

	/**
	 * 灯具类型 普通/仓库
	 */
	@ApiModelProperty("灯具类型 普通/仓库")
	private String lampType;

	/**
	 * 被测设备名称
	 */
	@ApiModelProperty("被测设备名称")
	private String deviceName;

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 距可燃物距离（m）
	 */
	@ApiModelProperty("距可燃物距离")
	private String combustibles;

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
