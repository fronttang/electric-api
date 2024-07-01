package com.rosenzest.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录终端类型
 *
 * @author L.D.J
 * @date 2021/07/20 17:38
 **/
@Getter
@AllArgsConstructor
public enum TerminalType implements IEnum<TerminalType> {

    /**
     * APP
     */
    APP("1", "APP");

    final String code;

    final String name;

}
