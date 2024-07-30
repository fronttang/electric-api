package com.rosenzest.electric.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BrandDto {

	@ApiModelProperty("id")
	private String id;

	@ApiModelProperty("品牌/生产厂家名称")
	private String name;
}
