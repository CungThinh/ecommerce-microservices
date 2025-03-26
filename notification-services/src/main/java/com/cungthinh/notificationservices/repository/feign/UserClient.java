package com.cungthinh.notificationservices.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.cungthinh.notificationservices.dto.response.GetListUserIdsResponse;

@FeignClient(name = "user-service", url = "${spring.application.user-services-url}")
public interface UserClient {
    @PostMapping(value = "/api/v1/users/get-a", produces = MediaType.APPLICATION_JSON_VALUE)
    GetListUserIdsResponse getAllUserId();
}
