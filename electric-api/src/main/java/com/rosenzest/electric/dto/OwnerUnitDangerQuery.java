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
public class OwnerUnitDangerQuery extends PageQuery {

	@NotNull(message = "业主单元ID不能为空")
	@ApiModelProperty("业主单元")
	private Long unitId;

	@ApiModelProperty("公共区域/户ID")
	private Long unitAreaId;

	/**
	 * 类型,见字典:owner_unit_area_type
	 */
	@ApiModelProperty("类型,公共区域/户，见字典:owner_unit_area_type")
	private String unitAreatype;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 初检时间
	 */
	@ApiModelProperty("初检时间,格式yyyy-MM-dd")
	private String initialDate;

	/**
	 * 复检时间
	 */
	@ApiModelProperty("复检时间,格式yyyy-MM-dd")
	private String reviewDate;

	/**
	 * 搜索关键字
	 */
	@ApiModelProperty("搜索关键字")
	private String keyword;

	/**
	 * 检测表ID
	 */
	@ApiModelProperty("检测表ID,B类表不传ID,只传编号和类型")
	private Long formId;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典:again_test_status")
	private String status;

	/**
	 * 检测表编号
	 */
	@ApiModelProperty("检测表编号")
	private String formCode;

	/**
	 * 检测表类型A/B/C
	 */
	@ApiModelProperty("检测表类型:A/B/C")
	private String formType;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * 轮次
	 */
	@ApiModelProperty("轮次")
	private Integer rounds;

	/**
	 * 充电桩ID
	 */
	@ApiModelProperty("充电桩ID")
	private Long chargingPileId;

	/**
	 * 初检开始时间
	 */
	@ApiModelProperty("初检开始时间：格式yyyy-MM-dd")
	private String startDate;

	/**
	 * 初检结束时间
	 */
	@ApiModelProperty("初检结束时间：格式yyyy-MM-dd")
	private String endDate;

	/**
	 * 复检开始时间
	 */
	@ApiModelProperty("复检开始时间：格式yyyy-MM-dd")
	private String startReviewDate;

	/**
	 * 复检结束时间
	 */
	@ApiModelProperty("复检结束时间：格式yyyy-MM-dd")
	private String endReviewDate;

}
