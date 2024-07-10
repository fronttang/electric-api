package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel
@NoArgsConstructor
public class OwnerUnitAreaQuery extends PageQuery {

	/**
	 * 业主单元ID
	 */
	@NotNull(message = "业主单元ID不能为空")
	@ApiModelProperty(value = "业主单元ID", required = true)
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型,见字典:owner_unit_area_type")
	private String type;

	/**
	 * 搜索关键字
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;
}
