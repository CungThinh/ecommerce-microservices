package com.cungthinh.productservice.middleware;

import com.cungthinh.productservice.dto.response.ApiResponse;
import com.cungthinh.productservice.dto.response.ProductResponseV2;
import com.cungthinh.productservice.service.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@Slf4j
public class ProductCacheInterceptor implements HandlerInterceptor {

    private final CacheService cacheService;
    private final ObjectMapper objectMapper;

    public ProductCacheInterceptor(CacheService cacheService, ObjectMapper objectMapper) {
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        String uri = request.getRequestURI();
        String productId = uri.substring(uri.lastIndexOf("/") + 1);
        String cacheKey = "spu-" + productId;

        ProductResponseV2 cached = (ProductResponseV2) cacheService.get(cacheKey);
        if (cached != null) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);


            ApiResponse<Object> apiResponse = ApiResponse.success(cached, "Get SPU info successfully");
            String jsonResponse = objectMapper.writeValueAsString(apiResponse);

            response.getWriter().write(jsonResponse);
            return false;
        }
        log.info("Cache miss for product: {}", productId);
        return true;
    }
}
