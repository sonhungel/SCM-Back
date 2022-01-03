package com.scm.backend.util;

import com.scm.backend.model.dto.PermissionDto;
import com.scm.backend.model.entity.Permission;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface PermissionDtoMapper {
    PermissionDto toPermissionDto(Permission permission);
    Permission toPermission(PermissionDto permissionDto);
}
