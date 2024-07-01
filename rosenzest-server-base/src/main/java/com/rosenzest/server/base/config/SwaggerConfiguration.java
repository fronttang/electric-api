package com.rosenzest.server.base.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.rosenzest.base.constant.ResultEnum;
import com.rosenzest.server.base.properties.SwaggerProperties;

import cn.hutool.core.collection.CollectionUtil;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Autowired
    private SwaggerProperties properties;

    @Bean
    public Docket customDocket() {

        /**
         * 请求头token参数
         */
        // Parameter parameter = new ParameterBuilder().name("token").description("token令牌")
        // .modelRef(new ModelRef("string")).parameterType("header").required(false).build();

        List<Parameter> parameters = CollectionUtil.newArrayList();
        // parameters.add(parameter);

        // 设定全局返回值说明
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        for (ResultEnum resultEnum : ResultEnum.values()) {
            responseMessageList
                .add(new ResponseMessageBuilder().code(resultEnum.getCode()).message(resultEnum.getMsg()).build());
        }

        return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(parameters)
            .globalResponseMessage(RequestMethod.GET, responseMessageList)
            .globalResponseMessage(RequestMethod.POST, responseMessageList)
            .globalResponseMessage(RequestMethod.PUT, responseMessageList)
            .globalResponseMessage(RequestMethod.DELETE, responseMessageList).apiInfo(apiInfo()).select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any()).build();
    }

    /**
     * name:开发者姓名 url:开发者网址 email:开发者邮箱
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact(properties.getContact().getName(), properties.getContact().getUrl(),
            properties.getContact().getEmail());
        return new ApiInfoBuilder().title(properties.getTitle()) // 标题
            .description(properties.getDesc()) // 文档接口的描述
            .contact(contact).version(properties.getVersion()) // 版本号
            .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
