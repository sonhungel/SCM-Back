package com.scm.backend.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class SuptTicketDto extends SupperDto {
    private SupplierDto supplier;

    private ItemRefDto item;

    private Long quantity;

    private Long cost;

    private String remark;
}
