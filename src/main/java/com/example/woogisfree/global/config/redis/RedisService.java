package com.example.woogisfree.global.config.redis;

public interface RedisService {

    void save(String username, String refreshToken, long expirationTimeInMilliseconds);

    String find(String username);

    void delete(String username);
}
