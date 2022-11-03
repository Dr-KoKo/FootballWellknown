package com.a203.sixback.db.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatCacheRepository {

    private final RedisTemplate<String, Long> chatRedisTemplate;

    private final static Duration CHAT_CACHE_TTL = Duration.ofDays(2);

    public void setChat(String key, Long value){
        String _key = getKey(key);

        log.info("Set Chat to Redis {}:{}", _key, value);

        chatRedisTemplate.opsForValue().set(_key, value, CHAT_CACHE_TTL);
    }

    public String getChat(String key){
        String _key = getKey(key);
        String value = String.valueOf(chatRedisTemplate.opsForValue().get(_key));

        log.info("Get data from Redis {}:{}", _key, value);

        return value;
    }

    public void plusUserCount(String key) {
        String _key = getKey(key);

        log.info("Increment Value from redis {}", _key);

        chatRedisTemplate.opsForValue().increment(_key);
    }

    public void minusUserCount(String key) {
        String _key = getKey(key);

        log.info("Decrement Value from redis {}", _key);

        chatRedisTemplate.opsForValue().decrement(_key);
    }

    public Long getAndDeleteChat(String key){
        String _key = getKey(key);
        Long value = chatRedisTemplate.opsForValue().getAndDelete(_key);

        log.info("Get data from Redis {}:{}", _key, value);

        return value;
    }

    private String getKey(String key){
        return "CHAT:"+key;
    }
}
