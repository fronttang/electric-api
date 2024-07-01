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
 * @since 2024-06-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitArea extends BaseEntity<OwnerUnitArea> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 单元ID
     */
    private Long unitId;

    /**
     * 名称
     */
    private String name;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 类型1公共区域，2户
     */
    private String type;


}
