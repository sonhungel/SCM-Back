package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserInvoiceDto extends SupperDto{
    @NotNull
    private String username;
}
