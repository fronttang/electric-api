package com.rosenzest.electric.station.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 充电场
 */
@Data
public class ChargingStationDto {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 楼栋编码
	 */
	private String code;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotBlank(message = "名称不能为空")
	private String name;

	/**
	 * 业主单元类型,同项目类型
	 */
	// private String type;

	/**
	 * 检测单位ID
	 */
	// private Long detectId;

	/**
	 * 检测单位名称
	 */
	// private String detectName;

	/**
	 * 项目ID
	 */
	@NotNull(message = "项目ID不能为空")
	@ApiModelProperty(value = "项目ID", required = true)
	private Long projectId;

	/**
	 * 项目名称
	 */
	// private String projectName;

	/**
	 * 区县
	 */
	@ApiModelProperty(value = "区县", required = true)
	@NotBlank(message = "区不能为空")
	private String district;

	/**
	 * 街道
	 */
	@ApiModelProperty(value = "街道", required = true)
	@NotBlank(message = "街道不能为空")
	private String street;

	/**
	 * 充电站类型 集中式/分散式
	 */
	@ApiModelProperty("充电站类型，见数据字典：charging_station_type")
	private String stationType;

	/**
	 * 检测模块
	 */
	@ApiModelProperty("检测模块，见数据字典：detect_module")
	private String detectModule;

	/**
	 * 检测地址
	 */
	@ApiModelProperty("检测地址")
	private String address;

	/**
	 * 运营单位
	 */
	@ApiModelProperty("运营单位")
	private String operating;

	/**
	 * 联系人/负责人/业主
	 */
	@ApiModelProperty("联系人")
	private String contact;

	/**
	 * 联系电话
	 */
	@ApiModelProperty("联系电话")
	private String phone;

	/**
	 * 委托单位
	 */
	@ApiModelProperty("委托单位")
	private String entrust;

	/**
	 * 物业类型
	 */
	@ApiModelProperty("物业类型，见字典：property_type")
	private String propertyType;

	/**
	 * 物业类型名称(物业类型为其他时输入)
	 */
	@ApiModelProperty("物业类型名称(物业类型为其他时输入)")
	private String propertyName;

	/**
	 * 温度
	 */
	@ApiModelProperty("温度")
	private String temperature;

	/**
	 * 湿度
	 */
	@ApiModelProperty("湿度")
	private String humidity;

	/**
	 * 检测日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty("检测日期,格式 yyyy-MM-dd")
	private Date testStartDate;

	/**
	 * 全景图
	 */
	@ApiModelProperty("全景图")
	private String panoramaPic;

	/**
	 * 点位图
	 */
	@ApiModelProperty("点位图")
	private String stationPic;

	/**
	 * 投入运营时间
	 */
	@ApiModelProperty("投入运营时间")
	private String operatingDate;

	/**
	 * 场站服务车辆
	 */
	@ApiModelProperty("场站服务车辆")
	private String vehicles;

	/**
	 * 有无休息室1有0无
	 */
	@ApiModelProperty("有无休息室1有0无")
	private String lounge;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;

	private String createBy;

}
