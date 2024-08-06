package com.rosenzest.electric.miniapp.vo;

import lombok.Data;

@Data
public class AreaUserInfoVo {

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 街区名
	 */
	private String areaName;

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
