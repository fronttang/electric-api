package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InitialOwnerUnitVo {

	/**
	 * id
	 */
	@ApiModelProperty("id")
	private Long id;
	
	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;
	
	/**
	 * 层数
	 */
	@ApiModelProperty("层数")
	private Integer layers;
	
	/**
	 * 户数
	 */
	@ApiModelProperty("户数")
	private Integer doors;
	
	/**
	 * 入户数
	 */
	@ApiModelProperty("入户数")
	private Integer households;
	
	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;
	
	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典初检状态：initial_test_status")
	private String status;
}
