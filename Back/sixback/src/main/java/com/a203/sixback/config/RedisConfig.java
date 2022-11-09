package com.a203.sixback.config;

import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.redis.RedisSubService;
import com.a203.sixback.socket.ChatMessage;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;
    private final RedisSubService redisSubService;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisURI redisURI = RedisURI.create(redisProperties.getUrl());

        RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisURI);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);

        return factory;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(redisSubService);
    }

    //컨테이너 설정
    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());
        return container;
    }

    //pub/sub 토픽 설정
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("chat");
    }

    @Bean
    public RedisTemplate<String, Object> chatRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<User>(User.class));

        return redisTemplate;
    }

    @Bean(name = "matchRedisTemplate")
    public RedisTemplate<String, String> matchRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<String>(String.class));

        return redisTemplate;
    }

    @Bean(name = "rankRedisTemplate")
    public RedisTemplate<String, String> rankRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<String>(String.class));

        return redisTemplate;
    }

}
