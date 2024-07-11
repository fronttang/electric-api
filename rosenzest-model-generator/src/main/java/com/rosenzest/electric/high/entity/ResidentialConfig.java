package com.rosenzest.electric.high.entity;

import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 住宅小区
 * </p>
 *
 * @author fronttang
 * @since 2024-07-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResidentialConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 业主单元ID
     */
    private Long unitId;

    /**
     * 4KgABC手提干粉灭火器数量
     */
    private Integer fireExtinguisherQuantity;

    /**
     * 4KgABC手提干粉灭火器是否完好1是0否
     */
    private String fireExtinguisherStatus;

    /**
     * 消防给水及消火栓系统有无1有0无
     */
    private String hydrantSystemHas;

    /**
     * 消防给水及消火栓系统是否完好1是0否
     */
    private String hydrantSystemStatus;

    /**
     * 火灾自动报警系统有无1有0无
     */
    private String alarmSystemHas;

    /**
     * 火灾自动报警系统是否完好1是0否
     */
    private String alarmSystemStatus;

    /**
     * 自动灭火系统有无1有0无
     */
    private String automaticFireSystemHas;

    /**
     * 自动灭火系统是否完好1是0否
     */
    private String automaticFireSystemStatus;

    /**
     * 防/排烟系统有无1有0无
     */
    private String exhaustSystemHas;

    /**
     * 防/排烟系统是否完好1是0否
     */
    private String exhaustSystemStatus;

    /**
     * 总平面布局、灭火救援设施是否符合要求1是0否
     */
    private String rescueFacilities;

    /**
     * 耐火等级、防火分区、平面布置是否符合要求1是0否
     */
    private String fireResistantLevel;

    /**
     * 防火构造及室内装修是否符合规范要求1是0否
     */
    private String fireproofStructure;

    /**
     * 安全疏散设施及管理是否符合要求1是0否
     */
    private String safeEvacuation;

    /**
     * 是否设置消防控制室1物业所有0否
     */
    private String fireControlRoom;

    /**
     * 电气线路敷设、电气设备使用是否符合要求1是0否
     */
    private String electricalEquipment;

    /**
     * 门头照
     */
    private String doorPic;


}
