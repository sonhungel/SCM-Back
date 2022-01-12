package com.scm.backend.util;

import com.scm.backend.model.dto.SuptTicketDto;
import com.scm.backend.model.entity.SupTicket;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(builder = @Builder(disableBuilder = true), componentModel = "spring")
public interface SupTicketDtoMapper {
    SuptTicketDto toSupTicketDto(SupTicket supTicket);
    SupTicket toSupTicket(SuptTicketDto suptTicketDto);
}
