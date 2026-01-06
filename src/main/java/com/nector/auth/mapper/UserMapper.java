package com.nector.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.nector.auth.dto.request.RegisterRequest;
import com.nector.auth.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)       // encrypt karke service me set hoga
    @Mapping(target = "passwordAlgorithm", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    User toEntity(RegisterRequest registerRequest);
}
