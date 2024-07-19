package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FireStationQuery {

	/**
	 * 项目ID
	 */
	@ApiModelProperty(value = "项目ID", required = true)
	@NotNull(message = "项目ID不能为空")
	private Long projectId;

	/**
	 * 区
	 */
	@ApiModelProperty(value = "区", required = true)
	@NotBlank(message = "区不能为空")
	private String district;

	/**
	 * 街道
	 */
	@NotBlank(message = "街道不能为空")
	@ApiModelProperty(value = "街道", required = true)
	private String street;

	/**
	 * 社区
	 */
	@NotBlank(message = "社区不能为空")
	@ApiModelProperty(value = "社区", required = true)
	private String community;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 类型1社区微型2重点单位3社区小型4乡镇传职
	 */
	@ApiModelProperty("类型1社区微型2重点单位3社区小型4乡镇传职")
	private String type;
}
