package com.rosenzest.electric.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetectDataDangerVo {
	/**
     * ID
     */
    private Long id;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 内容ID
     */
    private Long dataId;

    /**
     * 隐患等级
     */
    private String level;

    /**
     * 隐患描述
     */
    private String description;

    /**
     * 整改建议
     */
    private String suggestions;

    /**
     * 累计方式
     */
    private String accMethod;

    /**
     * 扣分数
     */
    private Double score;
}
