package com.scm.backend.service;

import com.scm.backend.model.dto.UserInvoiceDto;
import com.scm.backend.model.dto.UserRoleDto;
import com.scm.backend.model.entity.Permission;
import com.scm.backend.model.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    void createUserRole(UserRoleDto userRole) throws Exception;
    List<Permission> getUserPermission(UserInvoiceDto userInvoiceDto);
}
