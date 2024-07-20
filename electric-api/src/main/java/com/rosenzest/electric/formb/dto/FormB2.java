package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB2 {

	/**
	 * 被测设备类型 楼栋/变配电装置
	 */
	@ApiModelProperty("被测设备类型 楼栋/变配电装置")
	private String deviceType;

	/**
	 * 接地系统
	 */
	@ApiModelProperty("接地系统")
	private String groundingSystem;

	/**
	 * 测试地点
	 */
	@ApiModelProperty("测试地点")
	private String location;

	/**
	 * 被测设备名称
	 */
	@ApiModelProperty("被测设备名称")
	private String deviceName;

	/**
	 * 接地电阻
	 */
	@ApiModelProperty("接地电阻")
	private String resistance;

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
