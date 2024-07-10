package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Data
public class OwnerUnitBuildingDto {

	/**
	 * ID
	 */
	@ApiModelProperty("楼栋ID,修改时传入")
	private Long id;

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	@NotNull(message = "业主单元ID不能为空")
	private Long unitId;

	/**
	 * 楼栋名称
	 */
	@ApiModelProperty("楼栋名称")
	private String name;

	/**
	 * 类型配电房/宿舍楼/其他
	 */
	@ApiModelProperty("类型,1配电房/2宿舍楼/0其他, 见字典：industrial_area_building_type")
	private String type;

	/**
	 * 宿舍楼户数
	 */
	@ApiModelProperty("宿舍楼户数")
	private Long doors;

}
