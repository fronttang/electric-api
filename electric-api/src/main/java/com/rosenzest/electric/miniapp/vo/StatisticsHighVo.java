package com.rosenzest.electric.miniapp.vo;

import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo.DangerStatistics;
import com.rosenzest.electric.miniapp.vo.AreaUserIndexVo.UnitStatistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 高风险 场所统计
 */
@Data
public class StatisticsHighVo {

	/**
	 * 场所统计
	 */
	@ApiModelProperty("场所统计")
	private UnitStatistics unit;

	/**
	 * 隐患统计
	 */
	@ApiModelProperty("隐患统计")
	private DangerStatistics danger;

}
