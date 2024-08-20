package com.example.woogisfree.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String username, String refreshToken) {
        redisTemplate.opsForValue().set(username, refreshToken);
        redisTemplate.expire(username, Duration.ofDays(30));
    }

    @Override
    public String find(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Override
    public void delete(String username) {
        redisTemplate.delete(username);
    }
}
