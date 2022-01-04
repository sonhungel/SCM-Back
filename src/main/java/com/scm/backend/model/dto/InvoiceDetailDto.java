package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class InvoiceDetailDto extends SupperDto{
    private InvoiceDetailKeyDto key;

    private Long quantity;

    private Long price;

    private Double discount;
}
