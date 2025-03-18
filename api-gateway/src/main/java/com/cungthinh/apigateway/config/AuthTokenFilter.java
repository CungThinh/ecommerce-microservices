package com.cungthinh.apigateway.config;

import com.cungthinh.apigateway.dto.request.IntrospectRequest;
import com.cungthinh.apigateway.dto.response.IntrospectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthTokenFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private WebClient.Builder webClientBuilder;

    private static final List<String> publicEndpoints = List.of(
            "/auth/login",
            "/auth/logout"
    );


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        
        // Skip authentication for public endpoints
        if (isPublicEndpoint(path)) {
            log.info("Skipping authentication for path: {}", path);
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthenticated(exchange.getResponse());
        }

        String token = authHeader.substring(7);

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/api/v1/auth/introspect")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new IntrospectRequest(token))
                .retrieve()
                .bodyToMono(IntrospectResponse.class)
                .flatMap(response -> {
                    if(response.isSuccess()) {
                        log.info("Token is valid");
                        return chain.filter(exchange);
                    }
                    else {
                        log.info("Token is invalid");
                        return unauthenticated(exchange.getResponse());
                    }
                })
                .onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(String path) {
        return publicEndpoints.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
