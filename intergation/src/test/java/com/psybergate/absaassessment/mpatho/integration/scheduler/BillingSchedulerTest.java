package com.psybergate.absaassessment.mpatho.integration.scheduler;

import com.psybergate.absaassessment.mpatho.integration.entity.Bill;
import com.psybergate.absaassessment.mpatho.integration.service.BillService;
import com.psybergate.absaassessment.mpatho.integration.service.DateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillingSchedulerTest {

    @InjectMocks
    private BillingScheduler billingScheduler;

    @Mock
    private BillService billService;

    @Mock
    private DateService dateService;

    @Test
    public void shouldNotUploadBillFileWhenItAHoliday() {
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(dateService.currentDate()).thenReturn(date);
        when(dateService.isHoliday(date)).thenReturn(true);

        billingScheduler.sentBilling();

        verify(billService, times(0)).upload(any(String.class), eq(date));
    }

    @Test
    public void shouldNotUploadBillFileWhenAlreadUploadedForTheMonth() {
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(dateService.currentDate()).thenReturn(date);
        when(dateService.isHoliday(date)).thenReturn(false);
        when(billService.isBillUploaded(eq(date.getYear()), eq(date.getMonth().getValue()))).thenReturn(true);

        billingScheduler.sentBilling();

        verify(billService, times(0)).upload(any(String.class), eq(date));
    }

    @Test
    public void shouldSentBill() {
        LocalDate date = LocalDate.of(2019, 12, 9);
        String content = "A";
        List<Bill> bills = Collections.singletonList(mock(Bill.class));

        when(dateService.currentDate()).thenReturn(date);
        when(dateService.isHoliday(date)).thenReturn(false);
        when(billService.retrieveCompletedBills(date.getYear(), date.getMonth().getValue() - 1)).thenReturn(bills);
        when(billService.generateBillingFileContent(bills, date)).thenReturn(content);

        billingScheduler.sentBilling();

        verify(billService, times(1)).upload(content, date);
    }
}
