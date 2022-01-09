package com.scm.backend.service.impl;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.entity.*;
import com.scm.backend.model.exception.InvoiceDetailAlreadyExistException;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.repository.CustomerRepository;
import com.scm.backend.repository.InvoiceDetailRepository;
import com.scm.backend.repository.InvoiceRepository;
import com.scm.backend.repository.ItemRepository;
import com.scm.backend.service.InvoiceDetailService;
import com.scm.backend.util.InvoiceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvoiceDetailServiceImpl implements InvoiceDetailService {
    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void createInvoiceDetail(InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException {
        checkBeforeCreate(invoiceDetailDto);
        InvoiceDetail invoiceDetail = createNewInvoiceDetail(invoiceDetailDto);

        invoiceDetailRepository.saveAndFlush(invoiceDetail);
    }

    private void addTotalToInvoice(Long invoiceId, Long totalPaid) throws InvoiceNotFoundException {
        final Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found", invoiceId));

        invoice.setPaid(invoice.getPaid() + totalPaid);

        invoice.setStatus(InvoiceState.CLOSED);

        addTotalToCustomer(invoice.getCustomer(), totalPaid);

        invoiceRepository.saveAndFlush(invoice);
    }

    private void addTotalToCustomer(Customer customer, Long totalPaid) {
        customer.setPaid(customer.getPaid() + totalPaid);
        customer.setLatestBuy(LocalDate.now());
        customerRepository.saveAndFlush(customer);
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

    @Override
    public void createAllInvoiceDetail(List<InvoiceDetailDto> invoiceDetailDtoList) throws Exception {
        if(invoiceDetailDtoList.isEmpty()) {
            return;
        }

        Long totalPaid = 0L;

        for(InvoiceDetailDto i : invoiceDetailDtoList){
            if(i.getPrice() == null){
                throw new Exception("Price of item number " + i.getKey().getItem().getItemNumber() + " could not be NULL");
            }
            createInvoiceDetail(i);
            totalPaid += i.getPrice() * i.getQuantity();
        }

        Long invoiceId = invoiceDetailDtoList.get(0).getKey().getInvoice().getId();

        addTotalToInvoice(invoiceId, totalPaid);
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
                .addedDate(LocalDate.now())
                .build()
                ;
        if(invoiceDetailDto.getDiscount() != null) {
            invoiceDetail.setDiscount(invoiceDetailDto.getDiscount());
        }

        if(invoiceDetailDto.getQuantity() != null) {
            invoiceDetail.setQuantity(invoiceDetailDto.getQuantity());
        }

        if(invoiceDetailDto.getPrice() != null) {
            invoiceDetail.setPrice(invoiceDetailDto.getPrice());
        }

        invoiceDetailRepository.saveAndFlush(invoiceDetail);

        Long availableQuantity = item.getAvailableQuantity() - invoiceDetailDto.getQuantity();
        item.setAvailableQuantity(availableQuantity);
        itemRepository.save(item);

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
