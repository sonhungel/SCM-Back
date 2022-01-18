package com.scm.backend.web;

import com.scm.backend.model.dto.*;
import com.scm.backend.model.entity.User;
import com.scm.backend.service.ReportService;
import com.scm.backend.service.UserService;
import com.scm.backend.util.InternalState;
import com.scm.backend.util.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @GetMapping("/dailyReport")
    public ResponseEntity<ResponseDto> getReport() {
        List<DailyReportDto> dailyPaid = reportService.getDailyPaidReport(LocalDate.now());
        List<DailyReportDto> dailyCost = reportService.getDailyCostReport(LocalDate.now());
        List<DailyReportDto> weeklyPaid = reportService.getWeeklyPaidReport(LocalDate.now());
        List<DailyReportDto> weeklyCost = reportService.getWeeklyCostReport(LocalDate.now());

        List<User> userList = userService.getAllUser().stream().filter(e ->
                e.getInternalState() != InternalState.DELETED).collect(Collectors.toList());

        DailyReportDto dailyReportDto = new DailyReportDto() {
            @Override
            public Long getId() {
                return null;
            }

            @Override
            public Long getPaid() {
                if(dailyPaid.get(0) != null) {
                    if(dailyPaid.get(0).getPaid() != null){
                        return dailyPaid.get(0).getPaid();
                    }
                    return null;
                }
                return 0L;
            }

            @Override
            public Long getCost() {
                if(dailyCost.get(0) != null) {
                    if(dailyCost.get(0).getCost() != null){
                        return dailyCost.get(0).getCost();
                    }
                    return 0L;
                }
                return 0L;
            }

            @Override
            public LocalDate getDate() {
                return null;
            }

            @Override
            public Long getUser() {
                return (long) userList.size();
            }

            @Override
            public String getStatus() {
                return null;
            }
        };

        WeeklyReportDto weeklyReportDto = new WeeklyReportDto() {
            @Override
            public List<DailyReportDto> getWeeklyPaid() {
                return weeklyPaid;
            }

            @Override
            public List<DailyReportDto> getWeeklyCost() {
                return weeklyCost;
            }
        };

        ReportDto reportDto = new ReportDto() {
            @Override
            public DailyReportDto getDaily() {
                return dailyReportDto;
            }

            @Override
            public WeeklyReportDto getWeekly() {
                return weeklyReportDto;
            }
        };

        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, reportDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/monthlyReport")
    public ResponseEntity<ResponseDto> getMonthlyReport() {
        List<List<List<DailyReportDto>>> lists = reportService.getMonthlyPaidReport(LocalDate.now());



        ResponseDto responseDto = new ResponseDto("Create successfully", HttpStatus.OK, lists);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
