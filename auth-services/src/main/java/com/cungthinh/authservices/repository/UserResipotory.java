package com.cungthinh.authservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.cungthinh.authservices.entity.UserEntity;

public interface UserResipotory extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT u.id FROM UserEntity u")
    List<String> getAllUserId();
}
