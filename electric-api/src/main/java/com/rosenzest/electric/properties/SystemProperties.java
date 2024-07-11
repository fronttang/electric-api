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
