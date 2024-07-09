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
public class OwnerUnitReportLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 报告ID
     */
    private Long reportId;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 操作员ID
     */
    private Long operatorId;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作员角色
     */
    private String operatorRole;

    /**
     * 操作图片
     */
    private String operationPic;

    /**
     * 内容
     */
    private String content;

    /**
     * 备注,驳回原因等
     */
    private String remark;


}
