package com.rosenzest.electric.station.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 充电场
 */
@Data
public class ChargingStationVo {

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
	@ApiModelProperty("名称")
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
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 轮次
	 */
	private Integer rounds;

	/**
	 * 项目名称
	 */
	// private String projectName;

	/**
	 * 区县
	 */
	@ApiModelProperty("区县")
	private String district;

	/**
	 * 街道
	 */
	@ApiModelProperty("街道")
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

}
