package com.rosenzest.electric.miniapp.vo;

import com.rosenzest.electric.entity.OwnerUnitDanger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AreaUserDangerVo extends OwnerUnitDanger {

	private static final long serialVersionUID = 1L;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 区县
	 */
	private String districtName;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 街道
	 */
	private String streetName;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 社区
	 */
	private String communityName;

	/**
	 * 村
	 */
	private String hamlet;

	/**
	 * 村
	 */
	private String hamletName;

}
