package com.cungthinh.productservice.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(2001, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_OUT_OF_STOCK(2002, "Product out of stock", HttpStatus.BAD_REQUEST),
    INVENTORY_NOT_FOUND(2003, "Inventory not found", HttpStatus.NOT_FOUND),
    INVENTORY_OUT_OF_STOCK(2004, "Inventory out of stock", HttpStatus.BAD_REQUEST),
    CART_NOT_FOUND(2005, "Cart not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(2006, "Cart item not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
