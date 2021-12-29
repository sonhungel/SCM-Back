package com.scm.backend.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class SupperDto {
    private Long id;

    private Integer version;

    private LocalDate addedDate;

    private LocalDate updateDate;
}
