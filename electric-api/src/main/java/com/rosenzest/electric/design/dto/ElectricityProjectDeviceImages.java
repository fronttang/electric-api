package com.rosenzest.electric.design.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ElectricityProjectDeviceImages {

	@ApiModelProperty("id值，排序用的")
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 图片/视频列表
	 */
	@ApiModelProperty("图片/视频列表")
	private List<String> images;
}
