package com.rosenzest.electric.miniapp.vo;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AreaUserIndexVo {

	/**
	 * 用户姓名
	 */
	@ApiModelProperty("用户姓名")
	private String userName;

	/**
	 * 街区名
	 */
	@ApiModelProperty("街区名")
	private String areaName;

	/**
	 * 业主单元统计
	 */
	@ApiModelProperty("业主单元统计")
	private UnitStatistics unit;

	/**
	 * 隐患统计
	 */
	@ApiModelProperty("隐患统计")
	private DangerStatistics danger;

	/**
	 * 今日统计
	 */
	@ApiModelProperty("今日统计")
	private TodayStatistics today;

	@Data
	public static class UnitStatistics {

		/**
		 * 总数
		 */
		@ApiModelProperty("总数")
		private Long total = 0L;

		/**
		 * 检测中
		 */
		@ApiModelProperty("检测中")
		private Long detecting = 0L;

		/**
		 * 已检测
		 */
		@ApiModelProperty("已检测")
		private Long detected = 0L;

		/**
		 * 无法检测
		 */
		@ApiModelProperty("无法检测")
		private Long undetect = 0L;
	}

	@Data
	public static class DangerStatistics implements IDangerStatisticsVo {

		/**
		 * 总隐患数
		 */
		@ApiModelProperty("总隐患数")
		private Long danger = 0L;

		/**
		 * 待整改数
		 */
		@ApiModelProperty("待整改数")
		private Long rectification = 0L;

		/**
		 * 待复检数
		 */
		@ApiModelProperty("待复检数")
		private Long review = 0L;

		/**
		 * 完成数
		 */
		@ApiModelProperty("完成数")
		private Long finish = 0L;

		/**
		 * 隐患数
		 */
		@ApiModelProperty("总隐患汇总")
		private Map<String, Long> dangers;

		/**
		 * 完成汇总数
		 */
		@ApiModelProperty("完成汇总")
		private Map<String, Long> finishs;

		/**
		 * 待整改汇总数
		 */
		@ApiModelProperty("待整改汇总")
		private Map<String, Long> rectifications;

		/**
		 * 待复检汇总数
		 */
		@ApiModelProperty("待复检汇总")
		private Map<String, Long> reviews;
	}

	@Data
	public static class TodayStatistics {

		/**
		 * 检测楼栋
		 */
		@ApiModelProperty("检测楼栋")
		private Long unit = 0L;

		/**
		 * 新增隐患
		 */
		@ApiModelProperty("新增隐患")
		private Long danger = 0L;

		/**
		 * 整改隐患
		 */
		@ApiModelProperty("整改隐患")
		private Long finish = 0L;
	}
}
