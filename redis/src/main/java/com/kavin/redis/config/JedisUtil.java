package com.kavin.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JedisUtil {
    private static final Logger log = LoggerFactory.getLogger(JedisUtil.class);
    /**
     * redisTemplate.opsForValue();//操作字符串
     *
     * redisTemplate.opsForHash();//操作hash
     *
     * redisTemplate.opsForList();//操作list
     *
     * redisTemplate.opsForSet();//操作set
     *
     * redisTemplate.opsForZSet();//操作有序set
     */
    @Autowired
    private RedisTemplate redisTemplate;


    public Object get(String Key) {
        return redisTemplate.opsForValue().get(Key);
    }

    public void set(String Key, Object Value) {
        try {
            RedisConnectionFactory conn = redisTemplate.getConnectionFactory();
            redisTemplate.opsForValue().set(Key,Value);
        } catch (Exception ex) {
            log.error("setToRedis:{Key:" + Key + ",value" + Value + "}", ex);
        }
    }
}
