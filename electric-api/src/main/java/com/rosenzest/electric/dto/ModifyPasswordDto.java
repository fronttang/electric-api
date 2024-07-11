package com.rosenzest.electric.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
public class ModifyPasswordDto {

	/**
	 * 密码
	 */
	@NotBlank(message = "旧密码不能为空")
	@ApiModelProperty("旧密码")
	private String password;

	/**
	 * 新密码
	 */
	@NotBlank(message = "新密码不能为空")
	@ApiModelProperty("新密码")
	private String newPassword;
}
