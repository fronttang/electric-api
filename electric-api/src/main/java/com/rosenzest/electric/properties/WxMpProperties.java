package com.rosenzest.electric.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {
	/**
	 * 多个公众号配置信息
	 */
	private List<MpConfig> configs;

	@Data
	public static class MpConfig {
		/**
		 * 设置微信公众号的appid
		 */
		private String appId;

		/**
		 * 设置微信公众号的app secret
		 */
		private String secret;

		/**
		 * 设置微信公众号的token
		 */
		private String token;

		/**
		 * 设置微信公众号的EncodingAESKey
		 */
		private String aesKey;

		/**
		 * 设置微信公众号的授权域名
		 */
		private String url;
	}
}
