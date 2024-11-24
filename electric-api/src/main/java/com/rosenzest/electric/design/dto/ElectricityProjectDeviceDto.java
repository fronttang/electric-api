package com.rosenzest.electric.design.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 电力设计项目设备
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
public class ElectricityProjectDeviceDto {

	/**
	 * ID
	 */
	@ApiModelProperty("id,修改时传")
	private Long id;

	/**
	 * 项目ID
	 */
	@NotNull(message = "项目ID不能为空")
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 设备名称
	 */
	@NotBlank(message = "设备名称不能为空")
	@ApiModelProperty("设备名称")
	private String deviceName;

	/**
	 * 设备位置
	 */
	// @NotBlank(message = "设备位置不能为空")
	@ApiModelProperty("设备位置")
	private String deviceLocation;

	/**
	 * 设备类型
	 */
	@NotBlank(message = "设备类型不能为空")
	@ApiModelProperty("设备类型")
	private String deviceType;

}
