package com.scm.backend.model.dto;

import lombok.*;


public interface DailyReportDto {
    Long getId();

    Long getPaid();

    String getStatus();
}
