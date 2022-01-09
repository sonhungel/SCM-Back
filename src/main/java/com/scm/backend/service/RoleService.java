package com.scm.backend.service;

import com.scm.backend.model.entity.Role;

import java.util.List;

public interface RoleService {
    void createRole(Role role);

    List<Role> getAllRole();
}
