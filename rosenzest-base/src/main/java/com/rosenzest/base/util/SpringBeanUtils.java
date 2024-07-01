/**
 *
 */
package com.rosenzest.base.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * spring bean util
 * 
 * @author fronttang
 */
@Component
public class SpringBeanUtils implements BeanFactoryAware {

    @NonNull
    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringBeanUtils.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return beanFactory.getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

}
