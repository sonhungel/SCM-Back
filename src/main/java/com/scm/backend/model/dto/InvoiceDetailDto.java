package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class InvoiceDetailDto extends SupperDto {
    @NotNull
    private InvoiceDto invoice;

    @NotNull
    private ItemRefDto item;

    @NotNull
    private Long quantity;

    private Double discount;
}
