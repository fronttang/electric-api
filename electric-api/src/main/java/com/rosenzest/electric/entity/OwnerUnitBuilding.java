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
 * @since 2024-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitBuilding extends BaseEntity<OwnerUnitBuilding> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 楼栋名称
	 */
	private String name;

	/**
	 * 类型配电房/宿舍楼/其他
	 */
	private String type;

	/**
	 * 宿舍楼户数
	 */
	private Long doors;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 复检状态
	 */
	private String reviewStatus;

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

}
