package com.rosenzest.electric.vo;

import java.util.Date;

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
	private Long id;

	/**
	 * 楼栋编码
	 */
	private String code;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 业主单元类型
	 */
	private String type;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/**
	 * 检测单位名称
	 */
	private String detectName;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 委托单位
	 */
	private String entrust;

	/**
	 * 检测地址
	 */
	private String address;

	/**
	 * 联系人/负责人/业主
	 */
	private String contact;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 建筑面积
	 */
	private String acreage;

	/**
	 * 建筑层数
	 */
	private Integer layers;

	/**
	 * 户数
	 */
	private Integer doorNumber;

	/**
	 * 建筑使用性质
	 */
	private String nature;

	/**
	 * 检测开始时间
	 */
	private Date testStartDate;

	/**
	 * 检测结束时间
	 */
	private Date testEndDate;

	/**
	 * 检测内容
	 */
	private String testContent;

	/**
	 * 初检编号
	 */
	private String initialTestNo;

	/**
	 * 复检编号
	 */
	private String againTestNo;

	/**
	 * 复检时间
	 */
	private Date againTestData;

	/**
	 * 全景图
	 */
	private String panoramaPic;

	/**
	 * 区县
	 */
	private String district;

	/**
	 * 街道
	 */
	private String street;

	/**
	 * 社区
	 */
	private String community;

	/**
	 * 村
	 */
	private String hamlet;

}
