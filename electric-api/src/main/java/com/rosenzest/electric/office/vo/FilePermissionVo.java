package com.rosenzest.electric.office.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FilePermissionVo {

	/**
	 * 当前用户 Id，当 update=1时必须返回当前用户的 user_id
	 */
	@JsonProperty("user_id")
	private String userId = "1";

	/**
	 * 是否具有预览权限，0-无 1-有
	 */
	private Integer read = 1;

	/**
	 * 是否具有编辑权限，0-无 1-有
	 */
	private Integer update = 1;

	/**
	 * 是否具有下载文档权限，0-无 1-有
	 */
	private Integer download = 1;

	/**
	 * 是否具有重命名文档权限，0-无 1-有
	 */
	private Integer rename = 0;

	/**
	 * 是否具有查看文档历史记录权限，0-无 1-有
	 */
	private Integer history = 0;

	/**
	 * 是否具有拷贝文档内容权限，0-无 1-有
	 */
	private Integer copy = 1;

	/**
	 * 是否具有打印文档权限，0-无 1-有
	 */
	private Integer print = 1;

	/**
	 * 是否具有另存当前文档权限，0-无 1-有
	 */
	private Integer saveas = 1;

	/**
	 * 是否具有评论文档权限，0-无 1-有。
	 */
	private Integer comment = 1;
}
