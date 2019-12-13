package com.psybergate.absaassessment.mpatho.integration.scheduler;

import com.psybergate.absaassessment.mpatho.integration.entity.Bill;
import com.psybergate.absaassessment.mpatho.integration.service.BillService;
import com.psybergate.absaassessment.mpatho.integration.service.DateService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Transactional
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BillingScheduler {

    private final BillService billService;
    private final DateService dateService;

    @Scheduled(cron = "0 0 7 ? * MON-FRI")
    public void sentBilling() {
        LocalDate date = dateService.currentDate();
        if (dateService.isHoliday(date)) {
            return;
        }
        if (billService.isBillUploaded(date.getYear(), date.getMonth().getValue())) {
            return;
        }
        List<Bill> bills = billService.retrieveCompletedBills(date.getYear(), date.getMonth().getValue() - 1);
        String fileContent = billService.generateBillingFileContent(bills, date);
        billService.upload(fileContent, date);
    }
}
