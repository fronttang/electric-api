package com.rosenzest.electric.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业主单元
 * </p>
 *
 * @author fronttang
 * @since 2024-07-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnit extends BaseEntity<OwnerUnit> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
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
	 * 业主单元类型,同项目类型
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
	 * 区域
	 */
	private Long area;

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

	/**
	 * 委托单位
	 */
	private String entrust;

	/**
	 * 管理员
	 */
	private Long manager;

	/**
	 * 网格员
	 */
	private Long gridman;

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
	 * 检测起止日期
	 */
	private String testDate;

	/**
	 * 检测内容
	 */
	private String testContent;

	/**
	 * 户数
	 */
	private Integer doorNumber;

	/**
	 * 楼栋长
	 */
	private String buildman;

	/**
	 * 单位类型
	 */
	private String unitType;

	/**
	 * 负责人
	 */
	private String incharge;

	/**
	 * 场所类型
	 */
	private String venueType;

	/**
	 * 经营范围
	 */
	private String businessScope;

	/**
	 * 消防安全负责人
	 */
	private String safetyIncharge;

	/**
	 * 消防安全负责人电话
	 */
	private String safetyInchargePhone;

	/**
	 * 消防安全管理人
	 */
	private String safetyManager;

	/**
	 * 消防安全管理人电话
	 */
	private String safetyManagerPhone;

	/**
	 * 高风险类型
	 */
	private String highRiskType;

	/**
	 * 员工人数
	 */
	private Integer staffs;

	/**
	 * 有无证照
	 */
	private String licence;

	/**
	 * 消防安全重点单位
	 */
	private String safetyKeyUnit;

	/**
	 * 充电站类型
	 */
	private String stationType;

	/**
	 * 轮次
	 */
	private Integer rounds;

	/**
	 * 检测模块
	 */
	private String detectModule;

	/**
	 * 运营单位
	 */
	private String operating;

	/**
	 * 物业类型
	 */
	private String propertyType;

	/**
	 * 物业类型名称(物业类型为其他时输入)
	 */
	private String propertyName;

	/**
	 * 全景图
	 */
	private String panoramaPic;

	/**
	 * 点位图
	 */
	private String stationPic;

	/**
	 * 门牌号照片
	 */
	private String houseNoPic;

	/**
	 * 现场检测图
	 */
	private String inspectionPic;

	/**
	 * 温度
	 */
	private String temperature;

	/**
	 * 湿度
	 */
	private String humidity;

}
