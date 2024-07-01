package com.rosenzest.model.base.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author fronttang
 * @date 2021/06/30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CoreBaseEntity<T extends BaseModel<?>> extends BaseModel<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2942758695203022133L;

    /**
     * 顺序号
     */
    @TableId(value = "SEQ_NO", type = IdType.ASSIGN_ID)
    private String seqNo;

    /**
     * 创建柜员
     */
    // @TableField("CRE_TLR")
    // private String creTlr;

    /**
     * 创建日期
     */
    @TableField(value = "CRE_DTE", fill = FieldFill.INSERT)
    private Date creDte;

    /**
     * 创建机构
     */
    // @TableField("CRE_BR")
    // private String creBr;

    /**
     * 创建分行
     */
    // @TableField("CRE_BK")
    // private String creBk;

    /**
     * 更新柜员
     */
    // @TableField("UPD_TLR")
    // private String updTlr;

    /**
     * 更新日期
     */
    @TableField(value = "UPD_DTE", fill = FieldFill.UPDATE, update = "now()")
    private Date updDte;

    /**
     * 更新机构
     */
    // @TableField("UPD_BR")
    // private String updBr;

    /**
     * 更新分行
     */
    // @TableField("UPD_BK")
    // private String updBk;
}
