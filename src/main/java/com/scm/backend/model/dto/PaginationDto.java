package com.scm.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {
    Boolean hasNext;
    Boolean hasPrevious;
    Object data;
}
