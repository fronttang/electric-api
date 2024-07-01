package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 检测设备
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DetectDevice extends BaseEntity<DetectDevice> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 检测单位
     */
    private Long detectId;

    /**
     * 检测单位名称
     */
    private String detectName;

    /**
     * 仪器编号
     */
    private String deviceId;

    /**
     * 类型
     */
    private String type;

    /**
     * 仪器名称
     */
    private String name;

    /**
     * 校准日期
     */
    private Date calibrationDate;

    /**
     * 是否过期1过期，0未过期
     */
    private String isExpired;


}
