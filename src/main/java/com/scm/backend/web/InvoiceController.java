package com.scm.backend.web;

import com.scm.backend.model.dto.InvoiceDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.UserInvoiceDto;
import com.scm.backend.service.InvoiceService;
import com.scm.backend.service.MapValidationErrorService;
import com.scm.backend.util.InvoiceDtoMapper;
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
@RequestMapping("/api/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceDtoMapper invoiceDtoMapper;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createInvoice(@Valid @RequestBody InvoiceDto invoiceDto, BindingResult result, Principal principal){
        UserInvoiceDto userInvoiceDto = new UserInvoiceDto();
        userInvoiceDto.setUsername(principal.getName());
        invoiceDto.setUser(userInvoiceDto);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            ResponseDto responseDto = new ResponseDto("Create invoice failed", HttpStatus.BAD_REQUEST, errorMap);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        invoiceService.createInvoice(invoiceDto);
        ResponseDto responseDto = new ResponseDto("Create invoice successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
