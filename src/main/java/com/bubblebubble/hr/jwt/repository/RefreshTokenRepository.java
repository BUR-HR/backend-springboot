package com.bubblebubble.hr.jwt.repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.bubblebubble.hr.jwt.entity.RefreshToken;

@Repository
public class RefreshTokenRepository {
    private RedisTemplate<String, Integer> redisTemplate;

    public RefreshTokenRepository(final RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(final RefreshToken refreshToken, Long expireTime) {
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getRefreshToken(), refreshToken.getEmpNo());
        redisTemplate.expire(refreshToken.getRefreshToken(), expireTime, TimeUnit.MILLISECONDS);
    }

    public Optional<RefreshToken> findById(final String refreshToken) {
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        Integer empNo = valueOperations.get(refreshToken);

        if (Objects.isNull(empNo)) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(refreshToken, empNo));
    }
}