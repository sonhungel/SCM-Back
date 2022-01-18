package com.scm.backend.model.dto;

import java.util.List;

public interface ReportDto {
    DailyReportDto getDaily();

    WeeklyReportDto getWeekly();

    MonthlyReportDto getMonthly();
}
