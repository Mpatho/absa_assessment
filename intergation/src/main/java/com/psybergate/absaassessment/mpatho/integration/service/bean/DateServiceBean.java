package com.psybergate.absaassessment.mpatho.integration.service.bean;

import com.psybergate.absaassessment.mpatho.integration.repository.HolidayRepository;
import com.psybergate.absaassessment.mpatho.integration.service.DateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DateServiceBean implements DateService {

    private final HolidayRepository holidayRepository;

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidayRepository.existsByDate(date);
    }

    @Override
    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
