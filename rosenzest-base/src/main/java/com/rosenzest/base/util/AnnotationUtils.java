package com.rosenzest.base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author fronttang
 * @date 2021/07/05
 */
public final class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {

    /**
     * 获取方法和类上的注解
     * 
     * @param <A>
     *            注解类型
     * @param method
     *            方法
     * @param annotationType
     *            注解类型
     * @return 注解对象
     */
    public static <A extends Annotation> A findAnnotationAndDeclaringAnnotation(Method method,
        Class<A> annotationType) {
        if (annotationType == null) {
            return null;
        }

        A annotation = findAnnotation(method, annotationType);
        if (annotation == null) {
            annotation = findAnnotation(method.getDeclaringClass(), annotationType);
        }
        return annotation;
    }
}
