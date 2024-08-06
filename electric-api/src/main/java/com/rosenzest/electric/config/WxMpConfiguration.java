package com.rosenzest.electric.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rosenzest.electric.properties.WxMpProperties;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {

	@Autowired
	private WxMpProperties properties;

	@Bean
	public WxMaService wxMpService() {
		final List<WxMpProperties.MpConfig> configs = this.properties.getConfigs();
		if (configs == null) {
			throw new RuntimeException("公众号配置出错！");
		}

		WxMaService service = new WxMaServiceImpl();
		service.setMultiConfigs(configs.stream().map(a -> {
			WxMaDefaultConfigImpl configStorage = new WxMaDefaultConfigImpl();
			configStorage.setAppid(a.getAppId());
			configStorage.setSecret(a.getSecret());
			return configStorage;
		}).collect(Collectors.toMap(WxMaDefaultConfigImpl::getAppid, a -> a, (o, n) -> o)));
		return service;
	}

}
