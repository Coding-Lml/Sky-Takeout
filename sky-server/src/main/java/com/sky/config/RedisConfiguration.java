package com.sky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 创建 JSON 序列化器
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 1. 设置 Key 的序列化器 (保持不变，使用 String)
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 2. 【关键修复】设置 Value 的序列化器为 JSON 
        redisTemplate.setValueSerializer(jsonRedisSerializer);

        // 3. 【建议设置】设置 Hash Key 和 Hash Value 的序列化器，防止 Hash 类型数据乱码
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);

        // 确保所有属性设置完毕
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}