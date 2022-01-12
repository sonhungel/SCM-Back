package com.scm.backend.service;

import com.scm.backend.model.dto.SuptTicketDto;
import com.scm.backend.model.entity.SupTicket;
import com.scm.backend.model.exception.CreateException;

public interface SupTicketService {
    SupTicket createSupTicket(SuptTicketDto suptTicketDto) throws CreateException;
}
