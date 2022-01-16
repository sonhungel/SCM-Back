package com.scm.backend.service.impl;

import com.scm.backend.model.dto.DailyReportDto;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<DailyReportDto> getDailyPaidReport(LocalDate today) {
        List<DailyReportDto> dailyList = userRepository.getDailyPaid(today);
        return dailyList;
    }

    @Override
    public List<DailyReportDto> getDailyCostReport(LocalDate today) {
        List<DailyReportDto> invoiceList = userRepository.getDailyCost(today);
        return invoiceList;
    }

    @Override
    public List<DailyReportDto> getWeeklyPaidReport(LocalDate today) {
        LocalDate fromDay;
        DayOfWeek day = today.getDayOfWeek();
        switch (day) {
            case MONDAY:
                fromDay = today;
                break;
            case TUESDAY:
                fromDay = today.minusDays(1);
                break;
            case WEDNESDAY:
                fromDay = today.minusDays(2);
                break;
            case THURSDAY:
                fromDay = today.minusDays(3);
                break;
            case FRIDAY:
                fromDay = today.minusDays(4);
                break;
            case SATURDAY:
                fromDay = today.minusDays(5);
                break;
            case SUNDAY:
                fromDay = today.minusDays(6);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + day);
        }

        List<DailyReportDto> weeklyList = userRepository.getWeeklyPaidReport(fromDay, today);

        return weeklyList;
    }

    @Override
    public List<DailyReportDto> getWeeklyCostReport(LocalDate today) {
        LocalDate fromDay;
        DayOfWeek day = today.getDayOfWeek();
        switch (day) {
            case MONDAY:
                fromDay = today;
                break;
            case TUESDAY:
                fromDay = today.minusDays(1);
                break;
            case WEDNESDAY:
                fromDay = today.minusDays(2);
                break;
            case THURSDAY:
                fromDay = today.minusDays(3);
                break;
            case FRIDAY:
                fromDay = today.minusDays(4);
                break;
            case SATURDAY:
                fromDay = today.minusDays(5);
                break;
            case SUNDAY:
                fromDay = today.minusDays(6);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + day);
        }

        List<DailyReportDto> weeklyList = userRepository.getWeeklyCostReport(fromDay, today);

        return weeklyList;
    }
}
