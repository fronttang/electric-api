package com.rosenzest.electric.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 业主单元
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Data
public class OwnerUnitVo {

	/**
	 * ID
	 */
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 楼栋编码
	 */
	@ApiModelProperty("楼栋编码")
	private String code;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 业主单元类型
	 */
	@ApiModelProperty("业主单元类型,见字典:project_type")
	private String type;

	/**
	 * 检测单位ID
	 */
	@ApiModelProperty("检测单位ID")
	private Long detectId;

	/**
	 * 检测单位名称
	 */
	@ApiModelProperty("检测单位名称")
	private String detectName;

	/**
	 * 项目ID
	 */
	@ApiModelProperty("项目ID")
	private Long projectId;

	/**
	 * 项目名称
	 */
	@ApiModelProperty("项目名称")
	private String projectName;

	/**
	 * 委托单位
	 */
	@ApiModelProperty("委托单位")
	private String entrust;

	/**
	 * 检测地址
	 */
	@ApiModelProperty("检测地址")
	private String address;

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
	 * 建筑面积
	 */
	@ApiModelProperty("建筑面积")
	private String acreage;

	/**
	 * 建筑层数
	 */
	@ApiModelProperty("建筑层数")
	private Integer layers;

	/**
	 * 户数
	 */
	@ApiModelProperty("户数")
	private Integer doorNumber;

	/**
	 * 建筑使用性质
	 */
	@ApiModelProperty("建筑使用性质")
	private String nature;

	/**
	 * 检测开始时间
	 */
	@ApiModelProperty("检测开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date testStartDate;

	/**
	 * 检测结束时间
	 */
	@ApiModelProperty("检测结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date testEndDate;

	/**
	 * 检测内容
	 */
	@ApiModelProperty("检测内容")
	private String testContent;

	/**
	 * 初检编号
	 */
	@ApiModelProperty("初检编号")
	private String initialTestNo;

	/**
	 * 复检编号
	 */
	@ApiModelProperty("复检编号")
	private String againTestNo;

	/**
	 * 复检时间
	 */
	@ApiModelProperty("复检时间")
	private Date againTestData;

	/**
	 * 全景图
	 */
	@ApiModelProperty("整体照片")
	private String panoramaPic;

	/**
	 * 门牌号照片
	 */
	@ApiModelProperty("门牌号照片")
	private String houseNoPic;

	/**
	 * 现场检测图
	 */
	@ApiModelProperty("现场检测图")
	private String inspectionPic;

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
	 * 社区
	 */
	@ApiModelProperty("社区")
	private String community;

	/**
	 * 村
	 */
	@ApiModelProperty("村")
	private String hamlet;

}
