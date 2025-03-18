package com.cungthinh.authservices.service;

import com.cungthinh.authservices.exception.CustomException;
import com.cungthinh.authservices.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.cungthinh.authservices.utils.SHA256Hashing.hashWithSHA256;

@Service
@Slf4j
public class OtpService {
    private RedisTemplate<String, String> redisTemplate;

    public OtpService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateOtp(String key) {
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(900000));
        String hashedKey = hashWithSHA256(key);
        redisTemplate.opsForValue().set(hashedKey, otp, 30, TimeUnit.MINUTES);

        log.info("OTP: {}", otp);

        return otp;
    }

    public void validateOtp(String key, String otp) {
        String hashedKey = hashWithSHA256(key);
        String value = redisTemplate.opsForValue().get(hashedKey);

        if(value == null) {
            throw new CustomException(ErrorCode.OTP_EXPIRED);
        }

        if (!value.equals(otp)) {
            throw new CustomException(ErrorCode.OTP_INVALID);
        }
    }
}
