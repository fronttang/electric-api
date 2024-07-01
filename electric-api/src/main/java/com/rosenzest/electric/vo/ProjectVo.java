package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVo {

    /**
     * ID
     */
	@ApiModelProperty("项目ID")
    private Long id;

    /**
     * 项目名称
     */
	@ApiModelProperty("项目名称")
    private String name;

    /**
     * 项目类型
     */
	@ApiModelProperty("项目类型")
    private String type;

    /**
     * 检测单位
     */
	@ApiModelProperty("检测单位")
    private Long detectId;

    /**
     * 入户率要求
     */
	@ApiModelProperty("入户率要求")
    private Integer householdRate;

    /**
     * 模板ID
     */
	@ApiModelProperty("模板ID")
    private Long templateId;

}
