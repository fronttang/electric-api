package com.rosenzest.electric.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.rosenzest.base.enums.EnumUtils;
import com.rosenzest.electric.enums.ProjectType;
import com.rosenzest.electric.formb.FormbDangerHandlerFactory;
import com.rosenzest.electric.formb.handler.IFormbDangerHandler;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class OwnerUnitDangerVo implements IOwnerUnitDanger {

	/**
	 * ID
	 */
	@ApiModelProperty("隐患数据ID")
	private Long id;

	/**
	 * 单元ID
	 */
	@ApiModelProperty("业主单元ID")
	private Long unitId;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 项目类型
	 */
	private String projectType;

	/**
	 * 业主单元名称
	 */
	@ApiModelProperty("业主单元名称")
	private String unitName;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 检测单位名称
	 */
	@ApiModelProperty("检测单位名称")
	private String detectName;

	/**
	 * 单元区域ID
	 */
	@ApiModelProperty("公共区域/户ID")
	private Long unitAreaId;

	/**
	 * 类型,见字典:owner_unit_area_type
	 */
	@ApiModelProperty("类型,公共区域/户，见字典:owner_unit_area_type")
	private String unitAreatype;

	/**
	 * 公共区域/户名称
	 */
	@ApiModelProperty("公共区域/户名称")
	private String unitAreaName;

	/**
	 * 楼栋ID
	 */
	@ApiModelProperty("楼栋ID")
	private Long buildingId;

	/**
	 * 楼栋名称
	 */
	@ApiModelProperty("楼栋名称")
	private String buildingName;

	/**
	 * 充电桩ID
	 */
	@ApiModelProperty("充电桩ID")
	private List<Long> chargingPileId;

	/**
	 * 充电桩名称
	 */
	private String chargingPileName;

	/**
	 * 隐患ID
	 */
	@ApiModelProperty("隐患ID")
	private Long dangerId;

	/**
	 * 检测表类型:A/B/C
	 */
	@ApiModelProperty("检测表类型:A/B/C")
	private String formType;

	/**
	 * 检测表ID
	 */
	@ApiModelProperty("检测表ID,B类表不传ID,只传编号和类型")
	private Long formId;

	/**
	 * 检测表编号
	 */
	@ApiModelProperty("检测表编号")
	private String formCode;

	/**
	 * B类表数据
	 */
	@ApiModelProperty("B类表数据")
	private JSONObject formb;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * 隐患等级
	 */
	@ApiModelProperty("隐患等级")
	private String level;

	/**
	 * 隐患描述
	 */
	@ApiModelProperty("隐患描述")
	private String description;

	/**
	 * 整改建议
	 */
	@ApiModelProperty("整改建议")
	private String suggestions;

	/**
	 * 位置
	 */
	@ApiModelProperty("位置")
	private String location;

	/**
	 * 隐患图片
	 */
	@ApiModelProperty("隐患图片")
	private String dangerPic;

	/**
	 * 整改图
	 */
	@ApiModelProperty("整改图")
	private String rectificationPic;

	/**
	 * 检测图
	 */
	@ApiModelProperty("检测图")
	private String detectPic;

	/**
	 * 整改未通过原因
	 */
	@ApiModelProperty("整改未通过原因")
	private String reason;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态,见字典复检状态：again_test_status")
	private String status;

	/**
	 * 检测员
	 */
	@ApiModelProperty("检测员")
	private String inspector;

	/**
	 * 检测员ID
	 */
	@ApiModelProperty("检测员ID")
	private Long inspectorId;

	/**
	 * 初检时间
	 */
	@ApiModelProperty("初检时间")
	private Date initialTime;

	/**
	 * 整改员
	 */
	@ApiModelProperty("整改员")
	private String rectification;

	/**
	 * 整改时间
	 */
	@ApiModelProperty("整改时间")
	private Date rectificationDate;

	/**
	 * 复检员
	 */
	@ApiModelProperty("复检员")
	private String reviewer;

	/**
	 * 复检时间
	 */
	@ApiModelProperty("复检时间")
	private Date reviewerDate;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;

	/**
	 * 列表及详情位置
	 */
	@ApiModelProperty("列表及详情位置")
	private String infoLocation;

	/**
	 * 列表及详情位置
	 * 
	 * @return
	 */
	public String getInfoLocation() {

		// ProjectType type = enumutil
		ProjectType type = EnumUtils.init(ProjectType.class).fromCode(this.projectType);

		// 城中村/工业园
		if (ProjectType.URBAN_VILLAGE == type || ProjectType.INDUSTRIAL_AREA == type) {

			if ("B".equalsIgnoreCase(this.formType)) {
				if (StrUtil.isNotBlank(this.formCode)) {
					IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory
							.getFormbDangerHander(this.formCode);
					if (formbDangerHander != null) {
						return formbDangerHander.getInfoLocation(this);
					}
				}
			}
		} else if (ProjectType.CHARGING_STATION == type) {
			// 充电桩
			if (StrUtil.isNotBlank(this.chargingPileName)) {
				return StrUtil.format("({}) {}", this.chargingPileName, this.location);
			}
		}

		String unitAreaName = StrUtil.isNotBlank(this.unitAreaName) ? this.unitAreaName : "";
		String buildingName = StrUtil.isNotBlank(this.buildingName) ? this.buildingName : "";
		return StrUtil.format("{}{}{}", buildingName, unitAreaName, this.location);
	}

	public String getDescription() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getDescription(this);
				}
			}
		}

		return this.description;
	}

	public String getSuggestions() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getSuggestions(this);
				}
			}
		}

		return this.suggestions;
	}

	public String getLevel() {
		// B表隐患位置处理
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getLevel(this);
				}
			}
		}

		return this.level;
	}

	public String getStatus() {
		if ("B".equalsIgnoreCase(this.formType)) {
			String result = getResult();
			if (IFormbDangerHandler.QUALIFIED.equals(result)) {
				// 非隐患
				return "9";
			} else if (IFormbDangerHandler.FAILURE.equals(result)) {
				return this.status;
			} else {
				return "";
			}
		}
		return this.status;
	}

	public String getResult() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getResult(this);
				}
			}
		}
		return null;
	}
	
	public String getDangerPic() {
		if ("B".equalsIgnoreCase(this.formType)) {
			if (StrUtil.isNotBlank(this.formCode)) {
				IFormbDangerHandler formbDangerHander = FormbDangerHandlerFactory.getFormbDangerHander(this.formCode);
				if (formbDangerHander != null) {
					return formbDangerHander.getPicture(this);
				}
			}
		}
		return this.dangerPic;
	}
}
