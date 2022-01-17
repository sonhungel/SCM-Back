package com.scm.backend.service;

import com.scm.backend.model.dto.DailyReportDto;

import java.time.LocalDate;
import java.util.List;


public interface ReportService {
    List<DailyReportDto> getDailyPaidReport(LocalDate today);

    List<DailyReportDto> getDailyCostReport(LocalDate today);

    List<DailyReportDto> getWeeklyPaidReport(LocalDate today);

    List<DailyReportDto> getWeeklyCostReport(LocalDate today);

    List<List<List<DailyReportDto>>> getMonthlyPaidReport(LocalDate now);
}
