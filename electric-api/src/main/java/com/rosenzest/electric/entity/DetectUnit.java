package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 检测单位表
 * </p>
 *
 * @author fronttang
 * @since 2024-06-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DetectUnit extends BaseEntity<DetectUnit> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 受控编号
     */
    private String controlledNumber;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * logo
     */
    private String logo;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 资质
     */
    private String qualification;


}
