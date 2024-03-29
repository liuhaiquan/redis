package com.kavin.redis.test;

import com.kavin.redis.config.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class test01 {

    @Autowired
    private JedisUtil jedisUtil;

    @Test
    public void test01(){
            jedisUtil.set("key2","value2");

    }

    @Test
    public void test02(){
        System.err.println(jedisUtil.get("key2"));
    }

}
