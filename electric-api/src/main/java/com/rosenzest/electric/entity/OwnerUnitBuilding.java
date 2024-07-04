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
 * @since 2024-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitBuilding extends BaseEntity<OwnerUnitBuilding> {

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
	 * 楼栋名称
	 */
	private String name;

	/**
	 * 类型配电房/宿舍楼
	 */
	private String type;

	/**
	 * 宿舍楼户数
	 */
	private Long doors;

}
