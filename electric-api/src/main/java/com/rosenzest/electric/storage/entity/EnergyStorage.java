package com.rosenzest.electric.storage.entity;

import java.math.BigDecimal;

import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 储能
 * </p>
 *
 * @author fronttang
 * @since 2024-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EnergyStorage extends BaseEntity<EnergyStorage> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 项目名称
	 */
	private String name;

	/**
	 * 省市
	 */
	private String area;

	/**
	 * 项目地址
	 */
	private String address;

	/**
	 * 业主方
	 */
	private String owner;

	/**
	 * 投资方
	 */
	private String investor;

	/**
	 * 尖（元/度）
	 */
	private BigDecimal jian;

	/**
	 * 峰（元/度）
	 */
	private BigDecimal feng;

	/**
	 * 平（元/度）
	 */
	private BigDecimal ping;

	/**
	 * 谷（元/度）
	 */
	private BigDecimal gu;

	/**
	 * 变压器容量（KVA）
	 */
	private BigDecimal capacity;

	/**
	 * 业主享受分成比例（%）
	 */
	private BigDecimal proportion;

	/**
	 * 计量点编号/表号
	 */
	private String code;

	/**
	 * 年运行天数（天）
	 */
	private Long annualDays;

	/**
	 * 运营年限（年）
	 */
	private Long operationYears;

	/**
	 * 充放电深度（DOD)（%）
	 */
	private BigDecimal dod;

	/**
	 * 系统综合效率（%）
	 */
	private BigDecimal efficiency;

	/**
	 * 储能系统首年衰减（%）
	 */
	private BigDecimal firstDecayRate;

	/**
	 * 储能系统次年开始衰减（%）
	 */
	private BigDecimal stepDecayRate;

	/**
	 * 综合落地造价（元/kWh）
	 */
	private BigDecimal cost;

	/**
	 * 需求侧响应收益（万元/年）
	 */
	private BigDecimal income;

	/**
	 * 虚拟电厂运营（万元/年）
	 */
	private BigDecimal virtualOperation;

	/**
	 * 业主享受分成比例（其他部分）（%）
	 */
	private BigDecimal proportionOther;

}
