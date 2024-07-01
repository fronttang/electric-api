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
public class DictDataVo {
	
	/**
     * 字典编码
     */
	@ApiModelProperty("字典编码")
    private Long dictCode;

    /**
     * 字典排序
     */
	@ApiModelProperty("字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     */
	@ApiModelProperty("字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
	@ApiModelProperty("字典键值")
    private String dictValue;

    /**
     * 字典类型
     */
	@ApiModelProperty("字典类型")
    private String dictType;
}
