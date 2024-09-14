package com.rosenzest.electric.station.vo;

import lombok.Data;

/**
 * <p>
 * 代表照片
 * </p>
 *
 * @author fronttang
 * @since 2024-09-14
 */
@Data
public class OwnerUnitStationPeprePicVo {

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
