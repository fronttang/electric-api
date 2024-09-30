package com.rosenzest.electric.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = SystemProperties.PREFIX)
public class SystemProperties {

	public static final String PREFIX = "system";

	/**
	 * 上传路径
	 */
	private static String profile = "/dat/upload";

	/**
	 * 访问地址
	 */
	private String domain;

	/**
	 * 小程序业主端地址
	 */
	private Miniapp miniapp = new Miniapp();

	/**
	 * 
	 */
	private String ownerUnitDesKey = "0185786A0362F7F2B0C316B31D1BAD62";

	private Echarts echarts = new Echarts();

	@Data
	public static class Echarts {

		/**
		 * echarts 渲染接口
		 */
		private String api;
	}

	@Data
	public static class Miniapp {

		/**
		 * 业主端地址
		 */
		private String ownerUrl = "pages/home/user/index";
	}

	public static String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		SystemProperties.profile = profile;
	}

	/**
	 * 获取上传路径
	 */
	public static String getUploadPath() {
		return getProfile() + "/upload";
	}
}
