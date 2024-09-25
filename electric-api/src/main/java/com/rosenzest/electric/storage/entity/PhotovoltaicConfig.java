package com.rosenzest.electric.storage.entity;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rosenzest.electric.storage.type.EffectiveHoursTypeHandler;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 光伏参数
 * </p>
 *
 * @author fronttang
 * @since 2024-09-25
 */
@Data
@TableName(autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class PhotovoltaicConfig extends BaseEntity<PhotovoltaicConfig> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 光伏发电时间段（峰、平时段加权平均电价（度/元）
	 */
	private BigDecimal averagePrice;

	/**
	 * 首年衰减率（%）
	 */
	private BigDecimal firstDecayRate;

	/**
	 * 逐步衰减率（%）
	 */
	private BigDecimal stepDecayRate;

	/**
	 * 光伏组件功率（W）
	 */
	private BigDecimal power;

	/**
	 * 年有效利用小时数设置
	 */
	@TableField(typeHandler = EffectiveHoursTypeHandler.class)
	private List<EffectiveHours> effectiveHours;

	@Data
	public static class EffectiveHours {

		/**
		 * id
		 */
		private String id;

		/**
		 * 省市
		 */
		private String name;

		/**
		 * 年有效利用小时数
		 */
		private BigDecimal value;
	}

}
