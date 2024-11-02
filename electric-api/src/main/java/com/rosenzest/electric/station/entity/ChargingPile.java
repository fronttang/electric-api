package com.rosenzest.electric.station.entity;

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
 * @since 2024-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChargingPile extends BaseEntity<ChargingPile> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.INPUT)
	private Long id;

	/**
	 * 充电站ID
	 */
	private Long unitId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 自编号
	 */
	private String code;

	/**
	 * 轮次
	 */
	private Integer rounds;

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
	 * 出厂编号
	 */
	private String serialNumber;

	/**
	 * 生产日期
	 */
	private String productionDate;

	/**
	 * 原始充电桩ID
	 */
	private Long originalId;

}
