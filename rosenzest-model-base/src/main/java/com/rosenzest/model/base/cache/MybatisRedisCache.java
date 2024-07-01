package com.rosenzest.model.base.cache;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import com.rosenzest.base.util.RedisUtil;

import lombok.Setter;

/**
 * mybatis redis 缓存
 * 
 * @author fronttang
 * @date 2021/07/23
 */
public class MybatisRedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 缓存刷新时间（秒）
     */
    @Setter
    private long flushInterval = 86400L;

    private String id;

    public MybatisRedisCache() {}

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object item, Object value) {
        RedisUtil.hset(getId(), item.toString(), value);

        if (flushInterval > 0L) {
            RedisUtil.expire(getId(), flushInterval);
        }
    }

    @Override
    public Object getObject(Object item) {
        return RedisUtil.hget(getId(), item.toString());
    }

    @Override
    public Object removeObject(Object item) {
        RedisUtil.hdel(getId(), item);
        return item;
    }

    @Override
    public void clear() {
        RedisUtil.del(getId());
    }

    @Override
    public int getSize() {
        return RedisUtil.hsize(getId());
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}
