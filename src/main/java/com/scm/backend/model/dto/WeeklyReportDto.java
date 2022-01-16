package com.scm.backend.model.dto;

import java.util.List;

public interface WeeklyReportDto {
    List<DailyReportDto> getWeeklyPaid();

    List<DailyReportDto> getWeeklyCost();
}
