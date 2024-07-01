package com.rosenzest.base.cache;

import java.util.Collection;
import java.util.Map;

/**
 * 缓存操作的基础接口，可以实现不同种缓存实现
 * <p>
 * 泛型为cache的值类class类型
 */
public interface CacheOperator<T> {

    /**
     * 添加缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     */
    void put(String key, T value);

    /**
     * 添加缓存（带过期时间，单位是秒）
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param timeoutSeconds
     *            过期时间，单位秒
     */
    void put(String key, T value, Long timeoutSeconds);

    /**
     * 通过缓存key获取缓存
     *
     * @param key
     *            键
     * @return 值
     */
    Object get(String key);

    /**
     * 删除缓存
     *
     * @param key
     *            键，多个
     */
    void remove(String... key);

    /**
     * 获得缓存的所有key列表（不带common prefix的）
     *
     * @return key列表
     */
    Collection<String> getAllKeys();

    /**
     * 获得缓存的所有值列表
     *
     * @return 值列表
     * @author stylefeng
     * @date 2020/7/8 22:11
     */
    Collection<T> getAllValues();

    /**
     * 获取所有的key，value
     *
     * @return 键值map
     */
    Map<String, T> getAllKeyValues();

    /**
     * 通用缓存的前缀，用于区分不同业务
     * <p>
     * 如果带了前缀，所有的缓存在添加的时候，key都会带上这个前缀
     *
     * @return 缓存前缀
     */
    String getCommonKeyPrefix();

}
