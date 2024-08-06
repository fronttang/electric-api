package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DangerPassDto {

	/**
	 * 隐患ID
	 */
	@NotNull(message = "隐患ID不能为空")
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	/**
	 * 整改照片
	 */
	@ApiModelProperty("整改照片")
	private String pic;

}
