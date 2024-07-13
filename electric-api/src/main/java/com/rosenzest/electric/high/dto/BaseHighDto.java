package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseHighDto {

	@ApiModelProperty("ID,添加是不传,修改时传")
	private Long id;

	/**
	 * 项目ID
	 */
	@NotNull(message = "项目ID不能为空")
	@ApiModelProperty(value = "项目ID", required = true)
	private Long projectId;
}
