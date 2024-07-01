package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class UnitAreaDangerDto {

	/**
     * ID
     */
	@ApiModelProperty("隐患数据ID")
    private Long id;

    /**
     * 单元ID
     */
    @NotNull(message = "单元/楼栋ID不能为空")
    @ApiModelProperty("单元/楼栋ID")
    private Long unitId;

    /**
     * 单元区域ID
     */
    @NotNull(message = "公共区域/户ID不能为空")
    @ApiModelProperty("公共区域/户ID")
    private Long unitAreaId;

    /**
     * 隐患ID
     */
    @ApiModelProperty("隐患ID")
    private Long dangerId;

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
    @NotNull(message = "隐患位置不能为空")
    @ApiModelProperty("位置")
    private String location;

    /**
     * 隐患图片
     */
    @ApiModelProperty("隐患图片")
    private String dangerPic;
}
