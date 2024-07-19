package com.rosenzest.electric.high.vo;

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
public class FireStationVo {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 区
	 */
	@ApiModelProperty("区")
	private String district;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
	private String street;

	/**
	 * 社区
	 */
	@ApiModelProperty("社区")
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
