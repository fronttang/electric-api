package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 缺失设备
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
public class OwnerUnitMissDeviceDto {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty(value = "业主单元ID", required = true)
	@NotNull(message = "业主单元ID不能为空")
	private Long unitId;

	/**
	 * 楼层
	 */
	@ApiModelProperty("楼层")
	private String floor;

	/**
	 * 类型1楼层设备2楼栋设备
	 */
	@ApiModelProperty("类型1楼层设备2楼栋设备")
	private String type;

	/**
	 * 缺失设备
	 */
	@ApiModelProperty("缺失设备")
	private JSONObject device;

}
