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
public class DictTypeVo {

	/**
     * 字典主键
     */
	@ApiModelProperty("字典主键")
    private Long dictId;

    /**
     * 字典名称
     */
	@ApiModelProperty("字典名称")
    private String dictName;

    /**
     * 字典类型
     */
	@ApiModelProperty("字典类型")
    private String dictType;
    
	@ApiModelProperty("字典数据")
    private List<DictDataVo> dictData;
}
