package com.rosenzest.electric.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rosenzest.model.base.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 版本更新
 * </p>
 *
 * @author fronttang
 * @since 2024-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientVersion extends BaseEntity<ClientVersion> {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 客户端
	 */
	private String client;

	/**
	 * 版本名称
	 */
	private String name;

	/**
	 * 版本代码
	 */
	private String version;

	/**
	 * 强制更新1是0否
	 */
	private String forced;

	/**
	 * 更新内容
	 */
	private String content;

	/**
	 * 下载地址
	 */
	private String downloadUrl;

}
