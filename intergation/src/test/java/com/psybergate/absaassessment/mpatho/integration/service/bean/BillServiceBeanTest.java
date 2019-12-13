package com.psybergate.absaassessment.mpatho.integration.service.bean;

import com.psybergate.absaassessment.mpatho.integration.constant.Currency;
import com.psybergate.absaassessment.mpatho.integration.constant.MessageStatus;
import com.psybergate.absaassessment.mpatho.integration.entity.Bill;
import com.psybergate.absaassessment.mpatho.integration.entity.BillUploadHistory;
import com.psybergate.absaassessment.mpatho.integration.repository.BillRepository;
import com.psybergate.absaassessment.mpatho.integration.repository.BillUploadHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillServiceBeanTest {

    @InjectMocks
    private BillServiceBean billServiceBean;

    @Mock
    private BillRepository billRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private BillUploadHistoryRepository billUploadHistoryRepository;

    @Test
    public void shouldRetrieveCompletedBillsForNov2019() {
        int year = 2019;
        int month = 11;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        List<Bill> bills = Collections.singletonList(mock(Bill.class));

        when(billRepository.findBillByMessageStatusAndDateTimeCreatedInBetween(eq(MessageStatus.Completed), eq(startDate.atStartOfDay()), eq(LocalDateTime.of(endDate, LocalTime.MAX)))).thenReturn(bills);

        List<Bill> result = billServiceBean.retrieveCompletedBills(year, month);

        assertThat(result, is(bills));
    }

    @Test
    public void shouldReturnTrueWhenBillUploadHistoryExitsForMonth() {
        int year = 2019;
        int month = 11;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);
        when(billUploadHistoryRepository.existsBySubmittedDateBetween(eq(startDate), eq(endDate))).thenReturn(true);

        boolean result = billServiceBean.isBillUploaded(year, month);

        assertThat(result, is(true));
    }

    @Test
    public void shouldSaveHistory() {
        String fileContent = "content";
        LocalDate date = LocalDate.of(2019, 12, 9);

        billServiceBean.upload(fileContent, date);

        ArgumentCaptor<BillUploadHistory> argumentCaptor = ArgumentCaptor.forClass(BillUploadHistory.class);
        verify(billUploadHistoryRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getContent(), is(fileContent));
        assertThat(argumentCaptor.getValue().getSubmittedDate(), is(date));
    }

    @Test
    public void shouldReturnContentWithUsageStats1() {
        Bill bill = mock(Bill.class);
        List<Bill> bills = Collections.singletonList(bill);
        String address = "00123456789";
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(bill.getClientSwiftAddress()).thenReturn(address);
        when(bill.getCurrency()).thenReturn(Currency.ZAR);

        String result = billServiceBean.generateBillingFileContent(bills, date);

        assertThat(result.length(), is(49));
        assertThat(result, is(getFileLayout(address, date, "ZAR", 1)));
    }

    @Test
    public void shouldReturnContentWithUsageStats2() {
        Bill bill1 = mock(Bill.class);
        Bill bill2 = mock(Bill.class);
        List<Bill> bills = Arrays.asList(bill1, bill2);
        String address = "00123456789";
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(bill1.getClientSwiftAddress()).thenReturn(address);
        when(bill1.getCurrency()).thenReturn(Currency.ZAR);
        when(bill2.getClientSwiftAddress()).thenReturn(address);
        when(bill2.getCurrency()).thenReturn(Currency.ZAR);

        String result = billServiceBean.generateBillingFileContent(bills, date);

        assertThat(result.length(), is(49));
        assertThat(result, is(getFileLayout(address, date, "ZAR", 2)));
    }

    @Test
    public void shouldReturnContentWith2EntryOfUsageStats1() {
        Bill bill1 = mock(Bill.class);
        Bill bill2 = mock(Bill.class);
        List<Bill> bills = Arrays.asList(bill1, bill2);
        String address = "00123456789";
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(bill1.getClientSwiftAddress()).thenReturn(address);
        when(bill1.getCurrency()).thenReturn(Currency.ZAR);
        when(bill2.getClientSwiftAddress()).thenReturn(address);
        when(bill2.getCurrency()).thenReturn(Currency.USD);

        String result = billServiceBean.generateBillingFileContent(bills, date);

        assertThat(result.length(), is(98));
        assertThat(result, containsString(getFileLayout(address, date, "ZAR", 1)));
        assertThat(result, containsString(getFileLayout(address, date, "CCY", 1)));
    }

    private String getFileLayout(String address, LocalDate date, String currency, int stats) {
        return String.format("INTEGRATEDSERVICES%s%sOUT%3$tY%3$tm%3$td%4$06d", address, currency, date, stats);
    }

}
