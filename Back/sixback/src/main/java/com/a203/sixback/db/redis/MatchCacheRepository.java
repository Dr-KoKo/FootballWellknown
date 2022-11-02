package com.a203.sixback.db.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MatchCacheRepository {

    private final RedisTemplate<String, String> matchRedisTemplate;

    private final static Duration MATCH_CACHE_TTL = Duration.ofDays(2);

    public void setMatch(long key, long value){
        String _key = getKey(String.valueOf(key));
        String _value = String.valueOf(value);

        log.info("Set Match to Redis {}:{}", _key, value);

        matchRedisTemplate.opsForValue().set(_key, _value, MATCH_CACHE_TTL);
    }

    public String getMatch(long key){
        String _key = getKey(String.valueOf(key));
        String value = matchRedisTemplate.opsForValue().get(_key);

        log.info("Get data from Redis {}:{}", _key, value);

        return value;
    }

    public String getAndDeleteMatch(long key){
        String _key = getKey(String.valueOf(key));
        String value = matchRedisTemplate.opsForValue().getAndDelete(_key);

        log.info("Get data from Redis {}:{}", _key, value);

        return value;
    }

    private String getKey(String key){
        return "MATCH:"+key;
    }

}
