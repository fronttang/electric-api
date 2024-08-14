package com.rosenzest.electric.formb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FormB1 {

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
	 * 三相检测
	 */
	@ApiModelProperty("三相检测")
	private ThreePhase threePhase;

	/**
	 * 单相检测
	 */
	@ApiModelProperty("单相检测")
	private SinglePhase singlePhase;

	/**
	 * 判定结果 合格/不合格
	 */
	@ApiModelProperty("判定结果 合格/不合格")
	private String result;

	/**
	 * 类型 三相/单相
	 */
	@ApiModelProperty("类型 三相/单相")
	private String type;

	/**
	 * 单相检测
	 */
	@Data
	public static class SinglePhase {
		/**
		 * L
		 */
		@ApiModelProperty("L")
		private ABNCLData L;

		/**
		 * N
		 */
		@ApiModelProperty("N")
		private ABNCLData N;

		/**
		 * 电压（V）LN
		 */
		@ApiModelProperty("电压（V）LN")
		private String ln;
	}

	/**
	 * 三相检测
	 */
	@Data
	public static class ThreePhase {

		/**
		 * A
		 */
		@ApiModelProperty("A")
		private ABNCLData A;

		/**
		 * B
		 */
		@ApiModelProperty("B")
		private ABNCLData B;

		/**
		 * N
		 */
		@ApiModelProperty("N")
		private ABNCLData N;

		/**
		 * C
		 */
		@ApiModelProperty("C")
		private ABNCLData C;

		/**
		 * 电压（V）
		 */
		@ApiModelProperty("电压（V）")
		private VoltageData voltage;
	}

	@Data
	public static class ABNCLData {

		/**
		 * 电源侧温度（℃）
		 */
		@ApiModelProperty("电源侧温度（℃）")
		private String powerTemp;

		/**
		 * 负荷侧温度（℃）
		 */
		@ApiModelProperty("负荷侧温度（℃）")
		private String loadTemp;

		/**
		 * 电流（A）
		 */
		@ApiModelProperty("电流（A）")
		private String current;

	}

	/**
	 * 电压（V）
	 */
	@Data
	public static class VoltageData {

		/**
		 * A-B
		 */
		@ApiModelProperty("A-B")
		private String ab;

		/**
		 * A-C
		 */
		@ApiModelProperty("A-C")
		private String ac;

		/**
		 * B-C
		 */
		@ApiModelProperty("B-C")
		private String bc;
	}

}
