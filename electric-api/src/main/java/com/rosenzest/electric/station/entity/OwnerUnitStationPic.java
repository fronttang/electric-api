package com.rosenzest.electric.station.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.model.base.type.LongListTypeHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 充电站合格照片
 * </p>
 *
 * @author fronttang
 * @since 2024-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerUnitStationPic extends BaseEntity<OwnerUnitStationPic> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 检测模块
	 */
	private String module;

	/**
	 * 轮次
	 */
	private Integer rounds;

	/**
	 * 充电桩ID
	 */
	@TableField(typeHandler = LongListTypeHandler.class)
	private List<Long> pileIds;

	/**
	 * 合格照片
	 */
	private String pictures;

}
