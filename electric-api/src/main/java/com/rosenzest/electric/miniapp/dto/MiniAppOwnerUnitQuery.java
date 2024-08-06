package com.rosenzest.electric.miniapp.dto;

import com.rosenzest.electric.dto.PageQuery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MiniAppOwnerUnitQuery extends PageQuery {

	private Long userId;

	private Long projectId;

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

	/**
	 * 名称/地址
	 */
	@ApiModelProperty("搜索关键字：名称/地址")
	private String keyword;

	/**
	 * 高风险类型
	 */
	@ApiModelProperty("场所类型 1出租屋 2三小场所 3住宅小区 4工业企业 5公共场所 6大型综合体")
	private String highRiskType;

	/**
	 * 初检状态
	 */
	@ApiModelProperty("初检状态 0检测中 1无法检测 2完成")
	private String status;

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
}
