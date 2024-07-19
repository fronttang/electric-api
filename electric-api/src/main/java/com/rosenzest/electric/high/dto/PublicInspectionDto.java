package com.rosenzest.electric.high.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 公共查阅
 * </p>
 *
 * @author fronttang
 * @since 2024-07-19
 */
@Data
public class PublicInspectionDto {

	/**
	 * 项目ID
	 */
	@ApiModelProperty(value = "项目ID", required = true)
	@NotNull(message = "项目ID不能为空")
	private Long projectId;

	/**
	 * 区
	 */
	@ApiModelProperty(value = "区", required = true)
	@NotBlank(message = "区不能为空")
	private String district;

	/**
	 * 街道
	 */
	@NotBlank(message = "街道不能为空")
	@ApiModelProperty(value = "街道", required = true)
	private String street;

	/**
	 * 社区
	 */
	@NotBlank(message = "社区不能为空")
	@ApiModelProperty(value = "社区", required = true)
	private String community;

	/**
	 * 类型1资料查阅2市政消火栓3消防安全网格化
	 */
	@ApiModelProperty("类型1资料查阅2市政消火栓3消防安全网格化")
	@NotBlank(message = "类型不能为空")
	private String type;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
	private JSONObject content;

}
