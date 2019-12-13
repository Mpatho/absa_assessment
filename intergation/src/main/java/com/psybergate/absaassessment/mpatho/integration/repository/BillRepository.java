package com.psybergate.absaassessment.mpatho.integration.repository;

import com.psybergate.absaassessment.mpatho.integration.constant.MessageStatus;
import com.psybergate.absaassessment.mpatho.integration.entity.Bill;
import com.psybergate.absaassessment.mpatho.integration.repository.common.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends BaseJpaRepository<Bill> {

    @Query("SELECT b FROM Bill b WHERE b.messageStatus = :messageStatus AND b.dateTimeCreated BETWEEN :startDate AND :endDate")
    List<Bill> findBillByMessageStatusAndDateTimeCreatedInBetween(@Param("messageStatus") MessageStatus messageStatus, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
