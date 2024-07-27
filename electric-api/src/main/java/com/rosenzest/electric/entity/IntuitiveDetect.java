package com.rosenzest.electric.entity;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rosenzest.model.base.entity.BaseEntity;
import com.rosenzest.model.base.type.StringListTypeHandler;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 直观检测表标题
 * </p>
 *
 * @author fronttang
 * @since 2024-06-29
 */
@Data
@TableName(autoResultMap = true)
@EqualsAndHashCode(callSuper = true)
public class IntuitiveDetect extends BaseEntity<IntuitiveDetect> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 模板ID
	 */
	private Long templateId;

	/**
	 * 检测表名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 代号
	 */
	private String code;

	/**
	 * 业主单元类型(同高风险类型)
	 */
	private String unitType;

	/**
	 * 归属1公共区域2户3配电房
	 */
	@TableField(typeHandler = StringListTypeHandler.class)
	private List<String> attribution;

}
