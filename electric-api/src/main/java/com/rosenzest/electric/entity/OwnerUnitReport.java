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
 * @since 2024-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitReport extends BaseEntity<OwnerUnitReport> {

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
	 * 报告类型1初检,2复检
	 */
	private String type;

	/**
	 * 报告编号
	 */
	private String code;

	/**
	 * 检测时间
	 */
	private Date detectData;

	/**
	 * 检测状态
	 */
	private String detectStatus;

	/**
	 * 检测员
	 */
	private String inspector;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;

	/**
	 * 报告状态
	 */
	private String status;

}
