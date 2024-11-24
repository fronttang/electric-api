package com.rosenzest.electric.design.dto;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectQuery extends PageQuery {

	@ApiModelProperty(value = "搜索关键字")
	private String keyword;
}
