package com.rosenzest.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.rosenzest.api.annotation.ApiScan;
import com.rosenzest.api.annotation.ApiScans;

/**
 * API扫描注册
 * 
 * @author fronttang
 * @date 2021/09/13
 */
public class ApiScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes annoAttrs =
            AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ApiScan.class.getName()));
        if (Objects.nonNull(annoAttrs)) {
            scanApi(annoAttrs, annotationMetadata, registry);
        }
    }

    void scanApi(AnnotationAttributes annoAttrs, AnnotationMetadata annotationMetadata,
        BeanDefinitionRegistry registry) {

        List<String> basePackages = new ArrayList<>();
        basePackages.addAll(
            Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText)
            .collect(Collectors.toList()));

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(annotationMetadata.getClassName()));
        }

        ApiScanner scanner = new ApiScanner(registry);
        scanner.scan(StringUtils.tokenizeToStringArray(StringUtils.collectionToCommaDelimitedString(basePackages),
            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    /**
     * A {@link ApiScannerRegistrar} for {@link ApiScans}.
     */
    public static class RepeatingRegistrar extends ApiScannerRegistrar {
        /**
         * {@inheritDoc}
         */
        @Override
        public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
            AnnotationAttributes annoAttrs =
                AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ApiScans.class.getName()));

            if (Objects.nonNull(annoAttrs)) {

                AnnotationAttributes[] annotations = annoAttrs.getAnnotationArray("value");
                for (int i = 0; i < annotations.length; i++) {
                    scanApi(annotations[i], annotationMetadata, registry);
                }
            }
        }
    }

}
