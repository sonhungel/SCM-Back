package com.scm.backend.service;

import com.scm.backend.model.entity.Permission;

import java.util.List;

public interface PermissionService {
    void createPermission(Permission permission);

    List<Permission> getAllPermission();
}
