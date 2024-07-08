package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class OwnerUnitAgainDangerQuery {

	@NotNull(message = "业主单元ID不能为空")
	@ApiModelProperty("业主单元")
	private Long unitId;

	/**
	 * 复检状态
	 */
	@ApiModelProperty("复检状态")
	private String status;

	/**
	 * 初检时间
	 */
	@ApiModelProperty("初检时间,格式yyyy-MM-dd")
	private String initalDate;

	/**
	 * 复检时间
	 */
	@ApiModelProperty("复检时间,格式yyyy-MM-dd")
	private String againDate;

	/**
	 * 搜索关键字
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;

	/**
	 * 类型,见字典:owner_unit_area_type
	 */
	@ApiModelProperty("类型,公共区域/户，见字典:owner_unit_area_type ")
	private String type;
}
