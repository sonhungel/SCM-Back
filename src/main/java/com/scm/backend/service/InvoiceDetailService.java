package com.scm.backend.service;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.entity.InvoiceDetail;
import com.scm.backend.model.exception.InvoiceDetailAlreadyExistException;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;

import java.util.List;

public interface InvoiceDetailService {
    void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException;
    List<InvoiceDetail> findByKey(InvoiceDetailDto invoiceDetailDto) throws InvoiceNotFoundException, ItemNumberNotFoundException;
}
