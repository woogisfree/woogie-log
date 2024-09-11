package com.example.woogisfree.global.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import org.webjars.NotFoundException;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String username, String refreshToken, long expirationTimeInMilliseconds) {
        redisTemplate.opsForValue().set(username, refreshToken, expirationTimeInMilliseconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public String find(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Override
    public void delete(String username) {
        String refreshToken = redisTemplate.opsForValue().get(username);
        if (StringUtils.isEmpty(refreshToken)) throw new NotFoundException("No RefreshToken exists for " + username);

        redisTemplate.delete(username);
    }
}
