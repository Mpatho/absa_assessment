package com.psybergate.absaassessment.mpatho.integration.service.bean;

import com.psybergate.absaassessment.mpatho.integration.constant.Currency;
import com.psybergate.absaassessment.mpatho.integration.constant.MessageStatus;
import com.psybergate.absaassessment.mpatho.integration.entity.Bill;
import com.psybergate.absaassessment.mpatho.integration.entity.BillUploadHistory;
import com.psybergate.absaassessment.mpatho.integration.repository.BillRepository;
import com.psybergate.absaassessment.mpatho.integration.repository.BillUploadHistoryRepository;
import com.psybergate.absaassessment.mpatho.integration.service.BillService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BillServiceBean implements BillService {
    private static final String SERVICE_NAME = "INTEGRATEDSERVICES";

    private final BillRepository billRepository;
    private final RestTemplate restTemplate;
    private final BillUploadHistoryRepository billUploadHistoryRepository;

    @Override
    @Transactional
    public BillUploadHistory upload(String fileContent, LocalDate date) {
        String result = restTemplate.postForObject(String.format("/%d/%d", date.getYear(), date.getMonth().getValue() - 1), fileContent, String.class);
        log.info(result);
        return billUploadHistoryRepository.save(new BillUploadHistory(fileContent, date));
    }

    @Override
    public String generateBillingFileContent(List<Bill> bills, LocalDate date) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Map<Currency, Long>> groupedBill = bills.stream()
                .collect(Collectors.groupingBy(Bill::getClientSwiftAddress, Collectors.groupingBy(Bill::getCurrency, Collectors.counting())));
        for (String address : groupedBill.keySet()) {
            Map<Currency, Long> usageByCurrency = groupedBill.get(address);
            for (Currency currency : usageByCurrency.keySet()) {
                String line = String.format("%s%s%s%4$tY%4$tm%4$td%5$06d", SERVICE_NAME, address, getSubService(currency), date, usageByCurrency.get(currency));
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean isBillUploaded(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        return billUploadHistoryRepository.existsBySubmittedDateBetween(date, date.plusMonths(1));
    }

    @Override
    public List<Bill> retrieveCompletedBills(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        return billRepository.findBillByMessageStatusAndDateTimeCreatedInBetween(MessageStatus.Completed, startDate.atStartOfDay(), LocalDateTime.of(endDate, LocalTime.MAX));
    }

    private String getSubService(Currency currency) {
        return (Currency.ZAR.equals(currency) ? "ZAR" : "CCY") + "OUT";
    }
}
