package com.scm.backend.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class InventoryCheckDto extends SupperDto {
    private String remark;

    private Long availableQuantity;

    ItemRefDto item;

    UserInvoiceDto user;
}
