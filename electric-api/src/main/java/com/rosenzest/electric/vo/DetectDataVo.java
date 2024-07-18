package com.rosenzest.electric.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetectDataVo {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 模板ID
	 */
	@ApiModelProperty("模板ID")
	private Long templateId;

	/**
	 * 检测表标题
	 */
	@ApiModelProperty("检测表标题")
	private Long detectTitle;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 业主单元类型
	 */
	@ApiModelProperty("业主单元类型:")
	private String unitType;

	/**
	 * 模块类型（一级，二级）
	 */
	@ApiModelProperty("模块类型（一级，二级）")
	private String moduleType;

	/**
	 * 检测板块
	 */
	@ApiModelProperty("检测板块")
	private String detectModule;

	/**
	 * 上级模块ID
	 */
	@ApiModelProperty("上级模块ID")
	private Long parentId;

	/**
	 * 一级编号
	 */
	@ApiModelProperty("一级编号")
	private String firstCode;

	/**
	 * 一级编号内容
	 */
	@ApiModelProperty("一级编号内容")
	private String firstContent;

	/**
	 * 二级编号
	 */
	@ApiModelProperty("二级编号")
	private String secondaryCode;

	/**
	 * 二级编号内容
	 */
	@ApiModelProperty("二级编号内容")
	private String secondaryContent;

	/**
	 * 权重
	 */
	@ApiModelProperty("权重")
	private Double weights;

	/**
	 * 最高扣分数
	 */
	@ApiModelProperty("最高扣分数")
	private Double maxScore;

	/**
	 * 1展示，2计分
	 */
	@ApiModelProperty("1展示，2计分")
	private String view;

	/**
	 * 展示模块上级
	 */
	@ApiModelProperty("展示模块上级")
	private Long viewParentId;

	/**
	 * 二级模块（高风险）
	 */
	private List<DetectDataVo> subDatas;

	/**
	 * 隐患数
	 */
	@ApiModelProperty("隐患数")
	private Integer danger;

	/**
	 * 隐患数据列表
	 */
	@ApiModelProperty("隐患数据列表")
	private List<DetectDataDangerVo> dangers;
}
