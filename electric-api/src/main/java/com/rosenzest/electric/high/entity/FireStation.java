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
 * 消防站
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true)
public class FireStation extends BaseEntity<FireStation> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 区
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
	 * 名称
	 */
	private String name;

	/**
	 * 类型1社区微型2重点单位3社区小型4乡镇传职
	 */
	private String type;

	/**
	 * 检测表
	 */
	@TableField(typeHandler = JsonObjectTypeHandler.class)
	private JSONObject detect;

	/**
	 * 消防设备
	 */
	@TableField(typeHandler = JsonObjectTypeHandler.class)
	private JSONObject device;

}
