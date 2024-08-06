package com.rosenzest.electric.miniapp.dto;

import javax.validation.constraints.NotNull;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MiniAppOwnerUnitDangerQuery extends PageQuery {

	/**
	 * 业主单元ID
	 */
	@NotNull(message = "业主单元ID不能为空")
	@ApiModelProperty(value = "业主单元ID", required = true)
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 搜索关键字
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;

	/**
	 * 类型 1公共区域/2户 不传为全部
	 */
	@ApiModelProperty("类型 1公共区域/2户 不传为全部")
	private String unitAreatype;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,0待整改 1待复检 2完成")
	private String status;

	/**
	 * 初检开始时间
	 */
	@ApiModelProperty("初检开始时间：格式yyyy-MM-dd")
	private String starDate;

	/**
	 * 初检结束时间
	 */
	@ApiModelProperty("初检结束时间：格式yyyy-MM-dd")
	private String endDate;
}
