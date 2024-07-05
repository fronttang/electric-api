package com.rosenzest.model.base.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author fronttang
 * @date 2021/06/30
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseTimeEntity<T extends BaseModel<?>> extends BaseModel<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 创建人
	 */
	@TableField(value = "CREATE_BY", fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 更新时间
	 */
	@TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE, update = "now()")
	private Date updateTime;

	/**
	 * 更新人
	 */
	@TableField(value = "UPDATE_BY", fill = FieldFill.UPDATE)
	private String updateBy;
}
