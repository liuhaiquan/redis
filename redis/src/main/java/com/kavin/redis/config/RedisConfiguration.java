package com.kavin.redis.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.io.Serializable;

@Configuration
public class RedisConfiguration {


    @Resource
    private LettuceConnectionFactory myLettuceConnectionFactory;

    /**
     * 使用自定义redisTemplate
     *
     * 方法名必须叫redisTemplate，这样才能覆盖内置的redisTemplate，否则还是使用系统内置的redisTemplate
     * @return
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate() {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(myLettuceConnectionFactory);
        return template;
    }

}
