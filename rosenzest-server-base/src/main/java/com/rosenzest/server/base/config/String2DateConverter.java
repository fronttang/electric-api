package com.rosenzest.server.base.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.rosenzest.base.exception.BusinessException;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/** 全局handler前日期统一处理 */
@Component
public class String2DateConverter implements Converter<String, Date> {

    private static final List<String> formats = new ArrayList<>(10);

    static {
        formats.add("yyyy-MM");
        formats.add("yyyy-MM-dd");
        formats.add("yyyy-MM-dd HH:mm");
        formats.add("yyyy-MM-dd HH:mm:ss");
        formats.add("yyyy/MM");
        formats.add("yyyy/MM/dd");
        formats.add("yyyy/MM/dd HH:mm");
        formats.add("yyyy/MM/dd HH:mm:ss");
        formats.add("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        formats.add("EEE MMM dd HH:mm:ss Z yyyy");
    }

    @Override
    public Date convert(String source) {
        try {
            return DateUtil.parse(source);
        } catch (Exception e) {
            return parseDate(source);
        }
        // if (source.matches("^\\d{4}-\\d{1,2}$")) {
        // return parseDate(source, formats.get(0));
        // } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
        // return parseDate(source, formats.get(1));
        // } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
        // return parseDate(source, formats.get(2));
        // } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
        // return parseDate(source, formats.get(3));
        // } else if (source.matches("^\\d{4}/\\d{1,2}$")) {
        // return parseDate(source, formats.get(4));
        // } else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
        // return parseDate(source, formats.get(5));
        // } else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
        // return parseDate(source, formats.get(6));
        // } else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
        // return parseDate(source, formats.get(7));
        // } else {
        // throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
        // }
    }

    private Date parseDate(String dateStr) {
        String value = dateStr.trim();
        if ("".equals(value)) {
            return null;
        }
        try {
            return DateUtils.parseDate(value, formats.toArray(new String[10]));
        } catch (Exception e) {
            throw new BusinessException(StrUtil.format("不能识别的日期格式:{}", value));
        }
    }
}
