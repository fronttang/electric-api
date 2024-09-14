package com.rosenzest.electric.station.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 代表照片
 * </p>
 *
 * @author fronttang
 * @since 2024-09-14
 */
@Data
public class OwnerUnitStationPeprePicDto {

	/**
	 * ID
	 */
	@ApiModelProperty("ID,修改时传")
	private Long id;

	/**
	 * 业主单元ID
	 */
	@NotNull(message = "业主单元ID")
	@ApiModelProperty(value = "业主单元ID，不能为空", required = true)
	private Long unitId;

	/**
	 * 编号
	 */
	@ApiModelProperty("编号")
	private String code;

	/**
	 * 图片
	 */
	@ApiModelProperty("图片")
	private String picture;

}
