package com.rosenzest.electric.storage.vo;

import java.math.BigDecimal;
import java.util.List;

import com.rosenzest.electric.storage.entity.PhotovoltaicConfig.EffectiveHours;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 光伏参数
 * </p>
 *
 * @author fronttang
 * @since 2024-09-25
 */
@Data
public class PhotovoltaicConfigVo {

	/**
	 * 光伏发电时间段（峰、平时段加权平均电价（度/元）)
	 */
	@ApiModelProperty("光伏发电时间段（峰、平时段加权平均电价（度/元）)")
	private BigDecimal averagePrice;

	/**
	 * 首年衰减率（%）
	 */
	@ApiModelProperty("首年衰减率（%）")
	private BigDecimal firstDecayRate;

	/**
	 * 逐步衰减率（%）
	 */
	@ApiModelProperty("逐步衰减率（%）")
	private BigDecimal stepDecayRate;

	/**
	 * 光伏组件功率（W）
	 */
	@ApiModelProperty("光伏组件功率（W）")
	private BigDecimal power;

	/**
	 * 年有效利用小时数设置
	 */
	@ApiModelProperty("年有效利用小时数")
	private List<EffectiveHours> effectiveHours;

}
