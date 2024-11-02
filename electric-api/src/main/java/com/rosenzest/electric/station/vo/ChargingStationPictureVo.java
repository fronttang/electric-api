package com.rosenzest.electric.station.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChargingStationPictureVo {

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 检测模块
	 */
	@ApiModelProperty("检测模块")
	private String module;

	/**
	 * 轮次
	 */
	@ApiModelProperty("轮次")
	private Integer rounds;

	/**
	 * 充电桩ID
	 */
	@ApiModelProperty("充电桩ID")
	private List<Long> pileId;

	/**
	 * 合格照片
	 */
	@ApiModelProperty("合格照片")
	private String pictures;

	/**
	 * 照片类型1合格照片,2原始记录照片
	 */
	@ApiModelProperty("照片类型1合格照片,2原始记录照片")
	private String type;
}
