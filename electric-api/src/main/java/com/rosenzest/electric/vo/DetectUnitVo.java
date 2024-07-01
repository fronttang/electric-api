package com.rosenzest.electric.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 检测单位返回
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DetectUnitVo {
	
	/**
	 * 检测单位ID
	 */
	@ApiModelProperty("检测单位ID")
	private Long id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 简称
	 */
	@ApiModelProperty("简称")
	private String shortName;

	/**
	 * 受控编号
	 */
	@ApiModelProperty("受控编号")
	private String controlledNumber;

	/**
	 * 电话
	 */
	@ApiModelProperty("电话")
	private String phone;

	/**
	 * 地址
	 */
	@ApiModelProperty("地址")
	private String address;

	/**
	 * 联系人
	 */
	@ApiModelProperty("联系人")
	private String contact;

	/**
	 * 联系电话
	 */
	@ApiModelProperty("联系电话")
	private String contactPhone;

	/**
	 * logo
	 */
	@ApiModelProperty("logo")
	private String logo;

	/**
	 * 营业执照
	 */
	@ApiModelProperty("营业执照")
	private String businessLicense;

	/**
	 * 资质
	 */
	@ApiModelProperty("资质")
	private String qualification;
}
