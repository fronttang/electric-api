package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目工作人员区域
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectWorkerArea extends BaseEntity<ProjectWorkerArea> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 项目工作人员ID
	 */
	private Long workerId;

	/**
	 * 数据权限1查看,2编辑
	 */
	private String type;

	/**
	 * 区县
	 */
	private String district;

	@TableField(exist = false)
	private String districtName;

	/**
	 * 街道
	 */
	private String street;

	@TableField(exist = false)
	private String streetName;

	/**
	 * 社区
	 */
	private String community;

	@TableField(exist = false)
	private String communityName;

	/**
	 * 村
	 */
	private String hamlet;

	@TableField(exist = false)
	private String hamletName;

}
