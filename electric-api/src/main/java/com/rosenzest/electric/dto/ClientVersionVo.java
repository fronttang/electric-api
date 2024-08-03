package com.rosenzest.electric.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClientVersionVo {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 客户端
	 */
	@ApiModelProperty("客户端[Android,IOS,HarmonyOS]")
	private String client;

	/**
	 * 版本名称
	 */
	@ApiModelProperty("版本名称")
	private String name;

	/**
	 * 版本代码
	 */
	@ApiModelProperty("版本代码")
	private String version;

	/**
	 * 强制更新1是0否
	 */
	@ApiModelProperty("强制更新1是0否")
	private String forced;

	/**
	 * 更新内容
	 */
	@ApiModelProperty("更新内容")
	private String content;

	/**
	 * 下载地址
	 */
	@ApiModelProperty("下载地址")
	private String downloadUrl;

}
