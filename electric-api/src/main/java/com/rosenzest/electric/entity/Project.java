package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Project extends BaseEntity<Project> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目类型
     */
    private String type;

    /**
     * 检测单位
     */
    private Long detectId;

    /**
     * 检测单位名称
     */
    private String detectName;

    /**
     * 入户率要求
     */
    private Integer householdRate;

    /**
     * 模板ID
     */
    private Long templateId;

}
