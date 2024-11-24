package com.rosenzest.electric.design.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 电力设计项目
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
public class ElectricityProjectDto {

	/**
	 * 项目id
	 */
	@ApiModelProperty("id,修改时传")
	private Long id;

	/**
	 * 项目名称
	 */
	@NotBlank(message = "项目名称不能为空")
	@ApiModelProperty("项目名称")
	private String name;

}
