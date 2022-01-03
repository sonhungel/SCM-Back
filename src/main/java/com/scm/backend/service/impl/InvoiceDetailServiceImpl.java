package com.scm.backend.service.impl;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.InvoiceDetail;
import com.scm.backend.model.entity.InvoiceDetailKey;
import com.scm.backend.model.entity.Item;
import com.scm.backend.model.exception.InvoiceDetailAlreadyExistException;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.repository.InvoiceDetailRepository;
import com.scm.backend.repository.InvoiceRepository;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.service.InvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException {
        checkBeforeCreate(invoiceDetailDto);
        InvoiceDetail invoiceDetail = createNewInvoiceDetail(invoiceDetailDto);

        invoiceDetailRepository.saveAndFlush(invoiceDetail);
    }

    @Override
    public List<InvoiceDetail> findByKey(InvoiceDetailDto invoiceDetailDto) throws InvoiceNotFoundException, ItemNumberNotFoundException {
        final Invoice invoice = invoiceRepository.findById(invoiceDetailDto.getKey().getInvoice().getId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found", invoiceDetailDto.getKey().getInvoice().getId()));

        final Item item = itemRepository.findItemByItemNumber(invoiceDetailDto.getKey().getItem().getItemNumber())
                .orElseThrow(() -> new ItemNumberNotFoundException("Item number not found", invoiceDetailDto.getKey().getItem().getItemNumber()));

        InvoiceDetailKey invoiceDetailKey = InvoiceDetailKey.builder()
                .invoice(invoice)
                .item(item)
                .build()
                ;

        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByKey(invoiceDetailKey);

        return invoiceDetails;
    }

    private InvoiceDetail createNewInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws InvoiceNotFoundException, ItemNumberNotFoundException {
        final Invoice invoice = invoiceRepository.findById(invoiceDetailDto.getKey().getInvoice().getId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found", invoiceDetailDto.getKey().getInvoice().getId()));

        final Item item = itemRepository.findItemByItemNumber(invoiceDetailDto.getKey().getItem().getItemNumber())
                .orElseThrow(() -> new ItemNumberNotFoundException("Item number not found", invoiceDetailDto.getKey().getItem().getItemNumber()));

        InvoiceDetailKey invoiceDetailKey = InvoiceDetailKey.builder()
                .invoice(invoice)
                .item(item)
                .build()
                ;

        InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                .key(invoiceDetailKey)
                .build()
                ;
        if(invoiceDetailDto.getDiscount() != null) {
            invoiceDetail.setDiscount(invoiceDetailDto.getDiscount());
        }

        if(invoiceDetailDto.getQuantity() != null) {
            invoiceDetail.setQuantity(invoiceDetailDto.getQuantity());
        }

        return invoiceDetail;
    }

    private void checkBeforeCreate(InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException {
        List<InvoiceDetail> invoiceDetailList = findByKey(invoiceDetailDto);
        if(!invoiceDetailList.isEmpty()){
            throw new InvoiceDetailAlreadyExistException("Invoice detail for invoice " + invoiceDetailDto.getKey().getInvoice().getId() +
                    " and item number " + invoiceDetailDto.getKey().getItem().getItemNumber() +" is already exist");
        }
    }
}
