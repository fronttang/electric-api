package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DetectTemplateB extends BaseEntity<DetectTemplateB> {

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
     * B类表ID
     */
    private String bid;

    /**
     * 类型1检测员端显示2初检报告显示
     */
    private String type;


}
