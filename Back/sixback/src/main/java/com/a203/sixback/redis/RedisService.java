package com.a203.sixback.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Get String Type
     * @param key
     */
    public String getStringValue(String key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * Set String Value And Expire Time
     * @param key user.id
     * @param token refreshToken
     * @param expireDate expire date
     */
    public void setStringValueAndExpire(String key, String token, long expireDate) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(key, token, expireDate, TimeUnit.MILLISECONDS);
    }

    public void setStringValue(long key, int value) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(String.valueOf(key), String.valueOf(value));
    }

    public void getAndDelete(long key) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.getAndDelete(String.valueOf(key));
    }
}
