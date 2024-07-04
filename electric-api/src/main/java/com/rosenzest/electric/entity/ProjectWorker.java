package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目工作人员
 * </p>
 *
 * @author fronttang
 * @since 2024-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectWorker extends BaseEntity<ProjectWorker> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 绑定类型
	 */
	private String bindType;

}
