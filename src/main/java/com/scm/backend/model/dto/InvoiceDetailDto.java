package com.scm.backend.model.dto;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class InvoiceDetailDto extends SupperDto{
    // deprecated
    private InvoiceDetailKeyDto key;

    private Integer customerNumber;

    private Integer itemNumber;

    private Long quantity;

    private Long price;

    private Long cost;

    private Double discount;

    // set internal
    private Long invoiceId;
}
