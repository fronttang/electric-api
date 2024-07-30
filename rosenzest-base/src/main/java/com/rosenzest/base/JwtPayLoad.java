/**
 * 
 */
package com.rosenzest.base;

import java.io.Serializable;

import lombok.Data;

/**
 * @author fronttang
 */
@Data
public class JwtPayLoad implements Serializable {

	private static final long serialVersionUID = -8399248780876539875L;

	/**
	 * 唯一表示id, 用于缓存登录用户的唯一凭证
	 */
	private Long userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 登录标识
	 */
	private String uuid;

}
