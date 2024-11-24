package com.rosenzest.electric.design.dto;

import javax.validation.constraints.NotNull;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectDeviceQuery extends PageQuery {

	@NotNull(message = "项目ID不能为空")
	@ApiModelProperty(value = "项目ID", required = true)
	private Long projectId;

	@ApiModelProperty(value = "搜索关键字")
	private String keyword;
}
