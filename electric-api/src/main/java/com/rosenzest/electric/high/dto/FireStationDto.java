package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 消防站
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
public class FireStationDto {

	@ApiModelProperty("id")
	private Long id;

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
	@NotBlank(message = "名称不能为空")
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 类型1社区微型2重点单位3社区小型4乡镇传职
	 */
	@NotBlank(message = "类型不能为空")
	@ApiModelProperty("类型1社区微型2重点单位3社区小型4乡镇传职")
	private String type;

	/**
	 * 检测表
	 */
	@ApiModelProperty("检测表")
	private JSONObject detect;

	/**
	 * 消防设备
	 */
	@ApiModelProperty("消防设备")
	private JSONObject device;

}
