package com.rosenzest.base.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.lang.NonNull;

import com.rosenzest.base.constant.Status;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 带泛型 redisTemplate封装
 */
@Slf4j
@SuppressWarnings(value = {"unchecked", "rawtypes"})
public final class RedisUtil {

    @Setter
    @NonNull
    private static RedisTemplate redisTemplate;

    @Getter
    private static RedisUtil redisUtil = new RedisUtil();

    private RedisUtil() {}

    /**
     * 指定缓存失效时间
     *
     * @param key
     *            键
     * @param timeout
     *            时间(秒)
     * @return
     */
    public static boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public static boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置过期时间，同时设置的时间会自动加上最大/小偏移量之间的随机秒数
     */
    public static void expire(String key, int seconds, int minOffset, int maxOffset) {
        int[] array = new int[] {Math.abs(minOffset), Math.abs(maxOffset)};
        seconds += RandomUtil.randomInt(NumberUtil.min(array), NumberUtil.max(array));
        expire(key, seconds);
    }

    /**
     * 在原有设置的秒数上加上 1~9 之间的一个随机整数作为新的过期时间
     */
    public static void expireOfSecondOffset(String key, int seconds) {
        seconds += RandomUtil.randomInt(1, 10);
        expire(key, seconds);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key
     *            键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key
     *            键
     * @return true 存在 false不存在
     */
    public static boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key
     *            可以传一个值 或多个
     */
    public static Long del(String... key) {
        if (key != null && key.length > 0) {
            return redisTemplate.delete(Arrays.asList(key));
        }
        return 0L;
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key
     *            键
     * @return 值
     */
    public static <T> T get(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return true成功 false失败
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static <T> void set(String key, T value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 递增
     *
     * @param key
     *            键
     * @param delta
     *            要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     *            键
     * @param delta
     *            要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key
     *            键 不能为null
     * @param item
     *            项 不能为null
     * @return 值
     */
    public static <T> T hget(String key, String item) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key
     *            键 不能为null
     * @param item
     *            项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hexists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key
     *            Redis键
     * @param hKeys
     *            Hash键集合
     * @return Hash对象集合
     */
    public static <T> List<T> hgets(final String key, final Collection<String> items) {
        return redisTemplate.opsForHash().multiGet(key, items);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key
     *            键
     * @return 对应的多个键值
     */
    public static <T> Map<String, T> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hash 键数量
     *
     * @param key
     *            key
     * @return int
     */
    public static int hsize(String key) {
        return redisTemplate.opsForHash().size(key).intValue();
    }

    public static <T> List<T> hvals(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    /**
     * HashSet
     *
     * @param key
     *            键
     * @param map
     *            对应多个键值
     * @return true 成功 false 失败
     */
    public static <T> void hmset(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key
     *            键
     * @param map
     *            对应多个键值
     * @param time
     *            时间(秒)
     * @return true成功 false失败
     */
    public static <T> void hmset(String key, Map<String, T> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key
     *            键
     * @param item
     *            项
     * @param value
     *            值
     * @return true 成功 false失败
     */
    public static <T> void hset(String key, String item, T value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key
     *            键
     * @param item
     *            项
     * @param value
     *            值
     * @param time
     *            时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static <T> void hset(String key, String item, T value, long time) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    public static <T> boolean hsetnx(String key, String item, T value) {
        return redisTemplate.opsForHash().putIfAbsent(key, item, value);
    }

    /**
     * 删除hash表中的值
     *
     * @param key
     *            键 不能为null
     * @param item
     *            项 可以使多个 不能为null
     */
    public static Long hdel(String key, Object... item) {
        return redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key
     *            键
     * @param item
     *            项
     * @param by
     *            要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key
     *            键
     * @param item
     *            项
     * @param by
     *            要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key
     *            键
     * @return
     */
    public static <T> Set<T> sGet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key
     *            键
     * @param values
     *            值 可以是多个
     * @return 成功个数
     */
    public static long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key
     *            键
     * @param time
     *            时间(秒)
     * @param values
     *            值 可以是多个
     * @return 成功个数
     */
    public static long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key
     *            键
     * @return
     */
    public static long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key
     *            键
     * @param values
     *            值 可以是多个
     * @return 移除的个数
     */
    public static long sRemove(String key, Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count;
    }

    public static <T> T spop(String key) {
        SetOperations<String, T> opsForSet = redisTemplate.opsForSet();
        return opsForSet.pop(key);
    }

    public static <T> T srand(String key) {
        SetOperations<String, T> opsForSet = redisTemplate.opsForSet();
        return opsForSet.randomMember(key);
    }

    public static <T> Set<T> sinter(String key, Collection<String> otherKeys) {
        SetOperations<String, T> opsForSet = redisTemplate.opsForSet();
        return opsForSet.intersect(key, otherKeys);
    }

    public static <T> Set<T> sinter(String key, String otherKey) {
        SetOperations<String, T> opsForSet = redisTemplate.opsForSet();
        return opsForSet.intersect(key, otherKey);
    }

    public static Long sinterAndStore(String key, String otherKey, String destinationKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destinationKey);
    }

    public static Long sinterAndStore(String key, Collection<String> otherKeys, String destinationKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destinationKey);
    }

    public static <T> Set<T> sunion(String key, String otherKey) {
        return redisTemplate.opsForSet().union(key, otherKey);
    }

    public static <T> Set<T> sunion(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().union(key, otherKeys);
    }

    public static Long sunionAndStore(String key, String otherKey, String destinationKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destinationKey);
    }

    public static Long sunionAndStore(String key, Collection<String> otherKeys, String destinationKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destinationKey);
    }

    public static <T> Set<T> sdiff(String key, String otherKey) {
        return redisTemplate.opsForSet().difference(key, otherKey);
    }

    public static <T> Set<T> sdiff(String key, Collection<String> otherKeys) {
        return redisTemplate.opsForSet().difference(key, otherKeys);
    }

    public static Long sdiffAndStore(String key, String otherKey, String destinationKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destinationKey);
    }

    public static Long sdiffAndStore(String key, Collection<String> otherKeys, String destinationKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destinationKey);
    }

    public static <T> boolean zadd(String key, T value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public static <T> Long zadd(String key, Set<TypedTuple<T>> tuples) {
        return redisTemplate.opsForZSet().add(key, tuples);
    }

    public static <T> Double zscore(String key, T value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public static <T> Double zincrby(String key, T value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    public static Long zcard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public static Long zcount(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().count(key, minScore, maxScore);
    }

    public static <T> Set<T> zrange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public static <T> Set<T> zrevRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public static <T> Set<T> zrangeByScore(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(key, minScore, maxScore);
    }

    public static <T> Set<T> zrevRangeByScore(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, minScore, maxScore);
    }

    public static <T> Long zrank(String key, T value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public static <T> Long zrevRank(String key, T value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public static <T> Long zrem(String key, T... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    public static Long zremRangeByRank(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    public static Long zremRangeByScore(String key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, minScore, maxScore);
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key
     *            键
     * @param start
     *            开始
     * @param end
     *            结束 0 到 -1代表所有值
     * @return
     */
    public static <T> List<T> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key
     *            键
     * @return
     */
    public static long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key
     *            键
     * @param index
     *            索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static <T> T lIndex(String key, long index) {
        ListOperations<String, T> opsForList = redisTemplate.opsForList();
        return opsForList.index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static <T> Long rpush(String key, T value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    public static <T> Long lpush(String key, T value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒)
     * @return
     */
    public static <T> Long rpush(String key, T value, long time) {
        Long lon = redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return lon;
    }

    public static <T> Long lpush(String key, T value, long time) {
        Long lon = redisTemplate.opsForList().leftPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return lon;
    }

    /**
     * 将list放入缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return
     */
    public static <T> Long rpush(String key, List<T> value) {
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    public static <T> Long lpush(String key, List<T> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒)
     * @return
     */
    public static <T> Long rpush(String key, List<T> value, long time) {
        Long lon = redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return lon;
    }

    public static <T> Long lpush(String key, List<T> value, long time) {
        Long lon = redisTemplate.opsForList().leftPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return lon;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key
     *            键
     * @param index
     *            索引
     * @param value
     *            值
     * @return
     */
    public static <T> void lSet(String key, long index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key
     *            键
     * @param count
     *            移除多少个
     * @param value
     *            值
     * @return 移除的个数
     */
    public static <T> Long lRemove(String key, long count, T value) {
        Long remove = redisTemplate.opsForList().remove(key, count, value);
        return remove;
    }

    public static <T> T blpop(String key, long timeout, TimeUnit unit) {
        ListOperations<String, T> opsForList = redisTemplate.opsForList();
        return opsForList.leftPop(key, timeout, unit);
    }

    public static <T> T brpop(String key, long timeout, TimeUnit unit) {
        ListOperations<String, T> opsForList = redisTemplate.opsForList();
        return opsForList.leftPop(key, timeout, unit);
    }

    public static <T> T lpop(String key) {
        ListOperations<String, T> opsForList = redisTemplate.opsForList();
        return opsForList.leftPop(key);
    }

    public static <T> T rpop(String key) {
        ListOperations<String, T> opsForList = redisTemplate.opsForList();
        return opsForList.rightPop(key);
    }

    /**
     * 模糊查询获取key值
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 使用Redis的消息队列
     *
     * @param channel
     * @param message
     *            消息内容
     */
    public static <T> void convertAndSend(String channel, T message) {
        redisTemplate.convertAndSend(channel, message);
    }

    // =========BoundListOperations 用法 start============

    /**
     * 将数据添加到Redis的list中（从右边添加）
     *
     * @param listKey
     * @param expireEnum
     *            有效期的枚举类
     * @param values
     *            待添加的数据
     */
    public static <T> void addToListRight(String listKey, Status.ExpireEnum expireEnum, T... values) {
        // 绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        // 插入数据
        boundValueOperations.rightPushAll(values);
        // 设置过期时间
        boundValueOperations.expire(expireEnum.getTime(), expireEnum.getTimeUnit());
    }

    public static <T> void addToListRight(String listKey, T... values) {
        // 绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        // 插入数据
        boundValueOperations.rightPushAll(values);
    }

    /**
     * 根据起始结束序号遍历Redis中的list
     *
     * @param listKey
     * @param start
     *            起始序号
     * @param end
     *            结束序号
     * @return
     */
    public static <T> List<T> rangeList(String listKey, long start, long end) {
        // 绑定操作
        BoundListOperations<String, T> boundValueOperations = redisTemplate.boundListOps(listKey);
        // 查询数据
        return boundValueOperations.range(start, end);
    }

    /**
     * 弹出右边的值 --- 并且移除这个值
     *
     * @param listKey
     */
    public static <T> T rifhtPop(String listKey) {
        BoundListOperations<String, T> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }

    public static <T> T leftPop(String listKey) {
        BoundListOperations<String, T> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.leftPop();
    }

    // =========BoundListOperations 用法 End============

    // ========================================

    /**
     * 往队列里增加数据
     * 
     * @param <T>
     * @param key
     * @param message
     * @return
     */
    public static <T> RecordId streamAdd(String key, T message) {
        return streamAdd(key, message, new Jackson2HashMapper(true));
    }

    public static <T, K, HK, HV> RecordId streamAdd(String key, T message, HashMapper<K, HK, HV> hashMapper) {

        StreamOperations<String, String, T> opsForStream = redisTemplate.opsForStream(hashMapper);
        ObjectRecord<String, T> record = StreamRecords.objectBacked(message).withStreamKey(key);
        return opsForStream.add(record);
    }

    /**
     * 创建组
     * 
     * @param <T>
     * @param key
     * @param groupName
     * @return
     */
    public static <T> String createGroup(String key, String groupName) {
        StreamOperations<String, String, T> opsForStream = redisTemplate.opsForStream();
        return opsForStream.createGroup(key, groupName);
    }

    // ========================================
}
