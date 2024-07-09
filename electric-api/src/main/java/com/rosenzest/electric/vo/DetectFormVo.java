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
public class DetectFormVo {

	/**
	 * 检测表ID
	 */
	@ApiModelProperty("检测表ID")
	private Long id;

	/**
	 * 检测表名称
	 */
	@ApiModelProperty("检测表名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型: A类 B类 C类")
	private String type;

	/**
	 * 编号
	 */
	@ApiModelProperty("编号")
	private String code;

	/**
	 * 隐患数
	 */
	@ApiModelProperty("隐患数")
	private Integer dangers;

	/**
	 * 检测表内容
	 */
	private List<DetectDataVo> datas;
}
