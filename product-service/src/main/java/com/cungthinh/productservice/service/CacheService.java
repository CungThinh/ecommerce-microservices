package com.cungthinh.productservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void putWithExpire(String key, Object value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlSeconds, TimeUnit.SECONDS);
    }

    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}