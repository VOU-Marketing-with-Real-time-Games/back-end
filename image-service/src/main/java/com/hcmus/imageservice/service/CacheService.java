package com.hcmus.imageservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void saveObject(String key, Object object) {
        redisTemplate.opsForValue().set(key, object);
    }

    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
