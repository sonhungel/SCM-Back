package com.scm.backend.model.dto;

import lombok.*;

import java.time.LocalDate;


public interface DailyReportDto {
    Long getId();

    Long getPaid();

    Long getCost();

    LocalDate getDate();

    String getStatus();
}
