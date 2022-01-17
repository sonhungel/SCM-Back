package com.scm.backend.service.impl;

import com.scm.backend.model.dto.DailyReportDto;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public List<List<List<DailyReportDto>>> getMonthlyPaidReport(LocalDate now) {
        List<List<DailyReportDto>> listPaidResult = new ArrayList<>();
        List<List<DailyReportDto>> listCostResult = new ArrayList<>();
        List<List<List<DailyReportDto>>> listResult = new ArrayList<>();

        List<DailyReportDto> listPaid1 = userRepository.getMonthlyPaidReport(now.minusDays(6), now);
        List<DailyReportDto> listPaid2 = userRepository.getMonthlyPaidReport(now.minusDays(12), now.minusDays(6));
        List<DailyReportDto> listPaid3 = userRepository.getMonthlyPaidReport(now.minusDays(18), now.minusDays(12));
        List<DailyReportDto> listPaid4 = userRepository.getMonthlyPaidReport(now.minusDays(24), now.minusDays(18));
        List<DailyReportDto> listPaid5 = userRepository.getMonthlyPaidReport(now.minusDays(30), now.minusDays(24));

        List<DailyReportDto> listCost1 = userRepository.getMonthlyCostReport(now.minusDays(6), now);
        List<DailyReportDto> listCost2 = userRepository.getMonthlyCostReport(now.minusDays(12), now.minusDays(6));
        List<DailyReportDto> listCost3 = userRepository.getMonthlyCostReport(now.minusDays(18), now.minusDays(12));
        List<DailyReportDto> listCost4 = userRepository.getMonthlyCostReport(now.minusDays(24), now.minusDays(18));
        List<DailyReportDto> listCost5 = userRepository.getMonthlyCostReport(now.minusDays(30), now.minusDays(24));


        listPaidResult.add(listPaid1);
        listPaidResult.add(listPaid2);
        listPaidResult.add(listPaid3);
        listPaidResult.add(listPaid4);
        listPaidResult.add(listPaid5);

        listCostResult.add(listCost1);
        listCostResult.add(listCost2);
        listCostResult.add(listCost3);
        listCostResult.add(listCost4);
        listCostResult.add(listCost5);

        listResult.add(listPaidResult);
        listResult.add(listCostResult);

        return listResult;
    }
}
