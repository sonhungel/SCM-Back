package com.scm.backend.service.impl;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.InvoiceDetail;
import com.scm.backend.model.entity.InvoiceDetailKey;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.repository.InvoiceDetailRepository;
import com.scm.backend.repository.InvoiceRepository;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException {
        checkBeforeCreate(invoiceDetailDto);
        InvoiceDetail invoiceDetail = createNewInvoiceDetail(invoiceDetailDto);

        invoiceDetailRepository.saveAndFlush(invoiceDetail);
    }

    private InvoiceDetail createNewInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws InvoiceNotFoundException, ItemNumberNotFoundException {
        final Invoice invoice = invoiceRepository.findById(invoiceDetailDto.getInvoice().getId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found", invoiceDetailDto.getInvoice().getId()));

        final Item item = itemRepository.findItemByItemNumber(invoiceDetailDto.getItem().getItemNumber())
                .orElseThrow(() -> new ItemNumberNotFoundException("Item number not found", invoiceDetailDto.getItem().getItemNumber()));

        InvoiceDetailKey invoiceDetailKey = InvoiceDetailKey.builder()
                .invoice(invoice)
                .item(item)
                .build()
                ;

        InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                .key(invoiceDetailKey)
                .quantity(invoiceDetailDto.getQuantity())
                .build()
                ;
        if(invoiceDetailDto.getDiscount() != null) {
            invoiceDetail.setDiscount(invoiceDetailDto.getDiscount());
        }

        return invoiceDetail;
    }

    private void checkBeforeCreate(InvoiceDetailDto invoiceDetailDto) {
    }
}
