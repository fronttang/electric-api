/**
 * 
 */
package com.rosenzest.electric.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rosenzest.base.annotation.EnumValue;
import com.rosenzest.base.enums.TerminalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fronttang
 */
@Data
@ApiModel
@NoArgsConstructor
public class LoginDto {

	@ApiModelProperty("用户名")
	@NotBlank(message = "用户名不能为空")
	private String userName;

	@ApiModelProperty("密码")
	@NotBlank(message = "密码不能为空")
	private String password;

	@NotNull(message = "终端类型不能为空")
	@ApiModelProperty("终端 APP,MINIAPP，默认APP")
	@EnumValue(enumClass = TerminalType.class)
	private String terminal = TerminalType.APP.code();

	public LoginDto(@NotBlank String userName, @NotBlank String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

}
