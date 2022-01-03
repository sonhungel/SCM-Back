package com.scm.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolePermissionKeyDto {
    private RoleDto role;
    private PermissionDto permission;
}
