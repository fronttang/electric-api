package com.rosenzest.base.enums;

import java.io.Serializable;

/**
 * 枚举常量类的父类
 * 
 * @author fronttang
 * @date 2021/07/21
 */
public interface IEnum<E extends Enum<E>> extends Serializable {

    /**
     * 获取名称
     * 
     * @return
     */
    default String desc() {
        return this.getName();
    }

    /**
     * 获取code
     * 
     * @return
     */
    default String code() {
        return getCode();
    }

    /**
     * 获取code
     * 
     * @return
     */
    String getCode();

    /**
     * 获取名称
     * 
     * @return
     */
    String getName();

}
