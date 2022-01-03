package com.scm.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleKeyDto {
    private UserInvoiceDto user;
    private RoleDto role;
}
