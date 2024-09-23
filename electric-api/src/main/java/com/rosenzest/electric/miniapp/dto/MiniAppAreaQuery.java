package com.rosenzest.electric.miniapp.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MiniAppAreaQuery {

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 区县
	 */
	@ApiModelProperty("区县")
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
	 * 村
	 */
	@ApiModelProperty("村")
	private String hamlet;
}
