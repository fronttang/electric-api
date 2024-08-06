/**
 * 
 */
package com.rosenzest.electric.vo;

import com.rosenzest.electric.miniapp.vo.OwnerUnitOverviewVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 登录返回
 * 
 * @author fronttang
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {

	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户ID")
	private Long userId;

	/**
	 * 登录用户名
	 */
	@ApiModelProperty("登录用户名")
	private String userName;

	/**
	 * 姓名
	 */
	@ApiModelProperty("姓名")
	private String nickName;

	/**
	 * TOKEN
	 */
	@ApiModelProperty("TOKEN")
	private String token;

	/**
	 * 用户类型 00平台用户 01检测单位管理员 02工作人员账号 03街区账号 04网格员 05业主单元账号 99游客
	 */
	@ApiModelProperty("用户类型 00平台用户 01检测单位管理员 02工作人员账号 03街区账号 04网格员 05业主单元账号 99游客")
	private String type;

	/**
	 * 检测单位
	 */
	@ApiModelProperty("检测单位")
	private DetectUnitVo detectUnit;

	/**
	 * 项目信息
	 */
	@ApiModelProperty("项目信息")
	private ProjectVo project;

	/**
	 * 业主单元信息
	 */
	@ApiModelProperty("业主单元信息")
	private OwnerUnitOverviewVo unit;

}
