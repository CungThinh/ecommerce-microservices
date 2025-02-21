package com.cungthinh.authservices.mapper;

import com.cungthinh.authservices.dto.request.ProfileCreationRequest;
import com.cungthinh.authservices.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
