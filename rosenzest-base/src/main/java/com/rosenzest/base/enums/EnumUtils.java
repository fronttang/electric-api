package com.rosenzest.base.enums;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import cn.hutool.core.util.StrUtil;

/**
 * 枚举工具类</br>
 * 使用方式</br>
 * <code>
 * LoginType loginType = EnumUtils.init(LoginType.class).fromCode("1");</br>
 * String name = EnumUtils.init(LoginType.class).getNamefromCode("1");</br>
 * boolean inEnum = EnumUtils.init(LoginType.class).isInEnum("1");</br>
 * </code>
 * 
 * @author fronttang
 * @date 2021/07/22
 */
public final class EnumUtils<E extends IEnum<?>> {

    private static final ConcurrentHashMap<Class<?>, EnumUtils<?>> CACHE =
        new ConcurrentHashMap<Class<?>, EnumUtils<?>>();

    private Class<E> clazz;

    private EnumUtils(Class<E> clazz) {
        this.clazz = clazz;
    }

    @SuppressWarnings("unchecked")
    public static <E extends IEnum<?>> EnumUtils<E> init(Class<E> clazz) {
        EnumUtils<E> enumUtil = (EnumUtils<E>)CACHE.get(clazz);
        if (Objects.isNull(enumUtil)) {
            enumUtil = new EnumUtils<E>(clazz);
            CACHE.put(clazz, enumUtil);
        }
        return enumUtil;
    }

    /**
     * 获取所有枚举对象
     *
     * @return 枚举对象数组
     */
    public E[] items() {
        return clazz.getEnumConstants();
    }

    /**
     * 根据code获取
     * 
     * @param code
     * @return
     */
    public E fromCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        E[] items = items();
        for (E enumItem : items) {
            if (enumItem.code().equals(code)) {
                return enumItem;
            }
        }
        return null;
    }

    /**
     * 根据code获取名称
     * 
     * @param code
     * @return
     */
    public String getNamefromCode(String code) {
        E item = fromCode(code);
        return Objects.nonNull(item) ? item.desc() : null;
    }

    /**
     * 是否在枚举常量里
     * 
     * @param code
     * @return
     */
    public boolean isInEnum(String code) {
        E item = fromCode(code);
        return item != null;
    }
}
