package com.scm.backend.model.dto;

import com.scm.backend.util.InvoiceState;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class InvoiceDto extends SupperDto {
    private UserInvoiceDto user;

    // cus number
    private CustomerDto customer;

    // list item and quantity
    private List<InvoiceDetailDto> invoiceDetailDtoList;
    //private Map<String,List<String>> data;
    //auto set, don't need to add
    private Long paid;

    //auto set, don't need to add
    private InvoiceState status;
}
