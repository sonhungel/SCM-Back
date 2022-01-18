package com.scm.backend.web;

import com.scm.backend.model.dto.*;
import com.scm.backend.model.entity.Invoice;
import com.scm.backend.model.entity.ItemType;
import com.scm.backend.model.exception.*;
import com.scm.backend.service.InvoiceDetailService;
import com.scm.backend.service.InvoiceService;
import com.scm.backend.service.MapValidationErrorService;
import com.scm.backend.util.InvoiceDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceDtoMapper invoiceDtoMapper;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    /*@PostMapping("/create")
    public ResponseEntity<ResponseDto> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto,
                                                     BindingResult result, Principal principal)
            throws CustomerNumberNotFoundException {
        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(principal.getName());
        invoiceDto.setUser(userInvoiceDto);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            ResponseDto responseDto = new ResponseDto("Create invoice failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        Invoice invoice = invoiceService.createInvoice(invoiceDto);


        InvoiceDto invoiceData = invoiceDtoMapper.toInvoiceDto(invoice);

        ResponseDto responseDto = new ResponseDto("Create invoice successfully", HttpStatus.OK, invoiceData);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }*/

    @PostMapping("/createFull")
    public ResponseEntity<ResponseDto> createInvoiceFull(@Valid @RequestBody InvoiceDto invoiceDto,
                                                     BindingResult result, Principal principal)
            throws CustomerNumberNotFoundException, ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException, InternalException {
        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(principal.getName());
        invoiceDto.setUser(userInvoiceDto);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            ResponseDto responseDto = new ResponseDto("Create invoice failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        Invoice invoice = invoiceService.createInvoice(invoiceDto);

        List<InvoiceDetailDto> invoiceDetailDtoList = invoiceDto.getInvoiceDetailDtoList();
        for(InvoiceDetailDto i : invoiceDetailDtoList){
            i.setInvoiceId(invoice.getId());
        }
        invoiceDetailService.createInvoiceDetailFull(invoiceDetailDtoList);
        InvoiceDto invoiceData = invoiceDtoMapper.toInvoiceDto(invoice);

        ResponseDto responseDto = new ResponseDto("Create invoice successfully", HttpStatus.OK, invoiceData);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/getAllInvoice/{pageNumber}")
    public ResponseEntity<ResponseDto> getAllInvoice(@PathVariable int pageNumber) {
        Page<Invoice> invoicePage = invoiceService.getAllInvoice(pageNumber);

        PaginationDto paginationDto = new PaginationDto();
        if(invoicePage.hasContent()) {
            paginationDto.setData(invoiceDtoMapper.toInvoiceDtoList(invoicePage.getContent()));
        }
        paginationDto.setHasNext(invoicePage.hasNext());
        paginationDto.setHasPrevious(invoicePage.hasPrevious());

        ResponseDto responseDto = new ResponseDto("Get successfully", HttpStatus.OK, paginationDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
