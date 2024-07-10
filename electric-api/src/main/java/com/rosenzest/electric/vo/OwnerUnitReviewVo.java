package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OwnerUnitReviewVo {

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
	 * 总隐患数
	 */
	@ApiModelProperty("总隐患数")
	private Integer dangers;

	/**
	 * 待整改数
	 */
	@ApiModelProperty("待整改数")
	private Integer rectifications;

	/**
	 * 待复检数
	 */
	@ApiModelProperty("待复检数")
	private Integer reexaminations;

	/**
	 * 完成数
	 */
	@ApiModelProperty("完成数")
	private Integer finishs;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典复检状态：again_test_status")
	private String status;

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
