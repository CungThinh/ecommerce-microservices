package com.cungthinh.productservice.event.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cungthinh.productservice.dto.request.ReserveProductRequest;
import com.cungthinh.productservice.service.CartService;
import com.cungthinh.productservice.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderCreatedListener {

    private final CartService cartService;
    private final ProductService productService;

    public OrderCreatedListener(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void handleOrderCreatedEvent(ReserveProductRequest message) {
        cartService.clearCart(message.getCartId());
        productService.reserveProduct(message);
        log.info("Order created event handled");
    }
}
