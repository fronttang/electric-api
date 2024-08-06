package com.rosenzest.electric.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OwnerUnitQrcodeVo {

	/**
	 * 业主单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long id;

	/**
	 * 业主单元名称
	 */
	@ApiModelProperty("业主单元名称")
	private String name;

	/**
	 * 检测地址
	 */
	@ApiModelProperty("检测地址")
	private String address;

	/**
	 * 检测日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty("检测日期")
	private Date detectData;

	/**
	 * 二维码图片base64
	 */
	@ApiModelProperty("二维码图片地址")
	private String qrcode;
}
