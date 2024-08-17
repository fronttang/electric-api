package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB14 {

	public static final String TYPE_RESIDUALCURRENT = "residualCurrent";

	public static final String TYPE_ALARMTIME = "alarmTime";

	/**
	 * 剩余电流值检测
	 */
	@ApiModelProperty("剩余电流值检测")
	private ResidualCurrent residualCurrent;

	/**
	 * 报警时间检测
	 */
	@ApiModelProperty("报警时间检测")
	private AlarmTime alarmTime;

	/**
	 * 类型 residualCurrent/alarmTime
	 */
	private String type;

	/**
	 * 报警时间检测
	 */
	@Data
	public static class AlarmTime {
		/**
		 * 检测地点
		 */
		@ApiModelProperty("检测地点")
		private String address;

		/**
		 * 设备型号
		 */
		@ApiModelProperty("设备型号")
		private String deviceModel;

		/**
		 * 额定报警时间（ms）
		 */
		@ApiModelProperty("额定报警时间（ms）")
		private String expected;

		/**
		 * 实际报警时间（ms）
		 */
		@ApiModelProperty("实际报警时间（ms）")
		private String actual;

		/**
		 * 判定结果 合格/不合格
		 */
		@ApiModelProperty("判定结果 合格/不合格")
		private String result;
	}

	@Data
	public static class ResidualCurrent {
		/**
		 * 检测地点
		 */
		@ApiModelProperty("检测地点")
		private String address;

		/**
		 * 检测位置
		 */
		@ApiModelProperty("检测位置")
		private String location;

		/**
		 * 检测设备
		 */
		@ApiModelProperty("检测设备")
		private String device;

		/**
		 * 剩余电流有效值（mA）
		 */
		@ApiModelProperty("剩余电流有效值")
		private String residualCurrent;

		/**
		 * 检查结果
		 */
		@ApiModelProperty("检查结果")
		private String testResults;

		/**
		 * 判定结果 合格/不合格
		 */
		@ApiModelProperty("判定结果 合格/不合格")
		private String result;
	}

}
