package com.scm.backend.web;

import com.scm.backend.model.dto.ItemDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.service.SupplierService;
import com.scm.backend.util.SupplierDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierDtoMapper supplierDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createItem(@Valid @RequestBody SupplierDto supplierDto) throws SupplierNumberAlreadyExist {
        supplierService.createSupplier(supplierDto);
        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
