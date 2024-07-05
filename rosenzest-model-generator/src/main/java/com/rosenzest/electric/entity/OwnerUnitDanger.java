package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2024-07-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitDanger extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
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
     * 单元区域ID
     */
    private Long unitAreaId;

    /**
     * 隐患ID
     */
    private Long dangerId;

    /**
     * 检测表ID
     */
    private Long formId;

    /**
     * 检测表编号
     */
    private String formCode;

    /**
     * 检测表类型A/B/C
     */
    private String type;

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
     * 位置
     */
    private String location;

    /**
     * 隐患图片
     */
    private String dangerPic;

    /**
     * 整改图
     */
    private String rectificationPic;

    /**
     * 检测图
     */
    private String detectPic;

    /**
     * 整改未通过原因
     */
    private String reason;

    /**
     * 状态
     */
    private String status;

    /**
     * 检测员
     */
    private String inspector;

    /**
     * 检测员ID
     */
    private Long inspectorId;

    /**
     * 初检时间
     */
    private Date initialTime;

    /**
     * B类表数据
     */
    private String formb;


}
