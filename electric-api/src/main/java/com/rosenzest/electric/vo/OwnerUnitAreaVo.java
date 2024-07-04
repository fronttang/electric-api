package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OwnerUnitAreaVo {
	/**
	 * id
	 */
	@ApiModelProperty("id")
	private Long id;

	/**
	 * 单元ID
	 */
	@ApiModelProperty("单元ID")
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 楼层
	 */
	@ApiModelProperty("楼层")
	private Integer floor;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;

	/**
	 * 类型1公共区域，2户
	 */
	@ApiModelProperty("类型,见字典:owner_unit_area_type")
	private String type;

	/**
	 * 隐患数
	 */
	@ApiModelProperty("隐患数")
	private Integer dangers;
}
