package com.scm.backend.service.impl;

import com.scm.backend.model.entity.Role;
import com.scm.backend.repository.RoleRepository;
import com.scm.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void createRole(Role role) {
        role.setAddedDate(LocalDate.now());
        roleRepository.saveAndFlush(role);
    }
}
