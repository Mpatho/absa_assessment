package com.psybergate.absaassessment.mpatho.integration.repository;

import com.psybergate.absaassessment.mpatho.integration.entity.Holiday;
import com.psybergate.absaassessment.mpatho.integration.repository.common.BaseJpaRepository;

import java.time.LocalDate;

public interface HolidayRepository extends BaseJpaRepository<Holiday> {
    boolean existsByDate(LocalDate date);
}
