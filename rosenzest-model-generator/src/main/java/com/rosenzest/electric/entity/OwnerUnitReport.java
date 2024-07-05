package com.rosenzest.electric.entity;

import java.util.Date;
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
public class OwnerUnitReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 业主单元ID
     */
    private Long unitId;

    /**
     * 楼栋ID
     */
    private Long buildingId;

    /**
     * 是否开启隐患通知单1开启
     */
    private String isDangerNotice;

    /**
     * 是否完成入户率1是0否
     */
    private String isHouseholdRate;

    /**
     * 是否无法检测1是0否
     */
    private String isTest;

    /**
     * 无法检测原因
     */
    private String isTestReason;

    /**
     * 初检编号
     */
    private String initialTestNo;

    /**
     * 复检编号
     */
    private String againTestNo;

    /**
     * 复检时间
     */
    private Date againTestData;

    /**
     * 初检状态
     */
    private String initialTestStatus;

    /**
     * 复检状态
     */
    private String againTestStatus;

    /**
     * 检测员
     */
    private String inspector;

    /**
     * 检测员ID
     */
    private Long inspectorId;


}
