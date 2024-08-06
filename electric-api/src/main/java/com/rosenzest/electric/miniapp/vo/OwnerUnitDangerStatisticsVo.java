package com.rosenzest.electric.miniapp.vo;

import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitDangerStatisticsVo extends OwnerUnitOverviewVo implements IDangerStatisticsVo {

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 楼栋名称
	 */
	@ApiModelProperty("楼栋名称")
	private String buildingName;

	/**
	 * 楼栋类型 1配电房/2宿舍楼/0其他
	 */
	@ApiModelProperty("楼栋类型 1配电房/2宿舍楼/0其他")
	private String buildingType;

	/**
	 * 总隐患数
	 */
	@ApiModelProperty("总隐患数")
	private Long danger;

	/**
	 * 待整改数
	 */
	@ApiModelProperty("待整改数")
	private Long rectification;

	/**
	 * 待复检数
	 */
	@ApiModelProperty("待复检数")
	private Long review;

	/**
	 * 完成数
	 */
	@ApiModelProperty("完成数")
	private Long finish;

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
