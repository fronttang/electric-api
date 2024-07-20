package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB13 {

	/**
	 * 检测地点
	 */
	@ApiModelProperty("检测地点")
	private String location;

	/**
	 * 设备名称
	 */
	@ApiModelProperty("设备名称")
	private String deviceName;

	/**
	 * 设备编号
	 */
	@ApiModelProperty("设备编号")
	private String deviceCode;

	/**
	 * 规格型号
	 */
	@ApiModelProperty("规格型号")
	private String specifications;

	/**
	 * 绝缘等级
	 */
	@ApiModelProperty("绝缘等级")
	private String insulation;

	/**
	 * 额定电压（V）
	 */
	@ApiModelProperty("额定电压（V）")
	private String ratedVoltage;

	/**
	 * 总判定结果 合格/不合格
	 */
	@ApiModelProperty("总判定结果 合格/不合格")
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

	/**
	 * A-B
	 */
	@ApiModelProperty("A-B")
	private ABCData ab;

	/**
	 * A-C
	 */
	@ApiModelProperty("A-C")
	private ABCData ac;

	/**
	 * B-C
	 */
	@ApiModelProperty("B-C")
	private ABCData bc;

	@Data
	public static class ABCData {

		/**
		 * 绝缘电阻值（MΩ）
		 */
		@ApiModelProperty("绝缘电阻值（MΩ）")
		private String resistance;

		/**
		 * 检查结果 合格/不合格
		 */
		@ApiModelProperty("检查结果 合格/不合格")
		private String result;
	}

}
