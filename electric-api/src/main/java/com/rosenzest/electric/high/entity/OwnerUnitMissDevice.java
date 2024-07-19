package com.rosenzest.electric.high.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.model.base.type.JsonObjectTypeHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 缺失设备
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true)
public class OwnerUnitMissDevice extends BaseEntity<OwnerUnitMissDevice> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 楼层
	 */
	private String floor;

	/**
	 * 类型1楼层设备2楼栋设备
	 */
	private String type;

	/**
	 * 缺失设备
	 */
	@TableField(typeHandler = JsonObjectTypeHandler.class)
	private JSONObject device;

}
