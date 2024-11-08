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
public class OwnerUnitReviewQuery extends PageQuery {

	@ApiModelProperty(value = "项目ID", required = true)
	@NotNull(message = "项目ID不能为空")
	private Long projectId;

	private String type;

	@ApiModelProperty("状态,见字典:again_test_status")
	private String status;

	/** 区县 */
	@ApiModelProperty("区县")
	private String district;

	/** 街道 */
	@ApiModelProperty("街道")
	private String street;

	/** 社区 */
	@ApiModelProperty("社区")
	private String community;

	/** 村 */
	@ApiModelProperty("村")
	private String hamlet;

	@ApiModelProperty("名称/地址")
	private String keyword;

	private Long workerId;

	/**
	 * 高风险类型
	 */
	@ApiModelProperty("高风险类型,见字典：high_risk_type")
	private String highRiskType;
}
