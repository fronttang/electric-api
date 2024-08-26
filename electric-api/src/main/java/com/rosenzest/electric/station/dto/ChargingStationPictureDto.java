package com.rosenzest.electric.station.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChargingStationPictureDto {

	/**
	 * 业主单元ID
	 */
	@NotNull(message = "业主单元ID不能为空")
	@ApiModelProperty(value = "业主单元ID", required = true)
	private Long unitId;

	/**
	 * 检测模块
	 */
	@ApiModelProperty("检测模块")
	private String module;

	/**
	 * 轮次
	 */
	@ApiModelProperty("轮次,添加/修改时不传，查询时传")
	private Integer rounds;

	/**
	 * 充电桩ID
	 */
	@ApiModelProperty("充电桩ID,添加充电桩合格照片时传")
	private Long pileId;

	/**
	 * 合格照片
	 */
	@ApiModelProperty("合格照片")
	private String pictures;
}
