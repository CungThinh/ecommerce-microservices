package com.cungthinh.authservices.mapper;

import org.mapstruct.Mapper;

import com.cungthinh.authservices.dto.request.ProfileCreationRequest;
import com.cungthinh.authservices.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
