package com.rosenzest.base.sensitive;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.serializer.ValueFilter;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;

/**
 * fastjson 脱敏过滤器
 * 
 * @author fronttang
 * @date 2021/08/02
 */
@Slf4j
public class FastJsonSensitiveFilter implements ValueFilter {

    @Override
    public Object process(Object object, String name, Object value) {

        try {
            // 只处理string 类型的
            if (value instanceof String) {

                Map<String, SensitiveWapper> sensitiveMap = SensitiveConstants.SENSITIVE_MAP;

                SensitiveWapper sensitiveWapper = sensitiveMap.get(name);
                if (sensitiveWapper != null) {
                    String resultValue = SensitiveUtil.handleSensitive(Convert.toStr(value), sensitiveWapper);
                    return resultValue;
                }
            }

            if (value instanceof List) {
                List resultValue = (List)value;
                return resultValue.subList(0, resultValue.size() > 10 ? 10 : resultValue.size());
            }

        } catch (Exception e) {
            log.error("", e);
        }
        return value;
    }

}
