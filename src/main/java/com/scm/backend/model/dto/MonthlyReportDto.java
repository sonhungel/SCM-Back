package com.scm.backend.model.dto;

public interface MonthlyReportDto {
    Long getCost();

    Long getPaid();

    Object getItemsWarning();

    Object getItemsOutOfStock();
}
