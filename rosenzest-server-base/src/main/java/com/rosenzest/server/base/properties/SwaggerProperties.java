package com.rosenzest.server.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fronttang
 * @date 2021/07/27
 */
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
@Data
public class SwaggerProperties {

    public static final String PREFIX = "swagger";

    /**
     * 开启状态
     */
    private boolean enable = true;

    /**
     * 标题
     */
    private String title = "电安通APP接口";

    /**
     * 说明
     */
    private String desc = "API接口的描述";

    /**
     * 版本
     */
    private String version = "1.1.0";

    /**
     * 联系信息
     */
    private Contact contact = new Contact();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Contact {

        private String name = "电安通APP接口";

        private String url = "";

        private String email = "暂无";
    }
}
