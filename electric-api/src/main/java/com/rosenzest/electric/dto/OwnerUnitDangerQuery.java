package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class OwnerUnitDangerQuery {

	@NotNull(message = "业主单元ID不能为空")
	@ApiModelProperty("业主单元")
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 初检时间
	 */
	@ApiModelProperty("初检时间,格式yyyy-MM-dd")
	private String date;

	/**
	 * 搜索关键字
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;
}
