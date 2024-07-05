package com.rosenzest.electric.vo;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class OwnerUnitDangerVo {

	/**
	 * ID
	 */
	@ApiModelProperty("隐患数据ID")
	private Long id;

	/**
	 * 单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 单元区域ID
	 */
	@ApiModelProperty("公共区域/户ID")
	private Long unitAreaId;

	/**
	 * 业主单元名称
	 */
	@ApiModelProperty("业主单元名称")
	private String unitName;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 楼栋名称
	 */
	@ApiModelProperty("楼栋名称")
	private String buildingName;

	/**
	 * 公共区域/户名称
	 */
	@ApiModelProperty("公共区域/户名称")
	private String unitAreaName;

	/**
	 * 隐患ID
	 */
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	@ApiModelProperty(" 检测表类型:A/B/C")
	private String type;

	/**
	 * 隐患等级
	 */
	@ApiModelProperty("隐患等级")
	private String level;

	/**
	 * 隐患描述
	 */
	@ApiModelProperty("隐患描述")
	private String description;

	/**
	 * 整改建议
	 */
	@ApiModelProperty("整改建议")
	private String suggestions;

	/**
	 * 位置
	 */
	@ApiModelProperty("位置")
	private String location;

	/**
	 * 隐患图片
	 */
	@ApiModelProperty("隐患图片")
	private String dangerPic;

	/**
	 * 整改图
	 */
	@ApiModelProperty("整改图")
	private String rectificationPic;

	/**
	 * 检测图
	 */
	@ApiModelProperty("检测图")
	private String detectPic;

	/**
	 * 整改未通过原因
	 */
	@ApiModelProperty("整改未通过原因")
	private String reason;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,同字典复检状态：again_test_status")
	private String status;

	/**
	 * 检测员
	 */
	@ApiModelProperty("检测员")
	private String inspector;

	/**
	 * 检测员ID
	 */
	@ApiModelProperty("检测员ID")
	private Long inspectorId;

	/**
	 * 初检时间
	 */
	@ApiModelProperty("初检时间")
	private Date initialTime;

}
