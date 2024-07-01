package com.rosenzest.base.sensitive;

import com.rosenzest.base.sensitive.handler.RegexpSensitiveHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 脱敏类型枚举类
 * 
 * @author fronttang
 * @date 2021/07/30
 */
@Getter
@AllArgsConstructor
public enum SensitiveType implements ISensitive {

    /**
     * 中文名
     */
    CHINESE_NAME("(?<=.{1})[\\u4E00-\\u9FFF]", "*"),

    /**
     * 身份证号
     */
    ID_CARD("(?<=.{6})\\d(?=.{4})", "*"),

    /**
     * 座机号
     */
    FIXED_PHONE("(0\\d{2,3})-?[1-9]\\d{3,4}(\\d{3})", "$1****$2"),

    /**
     * 手机号
     */
    MOBILE_PHONE("(?<=.{3})\\d(?=.{4})", "*"),

    /**
     * 地址
     */
    ADDRESS("(?<=.{6})\\S", "*"),

    /**
     * 电子邮件
     */
    EMAIL("(?<=.{2})[^@]+(?=.{2}@)", "****"),

    /**
     * 银行卡
     */
    BANK_CARD("(?<=\\d{6})\\d(?=\\w{4})", "*"),

    /**
     * 公司开户银行联号
     */
    // CNAPS_CODE("", "", RegexpSensitiveHandler.class),

    /**
     * 车牌号
     */
    CAR_NUMBER("(?<=.{2})[A-HJ-NP-Z0-9](?=.{2})", "*"),

    /**
     * 社会统一信用代码
     */
    CREDIT_CODE("(?<=.{6})\\w(?=.{4})", "*"),

    /**
     * 车架号
     */
    CAR_VIN("(?<=.{4})\\w(?=.{4})", "*"),

    /**
     * 密码
     */
    PASSWORD(".*", "****"),

    /**
     * token
     */
    TOKEN(".*", "****"),

    /**
     * 公司名
     */
    COMPANY("(?<=.{4})\\S(?=.{4})", "*");

    ;

    private SensitiveType(String pattern, String replace) {
        this.pattern = pattern;
        this.replace = replace;
    }

    /**
     * 正则
     */
    private String pattern;

    /**
     * 正则替换的内容
     */
    private String replace;

    /**
     * 处理器
     */
    private Class<? extends ISensitiveHandler> handler = RegexpSensitiveHandler.class;

}
