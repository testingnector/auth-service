package com.nector.auth.mapper;

import com.nector.auth.dto.request.RoleRequest;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toEntity(RoleRequest roleRequest);
}