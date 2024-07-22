package com.rosenzest.electric.office.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FileInfoVo {

	/**
	 * 文档 ID，必须与传入的file_id一致，最大长度 47
	 */
	private String id;

	/**
	 * 文档名称，最大长度 240，不能包含下列特殊字符：\/|":*?<>
	 */
	private String name;

	/**
	 * 文档版本号，无符号 int32 位，从 1 开始，每次保存后递增，如果已经迭代了多个版本，这里您需要返回最新的版本号
	 */
	private Integer version;

	/**
	 * 文档大小，单位 byte
	 */
	private Long size;

	/**
	 * 文档创建时间戳，单位纪元秒
	 */
	@JsonProperty("create_time")
	private Long createTime;

	/**
	 * 文档最后修改时间戳，单位纪元秒
	 */
	@JsonProperty("modify_time")
	private Long modifyTime;

	/**
	 * 文档创建者 Id
	 */
	@JsonProperty("creator_id")
	private String creatorId = "1";

	/**
	 * 文档最后修改者 Id
	 */
	@JsonProperty("modifier_id")
	private String modifierId = "1";
}
