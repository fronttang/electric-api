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
 * @since 2024-07-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitArea extends BaseEntity {

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
     * 楼栋ID
     */
    private Long buildingId;

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

    /**
     * 备注
     */
    private String remark;


}
