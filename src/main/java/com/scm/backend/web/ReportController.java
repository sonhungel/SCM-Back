package com.scm.backend.web;

import com.scm.backend.model.dto.DailyReportDto;
import com.scm.backend.model.dto.ResponseDto;
import com.scm.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/dailyReport")
    public ResponseEntity<ResponseDto> getAllSupplier() {
        List<DailyReportDto> dailyReportDtoList = reportService.getDailyReport(LocalDate.of(2022, 01, 12));

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, dailyReportDtoList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
