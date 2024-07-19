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
 * 公共查阅
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(autoResultMap = true)
public class PublicInspection extends BaseEntity<PublicInspection> {

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
	 * 类型1资料查阅2市政消火栓3消防安全网格化
	 */
	private String type;

	/**
	 * 内容
	 */
	@TableField(typeHandler = JsonObjectTypeHandler.class)
	private JSONObject content;

}
