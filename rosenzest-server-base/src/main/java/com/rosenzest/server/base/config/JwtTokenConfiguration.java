package com.rosenzest.server.base.config;

import org.springframework.context.annotation.Bean;

import com.rosenzest.server.base.properties.TokenProperties;
import com.rosenzest.server.base.util.JwtTokenUtil;

/**
 * @author fronttang
 * @date 2021/07/23
 */
public class JwtTokenConfiguration {

    @Bean
    public JwtTokenUtil JwtTokenUtil(TokenProperties tokenProperties) {
        return JwtTokenUtil.init(tokenProperties);
    }
}
