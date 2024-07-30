/**
 * 
 */
package com.rosenzest.server.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fronttang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = TokenProperties.PREFIX)
public class TokenProperties {

	public static final String PREFIX = "token";

	/**
	 * 过期时间
	 */
	private Long expire = 7200L;

	/**
	 * 秘钥
	 */
	private String secret = "e084c6ecce5976059eab00d0d6382e18";

	/**
	 * 请求头名称
	 */
	private String header = "token";

	/**
	 * token 加密配置
	 */
	private TokenSM sm = new TokenSM();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class TokenSM {

		/**
		 * SM加密状态开
		 */
		public static final String SM_ON = "on";

		/**
		 * SM加密状态关
		 */
		public static final String SM_OFF = "off";

		/**
		 * SM加密开启状态 on代表开启 off代表关闭
		 */
		private String sts = "off";

		/**
		 * SM加密私钥
		 */
		private String privateKey;

		/**
		 * 私钥文件地址
		 */
		private String privateKeyResource = "";
	}
}
