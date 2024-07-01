package com.rosenzest.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rosenzest.base.sensitive.ISensitiveHandler;
import com.rosenzest.base.sensitive.SensitiveSerialize;
import com.rosenzest.base.sensitive.SensitiveType;
import com.rosenzest.base.sensitive.handler.RegexpSensitiveHandler;

/**
 * 脱敏字段注解,用于标记该字段需要脱敏<br/>
 * type为系统定义的常用脱敏字段类型<br/>
 * handler可以自定义脱敏实现方式<br/>
 * 
 * type 和 其他参数二选一
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface Sensitive {

    /**
     * 脱敏类型
     */
    SensitiveType type();

    /**
     * 正则
     */
    String pattern() default "";

    /**
     * 正则替换的内容
     */
    String replace() default "";

    /**
     * 脱敏处理器 ,默认是 {@link com.xmzy.base.sensitive.handler.RegexpSensitiveHandler}
     */
    Class<? extends ISensitiveHandler> handler() default RegexpSensitiveHandler.class;
}
