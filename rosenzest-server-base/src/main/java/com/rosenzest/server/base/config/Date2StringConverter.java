package com.rosenzest.server.base.config;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import cn.hutool.core.date.DateUtil;

/** 全局handler前日期统一处理 */
@Component
public class Date2StringConverter implements Converter<Date, String> {

    @Override
    public String convert(Date source) {
        try {
            return DateUtil.formatDateTime(source);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid value '" + source + "'");
        }
    }

}
