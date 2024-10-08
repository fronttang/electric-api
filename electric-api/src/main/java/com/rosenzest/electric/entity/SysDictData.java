package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字典数据表
 * </p>
 *
 * @author fronttang
 * @since 2024-07-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictData extends BaseEntity<SysDictData> {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典编码
	 */
	@TableId(value = "dict_code", type = IdType.AUTO)
	private Long dictCode;

	/**
	 * 字典排序
	 */
	private Integer dictSort;

	/**
	 * 字典标签
	 */
	private String dictLabel;

	/**
	 * 字典键值
	 */
	private String dictValue;

	/**
	 * 字典类型
	 */
	private String dictType;

	/**
	 * 样式属性（其他样式扩展）
	 */
	private String cssClass;

	/**
	 * 表格回显样式
	 */
	private String listClass;

	/**
	 * 是否默认（Y是 N否）
	 */
	private String isDefault;

	/**
	 * 状态（0正常 1停用）
	 */
	private String status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/**
	 * 项目ID
	 */
	private Long projectId;

}
