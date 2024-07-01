package com.rosenzest.electric.entity;

import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户设备
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDevice extends BaseEntity<UserDevice> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 设备ID
     */
    private Long deviceId;


}
