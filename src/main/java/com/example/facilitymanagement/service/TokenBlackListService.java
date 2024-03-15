package com.example.facilitymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlackListService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public TokenBlackListService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * トークンをブラックリストに追加します。
     *
     * @param token          トークン
     * @param expirationTime トークンの有効期限（秒）
     */
    public void addToBlacklist(String token, long expirationTime) {
        redisTemplate.opsForValue().set(token, "blackListed", expirationTime,
                TimeUnit.SECONDS);
    }

    /**
     * トークンがブラックリストに含まれているか確認します。
     *
     * @param token トークン
     * @return ブラックリストに含まれていればtrue、そうでなければfalse
     */
    public boolean isTokenBlackListed(String token) {
        return redisTemplate.hasKey(token);
    }
}
