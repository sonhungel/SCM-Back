package com.scm.backend.service.impl;

import com.scm.backend.model.entity.Permission;
import com.scm.backend.repository.PermissionRepository;
import com.scm.backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void createPermission(Permission permission) {
        permission.setAddedDate(LocalDate.now());
        permissionRepository.saveAndFlush(permission);
    }
}
