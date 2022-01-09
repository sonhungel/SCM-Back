package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ItemRefDto extends SupperDto {
    @NotNull
    @EqualsAndHashCode.Include
    private Integer itemNumber;

    private Long quantity;

    private Long minimumQuantity;
}
