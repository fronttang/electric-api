package com.rosenzest.electric.owner.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OwnerUnitListVo {

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

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
