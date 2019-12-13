package com.psybergate.absaassessment.mpatho.integration.service.bean;

import com.psybergate.absaassessment.mpatho.integration.repository.HolidayRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DateServiceBeanTest {

    @InjectMocks
    private DateServiceBean dateServiceBean;

    @Mock
    private HolidayRepository holidayRepository;

    @Test
    public void shouldReturnFalseWhenThereIsNoHolidayStored() {
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(holidayRepository.existsByDate(date)).thenReturn(false);

        boolean result = dateServiceBean.isHoliday(date);

        assertThat(result, is(false));
    }

    @Test
    public void shouldReturnTrueWhenThereIsHolidayStored() {
        LocalDate date = LocalDate.of(2019, 12, 9);

        when(holidayRepository.existsByDate(date)).thenReturn(true);

        boolean result = dateServiceBean.isHoliday(date);

        assertThat(result, is(true));
    }
}
