package com.cungthinh.authservices.repository.feign;

import com.cungthinh.authservices.dto.request.ProfileCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="profile-service", url = "${spring.application.profile-services-url}")
public interface ProfileClient {
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    Object createProfile(@RequestBody ProfileCreationRequest request);
}
