package com.cungthinh.authservices.entity;

import java.util.Set;

import jakarta.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    String email;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    String password;

    @Column(columnDefinition = "varchar(255)")
    Boolean enabled;

    @ManyToMany
    Set<Role> roles;
}
