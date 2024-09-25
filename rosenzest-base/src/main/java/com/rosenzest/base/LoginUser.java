/**
 * 
 */
package com.rosenzest.base;

import com.rosenzest.base.enums.TerminalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录用户基础信息
 * 
 * @author fronttang
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginUser extends JwtPayLoad {

	private static final long serialVersionUID = -233861039549453822L;

	/**
	 * 用户类型 00系统用户,01检测单位管理员
	 */
	private String type;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 终端类型 APP
	 */
	private TerminalType terminal;

	/**
	 * 检测单位ID
	 */
	private Long detectId;

	/**
	 * 项目ID
	 */
	private Long projectId;

	/**
	 * 业主单元ID
	 */
	private Long unitId;

	/**
	 * 账号类型 1操作员 2单位
	 */
	private String accountType;

}
