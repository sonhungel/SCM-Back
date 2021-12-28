package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class SupplierDto extends SupperDto {
    @EqualsAndHashCode.Include
    @Digits(integer = 9, fraction = 0)
    private Integer supplierNumber;

    @Size(min = 1, max = 50)
    private String name;
}
