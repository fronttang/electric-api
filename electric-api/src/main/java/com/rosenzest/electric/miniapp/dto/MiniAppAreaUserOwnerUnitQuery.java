package com.rosenzest.electric.miniapp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MiniAppAreaUserOwnerUnitQuery extends MiniAppOwnerUnitQuery {

	/** 区县 */
	private String userDistrict;

	/** 街道 */
	private String userStreet;

	/** 社区 */
	private String userCommunity;

	/** 村 */
	private String userHamlet;
}
