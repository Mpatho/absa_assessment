package com.psybergate.absaassessment.mpatho.api.service.bean;

import com.psybergate.absaassessment.mpatho.api.service.BillService;
import org.springframework.stereotype.Service;

@Service
public class BillServiceBean implements BillService {

    @Override
    public void save(Integer year, Integer month, String content) {
        System.out.printf("Saving: year %s, month %s, content %s%n", year, month, content);
    }
}
