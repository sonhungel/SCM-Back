package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ItemTypeDto extends SupperDto {
    @NotNull
    @Size(min = 1, max = 100)
    private String typeName;
}
