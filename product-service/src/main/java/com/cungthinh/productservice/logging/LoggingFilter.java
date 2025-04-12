package com.cungthinh.productservice.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("clientIp", request.getRemoteAddr());
            MDC.put("uri", request.getRequestURI());

            filterChain.doFilter(request, response);
        } finally {
            MDC.clear(); // Always clear MDC after request
        }
    }
}
