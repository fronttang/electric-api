package com.rosenzest.electric.design.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 设备照片集
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
public class ElectricityProjectDeviceImageDto {

	/**
	 * ID
	 */
	@ApiModelProperty("id")
	private Long id;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 项目设备id
	 */
	@ApiModelProperty("项目设备id")
	private Long deviceId;

	/**
	 * 照片集名称
	 */
	@ApiModelProperty("照片集名称")
	private String imageName;

	/**
	 * 照片集类型
	 */
	@ApiModelProperty("照片集类型")
	private String imageType;

}
