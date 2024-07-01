package com.rosenzest.base.sensitive;

import java.util.HashMap;
import java.util.Map;

/**
 * 脱敏相关常量
 * 
 * @author fronttang
 * @date 2021/08/02
 */
public interface SensitiveConstants {

    /**
     * 需要脱敏字段及处理方式常量
     */
    Map<String, SensitiveWapper> SENSITIVE_MAP = new HashMap<String, SensitiveWapper>() {

        private static final long serialVersionUID = 9129217441554128851L;

        {
            put("password", SensitiveType.PASSWORD.toWapper());
            put("passWord", SensitiveType.PASSWORD.toWapper());
            put("passwd", SensitiveType.PASSWORD.toWapper());
            put("token", SensitiveType.TOKEN.toWapper());
            put("phone", SensitiveType.MOBILE_PHONE.toWapper());
            put("userId", SensitiveType.MOBILE_PHONE.toWapper());
            put("mobile", SensitiveType.MOBILE_PHONE.toWapper());
            put("username", SensitiveType.MOBILE_PHONE.toWapper());
            put("userName", SensitiveType.MOBILE_PHONE.toWapper());
            put("loginName", SensitiveType.MOBILE_PHONE.toWapper());
            put("credNo", SensitiveType.ID_CARD.toWapper());
            put("cred_no", SensitiveType.ID_CARD.toWapper());
            put("idCardNo", SensitiveType.ID_CARD.toWapper());
            put("relationCred", SensitiveType.ID_CARD.toWapper());
            put("address", SensitiveType.ADDRESS.toWapper());
            put("ads", SensitiveType.ADDRESS.toWapper());
            put("credAddress", SensitiveType.ADDRESS.toWapper());
            put("bankcard", SensitiveType.BANK_CARD.toWapper());
            put("carNo", SensitiveType.CAR_NUMBER.toWapper());
            put("car_no", SensitiveType.CAR_NUMBER.toWapper());
            put("carDiscernNo", SensitiveType.CAR_VIN.toWapper());
            put("custName", SensitiveType.CHINESE_NAME.toWapper());
            put("relationName", SensitiveType.CHINESE_NAME.toWapper());
            put("nme", SensitiveType.CHINESE_NAME.toWapper());
            put("owner", SensitiveType.CHINESE_NAME.toWapper());
            put("email", SensitiveType.EMAIL.toWapper());
        }
    };

}
