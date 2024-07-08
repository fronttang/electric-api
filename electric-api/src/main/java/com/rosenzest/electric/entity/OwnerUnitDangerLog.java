package com.rosenzest.electric.entity;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fronttang
 * @since 2024-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitDangerLog extends BaseEntity<OwnerUnitDangerLog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志ID
	 */
	private Long id;

	/**
	 * 业主单元隐患ID
	 */
	private Long dangerId;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 操作员ID
	 */
	private Long operatorId;

	/**
	 * 操作类型1初检2整改3复检不通过4无法检测5复检通过
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
