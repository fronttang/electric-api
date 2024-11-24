package com.rosenzest.electric.design.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ElectricityProjectDeviceVo {

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
	 * 设备名称
	 */
	@ApiModelProperty("设备名称")
	private String deviceName;

	/**
	 * 设备位置
	 */
	@ApiModelProperty("设备位置")
	private String deviceLocation;

	/**
	 * 设备类型
	 */
	@ApiModelProperty("设备类型")
	private String deviceType;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty("创建时间")
	private Date createTime;

}
