package com.rosenzest.electric.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OwnerUnitAreaDto {

	private Long id;

	/**
	 * 单元ID
	 */
	@ApiModelProperty(value = "业主单元ID", required = true)
	@NotNull(message = "业主单元ID不能为空")
	private Long unitId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotBlank(message = "名称不能为空")
	private String name;

	/**
	 * 楼层
	 */
	@ApiModelProperty("楼层")
	private Integer floor;

	/**
	 * 类型1公共区域，2户
	 */
	@ApiModelProperty("类型,见字典:owner_unit_area_type")
	private String type;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;
}
