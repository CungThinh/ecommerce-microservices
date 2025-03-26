package com.cungthinh.notificationservices.enums;

public enum NotificationType {
    ORDER_CONFIRMATION, // For order placement success
    ORDER_SHIPPED, // When the order is shipped
    ORDER_DELIVERED, // When the order is delivered
    PRODUCT_LAUNCH, // New product announcements
    PROMOTIONAL, // Discounts, sales, and offers
    CART_REMINDER, // Reminders for abandoned carts
    ACCOUNT_SECURITY, // Password changes, login alerts
    SYSTEM_ALERT, // Maintenance, downtime updates
    NEW_ACCOUNT_CREATED, // When a new account is created
}
