package com.rosenzest.electric.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OwnerUnitAreaDto {
	
	private Long id;

    /**
     * 单元ID
     */
	@ApiModelProperty("单元ID")
    private Long unitId;

    /**
     * 名称
     */
	@ApiModelProperty("名称")
    private String name;

    /**
     * 楼层
     */
	@ApiModelProperty("楼层")
    private Integer floor;

    /**
     * 类型1公共区域，2户
     */
	@ApiModelProperty("类型1公共区域，2户")
    private String type;
}
