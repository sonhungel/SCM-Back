package com.scm.backend.web;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.entity.InvoiceDetail;
import com.scm.backend.model.exception.InvoiceDetailAlreadyExistException;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.service.InvoiceDetailService;
import com.scm.backend.service.MapValidationErrorService;
import com.scm.backend.util.InvoiceDetailDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/invoiceDetail")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private InvoiceDetailDtoMapper invoiceDetailDtoMapper;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createInvoiceDetail(@Valid @RequestBody InvoiceDetailDto invoiceDetailDto, BindingResult result) throws ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException {
        invoiceDetailService.createInvoiceDetail(invoiceDetailDto);
        ResponseDto responseDto = new ResponseDto("Create invoice detail successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/createAllInvoiceDetail")
    public ResponseEntity<ResponseDto> createAllInvoiceDetail(@Valid @RequestBody List<InvoiceDetailDto> invoiceDetailDtoList) throws ItemNumberNotFoundException, InvoiceNotFoundException, InvoiceDetailAlreadyExistException {
        invoiceDetailService.createAllInvoiceDetail(invoiceDetailDtoList);
        ResponseDto responseDto = new ResponseDto("Create invoice detail successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseDto> findByKey(@Valid @RequestBody InvoiceDetailDto invoiceDetailDto) throws ItemNumberNotFoundException, InvoiceNotFoundException {
        List<InvoiceDetail> invoiceDetailDtoList = invoiceDetailService.findByKey(invoiceDetailDto);
        List<InvoiceDetailDto> invoiceDetailDtos = new ArrayList<>();
        for(InvoiceDetail invoiceDetail : invoiceDetailDtoList){
            invoiceDetailDtos.add(invoiceDetailDtoMapper.toInvoiceDetailDto(invoiceDetail));
        }
        ResponseDto responseDto = new ResponseDto("Create invoice detail successfully", HttpStatus.OK, invoiceDetailDtos);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
