package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormBB1 {

	/**
	 * 辐射率
	 */
	@ApiModelProperty("辐射率")
	private String radiation;

	/**
	 * 天气
	 */
	@ApiModelProperty("天气")
	private String weather;

	/**
	 * 测试距离（m)
	 */
	@ApiModelProperty("测试距离（m)")
	private String distance;

	/**
	 * 风速（m/s）
	 */
	@ApiModelProperty("风速（m/s）")
	private String windSpeed;

	/**
	 * 检测时间 yyyy-MM-dd
	 */
	@ApiModelProperty("检测时间 yyyy-MM-dd")
	private String detectionTime;

	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 被测设备名称
	 */
	@ApiModelProperty("被测设备名称")
	private String deviceName;

	/**
	 * 设备编号
	 */
	@ApiModelProperty("设备编号")
	private String deviceCode;

	/**
	 * 图片编号
	 */
	@ApiModelProperty("图片编号")
	private String imageCode;

	/**
	 * 最高温度（°C）
	 */
	@ApiModelProperty("最高温度")
	private String maxTemp;

	/**
	 * 额定电压（V）
	 */
	@ApiModelProperty("额定电压（V）")
	private String ratedVoltage;

	/**
	 * 额定电流（A)
	 */
	@ApiModelProperty("额定电流（A)")
	private String ratedCurrent;

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

	/**
	 * 判定结果 合格/不合格
	 */
	@ApiModelProperty("判定结果 合格/不合格")
	private String result;
}
