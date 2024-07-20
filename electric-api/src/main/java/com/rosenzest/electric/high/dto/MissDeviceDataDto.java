package com.rosenzest.electric.high.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public class MissDeviceDataDto {

	@Data
	public static class RentalHouseType1 {

		/**
		 * 乙级防火门
		 */
		@ApiModelProperty("乙级防火门")
		private Integer fireDoorB;

		/**
		 * 紧急逃生口
		 */
		@ApiModelProperty("紧急逃生口")
		private Integer emergencyExit;

		/**
		 * 逃生缓降器
		 */
		@ApiModelProperty("逃生缓降器")
		private Integer escape;

		/**
		 * 箭头指示标志灯
		 */
		@ApiModelProperty("箭头指示标志灯")
		private Integer indicator;

		/**
		 * 安全出口标志灯
		 */
		@ApiModelProperty("安全出口标志灯")
		private Integer exitSignLights;

		/**
		 * 应急照明灯具
		 */
		@ApiModelProperty("应急照明灯具")
		private Integer emergencyLighting;

		/**
		 * 4kgABC干粉灭火器
		 */
		@ApiModelProperty("4kgABC干粉灭火器")
		private Integer kg4ABC;

		/**
		 * 独立式感烟火灾探测报警器
		 */
		@ApiModelProperty("独立式感烟火灾探测报警器")
		private Integer fireAlarm;

		/**
		 * 消防软管卷盘
		 */
		@ApiModelProperty("消防软管卷盘")
		private Integer fireHoseReels;
	}

	@Data
	public static class RentalHouseType2 {

		/**
		 * 火灾自动报警主机
		 */
		@ApiModelProperty("火灾自动报警主机")
		private Integer alarmHost;

		/**
		 * 火灾声光报警器
		 */
		@ApiModelProperty("火灾声光报警器")
		private Integer alarm;

		/**
		 * 手动报警按钮
		 */
		@ApiModelProperty("手动报警按钮")
		private Integer manualAlarm;

		/**
		 * 点型光电感烟探测器
		 */
		@ApiModelProperty("点型光电感烟探测器")
		private Integer smokeDetectors;
	}

	@Data
	public static class Small {

		/**
		 * 甲级防火门
		 */
		@ApiModelProperty("甲级防火门")
		private Integer fireDoorA;

		/**
		 * 乙级防火门
		 */
		@ApiModelProperty("乙级防火门")
		private Integer fireDoorB;

		/**
		 * 紧急逃生口
		 */
		@ApiModelProperty("紧急逃生口")
		private Integer emergencyExit;

		/**
		 * 逃生缓降器
		 */
		@ApiModelProperty("逃生缓降器")
		private Integer escape;

		/**
		 * 应急照明灯具
		 */
		@ApiModelProperty("应急照明灯具")
		private Integer emergencyLighting;

		/**
		 * 智能红外体感无线感烟探测报警器
		 */
		@ApiModelProperty("智能红外体感无线感烟探测报警器")
		private Integer smokeDetectors;

		/**
		 * 独立式感烟火灾报警器
		 */
		@ApiModelProperty("独立式感烟火灾报警器")
		private Integer fireAlarm;

		/**
		 * 4kg手提式干粉灭火器
		 */
		@ApiModelProperty("4kg手提式干粉灭火器")
		private Integer kg4ABC;

		/**
		 * 消防软管
		 */
		@ApiModelProperty("消防软管")
		private Integer fireHose;

		/**
		 * 简易喷淋
		 */
		@ApiModelProperty("简易喷淋")
		private Integer spray;

		/**
		 * 可燃气探测装置
		 */
		@ApiModelProperty("可燃气探测装置")
		private Integer combustible;

		/**
		 * 燃气自动切断阀
		 */
		@ApiModelProperty("燃气自动切断阀")
		private Integer automaticShutOff;

		/**
		 * 安全出口
		 */
		@ApiModelProperty("安全出口")
		private Integer exit;
	}
}
