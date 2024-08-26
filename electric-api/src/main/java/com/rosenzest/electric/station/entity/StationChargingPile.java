package com.rosenzest.electric.station.entity;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 充电站充电桩
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StationChargingPile extends BaseEntity<StationChargingPile> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 充电站ID
	 */
	private Long unitId;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 功率
	 */
	private String power;

	/**
	 * 数量
	 */
	private Long quantity;

}
