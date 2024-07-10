package com.rosenzest.electric.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceVo {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 检测单位
	 */
	@ApiModelProperty("检测单位")
	private Long detectId;

	/**
	 * 仪器编号
	 */
	@ApiModelProperty("仪器编号")
	private String deviceId;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型,见数据字典 detect_device_type ")
	private String type;

	/**
	 * 仪器名称
	 */
	@ApiModelProperty("仪器名称")
	private String name;

	/**
	 * 校准日期
	 */
	@ApiModelProperty("校准日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date calibrationDate;

	/**
	 * 是否过期1过期，0未过期
	 */
	@ApiModelProperty("是否过期1过期，0未过期")
	private String isExpired;
}
