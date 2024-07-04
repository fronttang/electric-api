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
public class OwnerUnitQueryDto extends PageQueryDto {

	@ApiModelProperty("项目ID")
	@NotNull(message = "项目ID不能为空")
	private Long projectId;

	@ApiModelProperty("类型,见字典:project_type")
	@NotNull(message = "类型不能为空")
	private String type;

	@ApiModelProperty("状态")
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
}
