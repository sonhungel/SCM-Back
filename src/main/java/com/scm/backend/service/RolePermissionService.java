package com.scm.backend.service;

import com.scm.backend.model.dto.RolePermissionDto;
import com.scm.backend.model.entity.RolePermission;

public interface RolePermissionService {
    void createRolePermission(RolePermissionDto rolePermission) throws Exception;
}
