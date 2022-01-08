package com.scm.backend.service.impl;

import com.scm.backend.model.dto.RolePermissionDto;
import com.scm.backend.model.entity.Permission;
import com.scm.backend.model.entity.Role;
import com.scm.backend.model.entity.RolePermission;
import com.scm.backend.model.entity.RolePermissionKey;
import com.scm.backend.repository.PermissionRepository;
import com.scm.backend.repository.RolePermissionRepository;
import com.scm.backend.repository.RoleRepository;
import com.scm.backend.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void createRolePermission(RolePermissionDto rolePermissionDto) throws Exception {
        Role role = roleRepository.findById(rolePermissionDto.getKey().getRole().getId())
                .orElseThrow(() -> new Exception("Role ID does not exist"));
        Permission permission = permissionRepository.findById(rolePermissionDto.getKey().getPermission().getId())
                .orElseThrow(() -> new Exception("Permission ID does not exist"));

        RolePermissionKey key = RolePermissionKey.builder()
                .role(role)
                .permission(permission)
                .build()
                ;

        RolePermission rolePermission = RolePermission.builder()
                .key(key)
                .addedDate(LocalDate.now())
                .build()
                ;

        rolePermissionRepository.saveAndFlush(rolePermission);
    }

}
