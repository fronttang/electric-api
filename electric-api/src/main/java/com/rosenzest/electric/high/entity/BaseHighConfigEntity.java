package com.rosenzest.electric.high.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseHighConfigEntity<T extends BaseEntity<?>> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;

	/**
	 * 业主单元ID
	 */
	@TableId(type = IdType.INPUT)
	private Long unitId;

}
