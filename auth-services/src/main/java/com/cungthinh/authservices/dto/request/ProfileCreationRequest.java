package com.cungthinh.authservices.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCreationRequest {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("full_name")
    private String fullName;

    private String phone;
    private AddressDTO address;
}
