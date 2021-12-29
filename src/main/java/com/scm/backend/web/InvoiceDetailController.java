package com.scm.backend.web;

import com.scm.backend.model.dto.InvoiceDetailDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.exception.InvoiceNotFoundException;
import com.scm.backend.model.exception.ItemNumberNotFoundException;
import com.scm.backend.service.InvoiceDetailService;
import com.scm.backend.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/invoiceDetail")
public class InvoiceDetailController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createInvoiceDetail(@Valid @RequestBody InvoiceDetailDto invoiceDetailDto, BindingResult result) throws ItemNumberNotFoundException, InvoiceNotFoundException {
        /*ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) {
            ResponseDto responseDto = new ResponseDto("Create failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }*/
        //todo: unique(invoice_id, item_number)
        invoiceDetailService.createInvoiceDetail(invoiceDetailDto);
        ResponseDto responseDto = new ResponseDto("Create invoice detail successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
