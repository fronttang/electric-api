package com.rosenzest.electric.miniapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OwnerUnitOverviewVo {

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	private String userName;

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty("业主单元名称")
	private String name;

	/**
	 * 地址
	 */
	@ApiModelProperty("业主单元地址")
	private String address;

	/**
	 * 业主单元类型 1城中村2工业园3高风险4充电站
	 */
	@ApiModelProperty("业主单元类型 1城中村2工业园3高风险4充电站")
	private String type;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 区县
	 */
	@ApiModelProperty("区县")
	private String district;

	/**
	 * 区县
	 */
	@ApiModelProperty("区县")
	private String districtName;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
	private String street;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
	private String streetName;

	/**
	 * 社区
	 */
	@ApiModelProperty("社区")
	private String community;

	/**
	 * 社区
	 */
	@ApiModelProperty("社区")
	private String communityName;

	/**
	 * 村
	 */
	@ApiModelProperty("村")
	private String hamlet;

	/**
	 * 村
	 */
	@ApiModelProperty("村")
	private String hamletName;
}
