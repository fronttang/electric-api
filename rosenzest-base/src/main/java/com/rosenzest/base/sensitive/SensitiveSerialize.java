package com.rosenzest.base.sensitive;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.rosenzest.base.annotation.Sensitive;

import lombok.extern.slf4j.Slf4j;

/**
 * 脱敏序列化实现
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Slf4j
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private Sensitive sensitive;

    public SensitiveSerialize() {}

    public SensitiveSerialize(Sensitive sensitive) {
        this.sensitive = sensitive;
    }

    @Override
    public void serialize(String source, JsonGenerator jsonGenerator, SerializerProvider serializers)
        throws IOException {
        try {

            if (sensitive != null) {

                SensitiveWapper wapper = SensitiveWapper.build(sensitive);
                String result = SensitiveUtil.handleSensitive(source, wapper);
                jsonGenerator.writeString(result);

                return;
            }
            jsonGenerator.writeString(source);
        } catch (Exception e) {
            log.error("", e);
            jsonGenerator.writeString(source);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty property)
        throws JsonMappingException {
        if (property != null) { // 为空直接跳过
            if (Objects.equals(property.getType().getRawClass(), String.class)) { // 非 String 类直接跳过
                Sensitive sensitive = property.getAnnotation(Sensitive.class);
                if (sensitive == null) {
                    sensitive = property.getContextAnnotation(Sensitive.class);
                }
                if (sensitive != null) { // 如果能得到注解，就将注解 传入 SensitiveSerialize
                    return new SensitiveSerialize(sensitive);
                }
            }
            return serializerProvider.findValueSerializer(property.getType(), property);
        }
        return serializerProvider.findNullValueSerializer(property);
    }

}
