package com.scm.backend.service.impl;

import com.scm.backend.model.dto.DailyReportDto;
import com.scm.backend.repository.UserRepository;
import com.scm.backend.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<DailyReportDto> getDailyReport(LocalDate today) {
        List<DailyReportDto> invoiceList = userRepository.getDailyPaid(today);
        invoiceList.get(0).getStatus();
        return invoiceList;
    }
}
