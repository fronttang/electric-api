package com.rosenzest.electric.office.dto;

import lombok.Data;

@Data
public class FileUploadDto {

	/**
	 * 文档名称
	 */
	private String name;

	/**
	 * 文档大小，单位 byte
	 */
	private Long size;

	/**
	 * 文档校验和，采用 sha1 算法
	 */
	private String sha1;

	/**
	 * 是否手动保存，即用户手动 ctrl/cmd + s 或点击保存版本触发的保存，区别于定时触发的自动保存
	 */
	private Boolean is_manual;

	/**
	 * 文档内包含的附件的大小，单位 byte
	 */
	private Long attachment_size;

	/**
	 * 文档的 MIME 类型
	 */
	private String content_type;
}
