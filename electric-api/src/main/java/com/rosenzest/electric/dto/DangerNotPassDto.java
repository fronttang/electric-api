package com.rosenzest.electric.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DangerNotPassDto {

	/**
	 * 隐患ID
	 */
	@NotNull(message = "隐患ID不能为空")
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	/**
	 * 无法检测
	 */
	@ApiModelProperty("无法检测时传1,只是不通过是传0,默认0")
	private String unableToDetect = "0";

	/**
	 * 不通过原因
	 */
	@ApiModelProperty("不通过原因")
	private String reason;

	/**
	 * 照片
	 */
	@ApiModelProperty("佐证照片")
	private String pic;

	/**
	 * 整改图
	 */
	@ApiModelProperty("整改图")
	private String rectificationPic;
}
