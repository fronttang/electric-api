package com.rosenzest.electric.station.entity;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代表照片
 * </p>
 *
 * @author fronttang
 * @since 2024-09-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitStationPeprePic extends BaseEntity<OwnerUnitStationPeprePic> {

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
	 * 编号
	 */
	private String code;

	/**
	 * 轮次
	 */
	private Integer rounds;

	/**
	 * 图片
	 */
	private String picture;
}
