package com.psybergate.absaassessment.mpatho.integration.repository;

import com.psybergate.absaassessment.mpatho.integration.entity.BillUploadHistory;
import com.psybergate.absaassessment.mpatho.integration.repository.common.BaseJpaRepository;

import java.time.LocalDate;

public interface BillUploadHistoryRepository extends BaseJpaRepository<BillUploadHistory> {
    boolean existsBySubmittedDateBetween(LocalDate start, LocalDate end);
}
