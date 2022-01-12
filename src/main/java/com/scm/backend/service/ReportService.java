package com.scm.backend.service;

import com.scm.backend.model.dto.DailyReportDto;

import java.time.LocalDate;
import java.util.List;


public interface ReportService {
    List<DailyReportDto> getDailyReport(LocalDate today);
}
