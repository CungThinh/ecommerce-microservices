package com.cungthinh.notificationservices.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cungthinh.notificationservices.enums.NotificationStatus;
import com.cungthinh.notificationservices.enums.NotificationType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Entity
@Table(name = "notifications")
@DynamicInsert
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {
    @Id
    String id;

    String userId;
    String message;

    NotificationType type;

    @Builder.Default
    NotificationStatus status = NotificationStatus.PENDING;

    @Builder.Default
    @Column(columnDefinition = "boolean default false")
    Boolean isRead = false;
}
