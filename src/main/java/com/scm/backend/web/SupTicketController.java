package com.scm.backend.web;

import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.model.dto.SupplierDto;
import com.scm.backend.model.dto.SuptTicketDto;
import com.scm.backend.model.entity.SupTicket;
import com.scm.backend.model.entity.Supplier;
import com.scm.backend.model.exception.CreateException;
import com.scm.backend.model.exception.SupplierNumberAlreadyExist;
import com.scm.backend.service.SupTicketService;
import com.scm.backend.util.SupTicketDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/supTicket")
public class SupTicketController {

    @Autowired
    private SupTicketService supTicketService;

    @Autowired
    private SupTicketDtoMapper supTicketDtoMapper;

    @PostMapping
    public ResponseEntity<ResponseDto> createST(@Valid @RequestBody SuptTicketDto suptTicketDto) throws CreateException {
        SupTicket s = supTicketService.createSupTicket(suptTicketDto);

        SuptTicketDto result = supTicketDtoMapper.toSupTicketDto(s);

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
