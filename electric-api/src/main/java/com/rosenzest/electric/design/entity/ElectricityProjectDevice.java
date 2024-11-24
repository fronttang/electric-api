package com.rosenzest.electric.design.entity;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 电力设计项目设备
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricityProjectDevice extends BaseEntity<ElectricityProject> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * 设备位置
	 */
	private String deviceLocation;

	/**
	 * 设备类型
	 */
	private String deviceType;

}
