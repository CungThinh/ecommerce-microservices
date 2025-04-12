package com.cungthinh.productservice.config;

import com.cungthinh.productservice.middleware.ProductCacheInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ProductCacheInterceptor productCacheInterceptor;

    public WebConfig(ProductCacheInterceptor productCacheInterceptor) {
        this.productCacheInterceptor = productCacheInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productCacheInterceptor)
                .addPathPatterns("/api/v2/products/**");
    }
}
