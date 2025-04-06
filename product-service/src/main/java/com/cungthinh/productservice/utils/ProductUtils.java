package com.cungthinh.productservice.utils;

import java.security.SecureRandom;

public class ProductUtils {
    public static String generateProductId() {
        long timestamp = System.currentTimeMillis();
        int random = new SecureRandom().nextInt(900000) + 100000; // 6-digit random number
        return "P" + timestamp + random;
    }
}
