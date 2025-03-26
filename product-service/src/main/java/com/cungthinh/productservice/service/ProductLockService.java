package com.cungthinh.productservice.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductLockService {
    private final RedisTemplate<String, String> redisTemplate;
    private final InventoryService inventoryService;
    private static final String LOCK_KEY = "lock_product_";
    private static final long LOCK_TIMEOUT = 3000;
    private static final int RETRY_TIME = 10;

    public ProductLockService(RedisTemplate<String, String> redisTemplate, InventoryService inventoryService) {
        this.redisTemplate = redisTemplate;
        this.inventoryService = inventoryService;
    }

    public String acquireLock(String productId, String cartId, int quantity) {
        String key = LOCK_KEY + productId;
        for (int i = 0; i < RETRY_TIME; i++) {
            Boolean success =
                    redisTemplate.opsForValue().setIfAbsent(key, "locked", LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
            if (Boolean.TRUE.equals(success)) {
                boolean isReserved = inventoryService.reserveInventory(productId, quantity, cartId);
                if (isReserved) {
                    redisTemplate.expire(key, LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
                    return key;
                } else {
                    return null;
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }

    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
