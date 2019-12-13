package com.psybergate.absaassessment.mpatho.integration.service;

import java.time.LocalDate;

public interface DateService {
    boolean isHoliday(LocalDate date);

    LocalDate currentDate();
}
