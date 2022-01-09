package com.scm.backend.util;

import com.scm.backend.model.dto.RoleDto;
import com.scm.backend.model.entity.Role;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface RoleDtoMapper {
    List<RoleDto> toRoleDtoList(List<Role> roleList);
}
