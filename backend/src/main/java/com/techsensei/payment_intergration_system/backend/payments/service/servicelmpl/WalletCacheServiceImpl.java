package com.techsensei.payment_intergration_system.backend.payments.service.servicelmpl;

import com.techsensei.payment_intergration_system.backend.payments.service.WalletCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletCacheServiceImpl implements WalletCacheService {

    private final RedisTemplate<String,Object> redisTemplate;

    private static final String PREFIX = "wallet_balance:";
    @Override
    public void cacheBalance(Long userId, BigDecimal balance) {
        redisTemplate.opsForValue().set(PREFIX + userId, balance,Duration.ofMinutes(30));
    }

    @Override
    public BigDecimal getBalance(Long userId) {

        Object value = redisTemplate.opsForValue().get(PREFIX + userId);

        if(value == null){return null;}

        return new BigDecimal(value.toString());
    }
}
