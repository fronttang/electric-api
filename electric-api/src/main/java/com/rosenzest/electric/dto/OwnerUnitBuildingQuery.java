package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel
@NoArgsConstructor
public class OwnerUnitBuildingQuery extends PageQuery {

	@ApiModelProperty("业主单元ID")
	@NotNull(message = "业主单元ID不能为空")
	private Long unitId;

	private String type;

	@ApiModelProperty("状态,见字典:initial_test_status")
	private String status;

	@ApiModelProperty("名称/地址")
	private String keyword;
}
