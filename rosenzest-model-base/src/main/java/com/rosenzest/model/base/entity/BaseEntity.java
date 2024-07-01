package com.rosenzest.model.base.entity;

import java.io.Serializable;

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
public class BaseEntity<T extends BaseModel<?>> extends BaseTimeEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 顺序号
     */
//    @TableId(value = "SEQ_NO", type = IdType.ASSIGN_ID)
//    private String seqNo;

}
