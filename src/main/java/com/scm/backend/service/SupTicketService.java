package com.scm.backend.service;

import com.scm.backend.model.dto.SuptTicketDto;
import com.scm.backend.model.entity.SupTicket;
import com.scm.backend.model.exception.CreateException;

import java.util.List;

public interface SupTicketService {
    SupTicket createSupTicket(SuptTicketDto suptTicketDto) throws CreateException;
    List<SupTicket> getAllSupTicket();
}
