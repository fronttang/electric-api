package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 检测表内容隐患
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IntuitiveDetectDanger extends BaseEntity<IntuitiveDetectDanger> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
