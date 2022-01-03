package com.scm.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDetailKeyDto {
    private ItemRefDto item;
    private InvoiceDto invoice;
}
