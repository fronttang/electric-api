package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB6 {
	
	/**
	 * 检测位置
	 */
	@ApiModelProperty("检测位置")
	private String location;

	/**
	 * 被测设备型号
	 */
	@ApiModelProperty("被测设备型号")
	private String deviceModel;

	/**
	 * 额定动作电流（mA）
	 */
	@ApiModelProperty("额定动作电流（mA）")
	private String ratedActionCurrent;

	/**
	 * 动作 填写/>1000/不动作
	 */
	@ApiModelProperty("动作 填写/>1000/不动作")
	private String action;

	/**
	 * 表盘读数 0°
	 */
	@ApiModelProperty("表盘读数 0°")
	private String dialValue0;

	/**
	 * 表盘读数 180°
	 */
	@ApiModelProperty("表盘读数 180°")
	private String dialValue180;

	/**
	 * 手动测试 手动动作/手动不动作
	 */
	@ApiModelProperty("手动测试 手动动作/手动不动作")
	private String manualTest;

	/**
	 * 其他情况 未安装/住户不允许断电无法进行测试
	 */
	@ApiModelProperty("其他情况 未安装/住户不允许断电无法进行测试")
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
