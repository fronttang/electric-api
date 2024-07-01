/**
 * 
 */
package com.rosenzest.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型
 * 
 * @author fronttang
 */
@Getter
@AllArgsConstructor
public enum LoginType implements IEnum<LoginType> {

    /**
     * 客户
     */
    SYSTEM("00", "系统用户"),

    /**
     * 地推人员
     */
    DETECT_ADMIN("01", "地推人员")

    ;

    final String code;

    final String name;

}
