package com.kavin.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisFactoryConfig {

    private static final Logger log = LoggerFactory.getLogger(RedisFactoryConfig.class);


    @Autowired
    private ClusterProperties clusterProperties;

    /**
     * 使用@value注解读取配置文件指定key一直获取不到。
     * 原因：可能是yml里面的文件没有加了缩进。手动打一遍，从网上复制会出现此问题。
     */


    @Value("${spring.redis.timeout}")
    private String timeout;


    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;


    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWait;



    @Bean  //因为这是一个配置类。所以下面的方法返回的genericObjectPoolConfig会自动注入进来。
    public RedisConnectionFactory myLettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {

        log.error("连接池信息："+genericObjectPoolConfig);

        System.err.println(clusterProperties.getNodes());
        System.err.println(timeout);
        System.err.println(clusterProperties.getMax_redirects());

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterProperties.getNodes());
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", clusterProperties.getMax_redirects());

        //配置lettuce 连接池,通过查看获取的redisTemplate.getConnectionFactory()获取的对象查看poolConfig属性是否存在。
        // 则证明连接池是否配置成功
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(6000))
                .poolConfig(genericObjectPoolConfig)
                .build();

        //设置redis集群配置。
        RedisClusterConfiguration redisClusterConfiguration
                = new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));

        return new LettuceConnectionFactory(redisClusterConfiguration,clientConfig);
    }

    //设置连接池对象属性
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        return genericObjectPoolConfig;
    }


}
