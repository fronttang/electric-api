package com.rosenzest.electric.design.entity;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 电力设计项目
 * </p>
 *
 * @author fronttang
 * @since 2024-11-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ElectricityProject extends BaseEntity<ElectricityProject> {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 项目名称
	 */
	private String name;

}
