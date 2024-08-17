package com.rosenzest.electric.entity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.model.base.type.JsonObjectTypeHandler;
import com.rosenzest.model.base.type.LongListTypeHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fronttang
 * @since 2024-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true)
public class OwnerUnitDanger extends BaseEntity<OwnerUnitDanger> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 单元ID
	 */
	private Long unitId;

	/**
	 * 楼栋ID
	 */
	private Long buildingId;

	/**
	 * 单元区域ID
	 */
	private Long unitAreaId;

	/**
	 * 充电桩ID
	 */
	@TableField(typeHandler = LongListTypeHandler.class)
	private List<Long> chargingPileId;

	/**
	 * 轮次
	 */
	private Integer rounds;

	/**
	 * 隐患ID
	 */
	private Long dangerId;

	/**
	 * 检测表ID
	 */
	private Long formId;

	/**
	 * 检测表编号
	 */
	private String formCode;

	/**
	 * 检测表类型A/B/C
	 */
	private String formType;

	/**
	 * 检测表数据ID
	 */
	private Long formDataId;

	/**
	 * 隐患等级
	 */
	private String level;

	/**
	 * 隐患描述
	 */
	private String description;

	/**
	 * 整改建议
	 */
	private String suggestions;

	/**
	 * 位置
	 */
	private String location;

	/**
	 * 隐患图片
	 */
	private String dangerPic;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 检测员
	 */
	private String inspector;

	/**
	 * 检测员ID
	 */
	private Long inspectorId;

	/**
	 * 初检时间
	 */
	private Date initialTime;

	/**
	 * B类表数据
	 */
	@TableField(typeHandler = JsonObjectTypeHandler.class)
	private JSONObject formb;

	/**
	 * B14类型 residualCurrent 剩余电流值，alarmTime报警时间
	 */
	@TableField(exist = false)
	private String b14Type;

	public String getB14Type() {
		if (this.formb != null && this.formb.getJSONObject("data") != null) {
			return this.formb.getJSONObject("data").getString("type");
		}
		return null;
	}

	public void setB14Type(String type) {
		if (this.formb != null && this.formb.getJSONObject("data") != null) {
			this.formb.getJSONObject("data").put("type", type);
		}
	}

	/**
	 * 整改员
	 */
	private String rectification;

	/**
	 * 整改时间
	 */
	private Date rectificationDate;

	/**
	 * 复检员
	 */
	private String reviewer;

	/**
	 * 复检时间
	 */
	private Date reviewerDate;

	/**
	 * 整改图
	 */
	private String rectificationPic;

	/**
	 * 整改未通过原因
	 */
	private String reason;

	/**
	 * 检测图
	 */
	private String detectPic;

	/**
	 * 备注
	 */
	private String remark;

}
