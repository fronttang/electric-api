package com.rosenzest.base.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.rosenzest.base.util.RedisUtil;

/**
 * redis配置类
 * 
 * @author fronttang
 * @date 2021/07/23
 */
public class RedisConfiguration  {

    @Bean
    public <V> RedisUtil redisUtil(RedisTemplate<String, V> redisTemplate) {
        RedisUtil.setRedisTemplate(redisTemplate);
        return RedisUtil.getRedisUtil();
    }

    // 缓存管理器
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration redisCacheConfiguration =
            RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
            .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * retemplate相关配置 使redis支持插入对象
     *
     * @param factory
     * @return 方法缓存 Methods the cache
     */
    @Bean
    public <V> RedisTemplate<String, V> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, V> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(factory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        // Jackson2JsonRedisSerializer<Object> jacksonSerial = new Jackson2JsonRedisSerializer<>(Object.class);

        // ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        // objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        // objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
        // ObjectMapper.DefaultTyping.NON_FINAL,
        // JsonTypeInfo.As.PROPERTY);
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // jacksonSerial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 对hash类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public <V> HashOperations<String, String, V> hashOperations(RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    /**
     * 对redis字符串类型数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public <V> ValueOperations<String, V> valueOperations(RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    /**
     * 对链表类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public <V> ListOperations<String, V> listOperations(RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForList();
    }

    /**
     * 对无序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public <V> SetOperations<String, V> setOperations(RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    /**
     * 对有序集合类型的数据操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public <V> ZSetOperations<String, V> zSetOperations(RedisTemplate<String, V> redisTemplate) {

        return redisTemplate.opsForZSet();
    }

    /**
     * 消息队列类型的操作
     * 
     * @param <V>
     * @param redisTemplate
     * @return
     */
    @Bean
    public <V> StreamOperations<String, String, V> streamOperations(RedisTemplate<String, V> redisTemplate) {
        return redisTemplate.opsForStream(new Jackson2HashMapper(true));
    }

}
