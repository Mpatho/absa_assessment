package com.psybergate.absaassessment.mpatho.integration.service;

import com.psybergate.absaassessment.mpatho.integration.entity.Bill;
import com.psybergate.absaassessment.mpatho.integration.entity.BillUploadHistory;

import java.time.LocalDate;
import java.util.List;

public interface BillService {
    List<Bill> retrieveCompletedBills(int year, int month);

    BillUploadHistory upload(String fileContent, LocalDate date);

    String generateBillingFileContent(List<Bill> bills, LocalDate date);

    boolean isBillUploaded(int year, int month);
}
