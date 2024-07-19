package com.rosenzest.electric.high.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.model.base.type.JsonObjectTypeHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业主单元消防配置
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true)
public class OwnerUnitConfig extends BaseEntity<OwnerUnitConfig> {

	private static final long serialVersionUID = 1L;

	/**
	 * 业主单元ID
	 */
	@TableId(type = IdType.INPUT)
	private Long unitId;

	/**
	 * 消防配置
	 */
	@TableField(typeHandler = JsonObjectTypeHandler.class)
	private JSONObject config;

}
