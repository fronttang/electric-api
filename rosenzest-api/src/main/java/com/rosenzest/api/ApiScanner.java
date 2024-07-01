package com.rosenzest.api;

import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.rosenzest.api.annotation.Api;

/**
 * api接口扫描器,将扫描到的接口注册到spring容器
 * 
 * @author fronttang
 * @date 2021/09/13
 */
public class ApiScanner extends ClassPathBeanDefinitionScanner {

    public ApiScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(Api.class));
        addIncludeFilter(new AssignableTypeFilter(IApi.class));
        Set<BeanDefinitionHolder> beanSet = super.doScan(basePackages);
        return beanSet;
    }
}
